package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.api.core.type.codec.ExtraTypeCodecs;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.api.querybuilder.schema.CreateMaterializedViewPrimaryKey;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.api.querybuilder.schema.CreateType;
import pl.pas.parcellocker.model.DeliveryStatus;
import pl.pas.parcellocker.repositories.codec.DepositBoxCodec;

import java.net.InetSocketAddress;
import java.util.Map;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;
import static pl.pas.parcellocker.configuration.SchemaConst.PARCEL_LOCKER_NAMESPACE;
import static pl.pas.parcellocker.configuration.TableSchemas.prepareClientByTelNumberTable;
import static pl.pas.parcellocker.configuration.TableSchemas.prepareClientTable;
import static pl.pas.parcellocker.configuration.TableSchemas.prepareDeliveryByClientTable;
import static pl.pas.parcellocker.configuration.TableSchemas.prepareDeliveryByIdTable;
import static pl.pas.parcellocker.configuration.TableSchemas.prepareDepositBoxType;
import static pl.pas.parcellocker.configuration.TableSchemas.prepareListTable;
import static pl.pas.parcellocker.configuration.TableSchemas.prepareLockersByIdentityNumberTable;
import static pl.pas.parcellocker.configuration.TableSchemas.prepareParcelTable;

public class SessionConnector implements AutoCloseable {

    private final int REPLICATION_FACTOR = 2;
    public final CqlSession session;
    public DepositBoxCodec depositBoxCodec;

    public SessionConnector() {
        this.session = initSession();
    }

    private CqlSession initSession() {
        CqlSession session =
            CqlSession.builder()
                .addContactPoint(new InetSocketAddress("127.22.0.2", 9042))
                .addContactPoint(new InetSocketAddress("127.22.0.3", 9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("user", "password")
                .addTypeCodecs(ExtraTypeCodecs.enumNamesOf(DeliveryStatus.class))
                .build();

        CreateKeyspace createKeyspace = prepareKeyspace(REPLICATION_FACTOR);
        session.execute(createKeyspace.build());

        CreateType createDepositBoxType = prepareDepositBoxType();
        session.execute(createDepositBoxType.build());

        registerCodecs(session);

        CreateTable createClientTable = prepareClientTable();
        CreateTable createClientByTelTable = prepareClientByTelNumberTable();
        CreateTable createListTable = prepareListTable();
        CreateTable createParcelTable = prepareParcelTable();
        CreateTable createDeliveryByIdTable = prepareDeliveryByIdTable();
        CreateMaterializedViewPrimaryKey createDeliveryByClientTable = prepareDeliveryByClientTable();
        CreateTable createLockerTable = prepareLockersByIdentityNumberTable();

        session.execute(createClientTable.build());
        session.execute(createClientByTelTable.build());
        session.execute(createListTable.build());
        session.execute(createParcelTable.build());
        session.execute(createDeliveryByIdTable.build());
        session.execute(createDeliveryByClientTable.build());
        session.execute(createLockerTable.build());

        return session;
    }

    private CreateKeyspace prepareKeyspace(int replicationFactor) {
        return createKeyspace(CqlIdentifier.fromCql(PARCEL_LOCKER_NAMESPACE))
            .ifNotExists()
            .withNetworkTopologyStrategy(Map.of("dc1", replicationFactor))
            .withDurableWrites(true);
    }

    private void registerCodecs(CqlSession session) {
        CodecRegistry codecRegistry = session.getContext().getCodecRegistry();

        UserDefinedType depositBoxUdt =
            session
                .getMetadata()
                .getKeyspace(PARCEL_LOCKER_NAMESPACE)
                .flatMap(ks -> ks.getUserDefinedType("deposit_box"))
                .orElseThrow(IllegalStateException::new);
        TypeCodec<UdtValue> innerCodec = codecRegistry.codecFor(depositBoxUdt);

        depositBoxCodec = new DepositBoxCodec(innerCodec);
        ((MutableCodecRegistry) codecRegistry).register(depositBoxCodec);
    }

    @Override
    public void close() {
        session.close();
    }
}
