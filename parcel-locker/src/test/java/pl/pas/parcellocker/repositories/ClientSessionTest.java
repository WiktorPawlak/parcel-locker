package pl.pas.parcellocker.repositories;//package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.configuration.SchemaConst;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.repositories.mapper.ClientMapper;
import pl.pas.parcellocker.repositories.mapper.ClientMapperBuilder;

import java.net.InetSocketAddress;

class ClientSessionTest {

    @Test
    void test() {
        try (
            CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("127.22.0.2", 9042))
                .addContactPoint(new InetSocketAddress("127.22.0.3", 9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("user", "password")
                .build()
        ) {
            CreateKeyspace keyspace = SchemaBuilder.createKeyspace(
                    CqlIdentifier.fromCql(SchemaConst.PARCEL_LOCKER_NAMESPACE))
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);
            SimpleStatement createKeyspace = keyspace.build();
            session.execute(createKeyspace);

            CreateTable table = SchemaBuilder.createTable(SchemaConst.PARCEL_LOCKER_NAMESPACE ,"clients")
                .ifNotExists()
                .withPartitionKey("entity_id", DataTypes.UUID)
                .withColumn("first_name", DataTypes.TEXT)
                .withColumn("last_name", DataTypes.TEXT)
                .withColumn("tel_number", DataTypes.TEXT)
                .withClusteringColumn("active", DataTypes.BOOLEAN);
            SimpleStatement createTable = table.build();
            session.execute(createTable);

            ClientMapper clientMapper = ClientMapper.builder(session)
                .withDefaultKeyspace(SchemaConst.PARCEL_LOCKER_NAMESPACE)
                .build();
            ClientDao dao = clientMapper.clientDao();
            dao.create(new Client("Adam", "Rogal", "123"));
        }
    }
}
