package pl.pas.parcellocker.configuration;

import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.schema.CreateMaterializedViewPrimaryKey;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.api.querybuilder.schema.CreateType;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createMaterializedView;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createTable;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createType;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.udt;
import static pl.pas.parcellocker.configuration.SchemaConst.PARCEL_LOCKER_NAMESPACE;

public class TableSchemas {

    public static CreateTable prepareClientTable() {
        return createTable(PARCEL_LOCKER_NAMESPACE, "clients")
            .ifNotExists()
            .withPartitionKey("entity_id", DataTypes.UUID)
            .withColumn("first_name", DataTypes.TEXT)
            .withColumn("last_name", DataTypes.TEXT)
            .withColumn("tel_number", DataTypes.TEXT)
            .withClusteringColumn("active", DataTypes.BOOLEAN);
    }

    public static CreateTable prepareClientByTelNumberTable() {
        return createTable(PARCEL_LOCKER_NAMESPACE, "clients_by_tel")
            .ifNotExists()
            .withPartitionKey("tel_number", DataTypes.TEXT)
            .withColumn("entity_id", DataTypes.UUID);
    }

    public static CreateTable prepareListTable() {
        return createTable(PARCEL_LOCKER_NAMESPACE, "list")
            .ifNotExists()
            .withPartitionKey("entity_id", DataTypes.UUID)
            .withColumn("base_price", DataTypes.DECIMAL)
            .withColumn("priority", DataTypes.BOOLEAN);
    }

    public static CreateTable prepareParcelTable() {
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

    public static CreateTable prepareDeliveryByIdTable() {
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

    public static CreateMaterializedViewPrimaryKey prepareDeliveryByClientTable() {
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

    public static CreateTable prepareLockersByIdentityNumberTable() {
        return createTable(PARCEL_LOCKER_NAMESPACE, "lockers_by_id")
            .ifNotExists()
            .withPartitionKey("identity_number", DataTypes.TEXT)
            .withColumn("entity_id", DataTypes.UUID)
            .withColumn("address", DataTypes.TEXT)
            .withColumn("deposit_boxes", DataTypes.listOf(udt("deposit_box", true)));
    }

    public static CreateType prepareDepositBoxType() {
        return createType(PARCEL_LOCKER_NAMESPACE, "deposit_box")
            .ifNotExists()
            .withField("entity_id", DataTypes.UUID)
            .withField("delivery_id", DataTypes.UUID)
            .withField("is_empty", DataTypes.BOOLEAN)
            .withField("access_code", DataTypes.TEXT)
            .withField("tel_number", DataTypes.TEXT);
    }

}
