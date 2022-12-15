package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.repositories.dao.ClientDao;
import pl.pas.parcellocker.repositories.mapper.ClientMapper;

import java.net.InetSocketAddress;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;
import static pl.pas.parcellocker.configuration.SchemaConst.PARCEL_LOCKER_NAMESPACE;

public class ClientRepository implements AutoCloseable {

    private CqlSession initSession() {
        CqlSession session =
            CqlSession.builder()
                .addContactPoint(new InetSocketAddress("127.22.0.2", 9042))
                .addContactPoint(new InetSocketAddress("127.22.0.3", 9043))
                .withKeyspace(CqlIdentifier.fromCql(PARCEL_LOCKER_NAMESPACE))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("user", "password")
                .build();

        CreateKeyspace keyspace =
            createKeyspace(CqlIdentifier.fromCql(PARCEL_LOCKER_NAMESPACE))
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true);

        SimpleStatement createKeyspace = keyspace.build();
        session.execute(createKeyspace);

        CreateTable table = SchemaBuilder.createTable(PARCEL_LOCKER_NAMESPACE ,"clients")
            .ifNotExists()
            .withPartitionKey("entity_id", DataTypes.UUID)
            .withColumn("first_name", DataTypes.TEXT)
            .withColumn("last_name", DataTypes.TEXT)
            .withColumn("tel_number", DataTypes.TEXT)
            .withClusteringColumn("active", DataTypes.BOOLEAN);
        SimpleStatement createTable = table.build();
        session.execute(createTable);

        return session;
    }

    private ClientDao getClientDao() {
        ClientMapper clientMapper = ClientMapper.builder(initSession()).build();
        return clientMapper.clientDao();
    }

    public void save(Client client) {
        getClientDao().create(client);
    }

    public void update(Client client) {
        getClientDao().update(client, client.getEntityId());
    }

    public void delete(Client client) {
        getClientDao().delete(client);
    }

    public Client findById(UUID id) {
       return  getClientDao().findById(id);
    }

    @Override
    public void close() throws Exception {

    }
}
