package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.DataTypes;
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
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createMaterializedView;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createType;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.udt;
import static pl.pas.parcellocker.configuration.SchemaConst.PARCEL_LOCKER_NAMESPACE;

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

    private CreateTable prepareClientTable() {
        return createTable(PARCEL_LOCKER_NAMESPACE, "clients")
            .ifNotExists()
            .withPartitionKey("entity_id", DataTypes.UUID)
            .withColumn("first_name", DataTypes.TEXT)
            .withColumn("last_name", DataTypes.TEXT)
            .withColumn("tel_number", DataTypes.TEXT)
            .withClusteringColumn("active", DataTypes.BOOLEAN);
    }

    private CreateTable prepareClientByTelNumberTable() {
        return createTable(PARCEL_LOCKER_NAMESPACE, "clients_by_tel")
            .ifNotExists()
            .withPartitionKey("tel_number", DataTypes.TEXT)
            .withColumn("entity_id", DataTypes.UUID);
    }

    private CreateTable prepareListTable() {
        return createTable(PARCEL_LOCKER_NAMESPACE, "list")
            .ifNotExists()
            .withPartitionKey("entity_id", DataTypes.UUID)
            .withColumn("base_price", DataTypes.DECIMAL)
            .withColumn("priority", DataTypes.BOOLEAN);
    }

    private CreateTable prepareParcelTable() {
        return createTable(PARCEL_LOCKER_NAMESPACE, "parcel")
            .ifNotExists()
            .withPartitionKey("entity_id", DataTypes.UUID)
            .withColumn("base_price", DataTypes.DECIMAL)
            .withColumn("width", DataTypes.DOUBLE)
            .withColumn("length", DataTypes.DOUBLE)
            .withColumn("height", DataTypes.DOUBLE)
            .withColumn("weight", DataTypes.DOUBLE)
            .withColumn("fragile", DataTypes.BOOLEAN);
    }

    private CreateTable prepareDeliveryByIdTable() {
        return createTable(PARCEL_LOCKER_NAMESPACE, "delivery_by_id")
            .ifNotExists()
            .withPartitionKey("entity_id", DataTypes.UUID)
            .withColumn("shipper_id", DataTypes.UUID)
            .withColumn("receiver_id", DataTypes.UUID)
            .withColumn("status", DataTypes.TEXT)
            .withColumn("package_id", DataTypes.UUID)
            .withColumn("locker_identity_number", DataTypes.TEXT)
            .withColumn("archived", DataTypes.BOOLEAN);
    }

    private CreateMaterializedViewPrimaryKey prepareDeliveryByClientTable() {
        return createMaterializedView(PARCEL_LOCKER_NAMESPACE, "delivery_by_client")
            .ifNotExists()
            .asSelectFrom(PARCEL_LOCKER_NAMESPACE, "delivery_by_id")
            .columns("entity_id", "receiver_id", "archived")
            .whereColumn("entity_id")
            .isNotNull()
            .whereColumn("receiver_id")
            .isNotNull()
            .whereColumn("archived")
            .isNotNull()
            .withPartitionKey("receiver_id")
            .withClusteringColumn("entity_id");
    }

    private CreateTable prepareLockersByIdentityNumberTable() {
        return createTable(PARCEL_LOCKER_NAMESPACE, "lockers_by_id")
            .ifNotExists()
            .withPartitionKey("identity_number", DataTypes.TEXT)
            .withColumn("entity_id", DataTypes.UUID)
            .withColumn("address", DataTypes.TEXT)
            .withColumn("deposit_boxes", DataTypes.listOf(udt("deposit_box", true)));
    }

    private CreateType prepareDepositBoxType() {
        return createType(PARCEL_LOCKER_NAMESPACE, "deposit_box")
            .ifNotExists()
            .withField("entity_id", DataTypes.UUID)
            .withField("delivery_id", DataTypes.UUID)
            .withField("is_empty", DataTypes.BOOLEAN)
            .withField("access_code", DataTypes.TEXT)
            .withField("tel_number", DataTypes.TEXT);
    }

    private void registerCodecs(CqlSession session) {
        CodecRegistry codecRegistry = session.getContext().getCodecRegistry();

        UserDefinedType depositBoxUdt =
            session
                .getMetadata()
                .getKeyspace(PARCEL_LOCKER_NAMESPACE)
                .flatMap(ks -> ks.getUserDefinedType("deposit_box")).get();
                //.orElseThrow(IllegalStateException::new);
        TypeCodec<UdtValue> innerCodec = codecRegistry.codecFor(depositBoxUdt);

        depositBoxCodec = new DepositBoxCodec(innerCodec);
        ((MutableCodecRegistry) codecRegistry).register(depositBoxCodec);
    }

    @Override
    public void close() {
        session.close();
    }
}
