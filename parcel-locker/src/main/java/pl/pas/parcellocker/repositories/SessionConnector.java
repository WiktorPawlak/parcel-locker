package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.ExtraTypeCodecs;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.internal.core.type.codec.extras.enums.EnumNameCodec;
import pl.pas.parcellocker.model.DeliveryStatus;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;
import static pl.pas.parcellocker.configuration.SchemaConst.PARCEL_LOCKER_NAMESPACE;


public class SessionConnector implements AutoCloseable {

    public final CqlSession session;

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

        CreateKeyspace keyspace =
            createKeyspace(CqlIdentifier.fromCql(PARCEL_LOCKER_NAMESPACE))
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);

        SimpleStatement createKeyspace = keyspace.build();
        session.execute(createKeyspace);

        CreateTable clientTable = SchemaBuilder.createTable(PARCEL_LOCKER_NAMESPACE ,"clients")
            .ifNotExists()
            .withPartitionKey("entity_id", DataTypes.UUID)
            .withColumn("first_name", DataTypes.TEXT)
            .withColumn("last_name", DataTypes.TEXT)
            .withColumn("tel_number", DataTypes.TEXT)
            .withClusteringColumn("active", DataTypes.BOOLEAN);
        SimpleStatement createClientTable = clientTable.build();

        CreateTable listTable = SchemaBuilder.createTable(PARCEL_LOCKER_NAMESPACE ,"list")
            .ifNotExists()
            .withPartitionKey("entity_id", DataTypes.UUID)
            .withColumn("base_price", DataTypes.DECIMAL)
            .withColumn("priority", DataTypes.BOOLEAN);
        SimpleStatement createListTable = listTable.build();

        CreateTable parcelTable = SchemaBuilder.createTable(PARCEL_LOCKER_NAMESPACE ,"parcel")
            .ifNotExists()
            .withPartitionKey("entity_id", DataTypes.UUID)
            .withColumn("base_price", DataTypes.DECIMAL)
            .withColumn("width", DataTypes.DOUBLE)
            .withColumn("length", DataTypes.DOUBLE)
            .withColumn("height", DataTypes.DOUBLE)
            .withColumn("weight", DataTypes.DOUBLE)
            .withColumn("fragile", DataTypes.BOOLEAN);
        SimpleStatement createParcelTable = parcelTable.build();

        CreateTable deliveryByIdTable = SchemaBuilder.createTable(PARCEL_LOCKER_NAMESPACE ,"delivery_by_id")
            .ifNotExists()
            .withPartitionKey("entity_id", DataTypes.UUID)
            .withColumn("shipper_id", DataTypes.UUID)
            .withColumn("receiver_id", DataTypes.UUID)
            .withColumn("status", DataTypes.TEXT)
            .withColumn("package_id", DataTypes.UUID)
            .withColumn("locker_id", DataTypes.UUID)
            .withColumn("archived", DataTypes.BOOLEAN);
        SimpleStatement createDeliveryByIdTable = deliveryByIdTable.build();

        CreateTable deliveryByClientTable = SchemaBuilder.createTable(PARCEL_LOCKER_NAMESPACE ,"delivery_by_client")
            .ifNotExists()
            .withPartitionKey("receiver_id", DataTypes.UUID)
            .withColumn("entity_id", DataTypes.UUID)
            .withClusteringColumn("archived", DataTypes.BOOLEAN);
        SimpleStatement createDeliveryByClientTable = deliveryByClientTable.build();

        session.execute(createClientTable);
        session.execute(createListTable);
        session.execute(createParcelTable);
        session.execute(createDeliveryByIdTable);
        session.execute(createDeliveryByClientTable);

        return session;
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
