package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.repositories.dao.client.ClientDao;
import pl.pas.parcellocker.repositories.mapper.ClientMapper;

import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.deleteFrom;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.insertInto;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.truncate;
import static pl.pas.parcellocker.configuration.SchemaConst.PARCEL_LOCKER_NAMESPACE;
import static pl.pas.parcellocker.configuration.SchemaNames.ACTIVE;
import static pl.pas.parcellocker.configuration.SchemaNames.ENTITY_ID;
import static pl.pas.parcellocker.configuration.SchemaNames.FIRST_NAME;
import static pl.pas.parcellocker.configuration.SchemaNames.LAST_NAME;
import static pl.pas.parcellocker.configuration.SchemaNames.TEL_NUMBER;

public class ClientRepository extends SessionConnector {

    private final ClientDao clientDao;

    public ClientRepository() {
        ClientMapper clientMapper = initClientMapper(session);
        this.clientDao = clientMapper.clientDao();
    }

    private ClientMapper initClientMapper(CqlSession session) {
        return ClientMapper.builder(session)
            .withDefaultKeyspace(PARCEL_LOCKER_NAMESPACE)
            .build();
    }

    public void save(Client client) {
        Insert insertIntoClientsById = insertInto("clients")
            .value(ENTITY_ID, literal(client.getEntityId()))
            .value(FIRST_NAME, literal(client.getFirstName()))
            .value(LAST_NAME, literal(client.getLastName()))
            .value(TEL_NUMBER, literal(client.getTelNumber()))
            .value(ACTIVE, literal(client.isActive()));

        Insert insertIntoClientsByTel = insertInto("clients_by_tel")
            .value(TEL_NUMBER, literal(client.getTelNumber()))
            .value(ENTITY_ID, literal(client.getEntityId()));

        BatchStatement batchStatement = BatchStatement.builder(BatchType.LOGGED)
            .setKeyspace(PARCEL_LOCKER_NAMESPACE)
            .addStatement(insertIntoClientsById.build())
            .addStatement(insertIntoClientsByTel.build())
            .build();

        session.execute(batchStatement);
    }

    public void update(Client client) {
        clientDao.update(client, client.getEntityId());
    }

    public void delete(Client client) {
        Delete deleteClientById = deleteFrom("clients")
            .where(Relation.column(ENTITY_ID)
                .isEqualTo(literal(client.getEntityId())));

        Delete deleteClientByTel = deleteFrom("clients_by_tel")
            .where(Relation.column(TEL_NUMBER)
                .isEqualTo(literal(client.getTelNumber())));

        BatchStatement batchStatement = BatchStatement.builder(BatchType.LOGGED)
            .setKeyspace(PARCEL_LOCKER_NAMESPACE)
            .addStatement(deleteClientById.build())
            .addStatement(deleteClientByTel.build())
            .build();

        session.execute(batchStatement);
    }

    public PagingIterable<Client> findAll() {
        return clientDao.all();
    }

    public Client findById(UUID id) {
       return clientDao.findById(id);
    }

    public Client findByTelNumber(String telNumber) {
        Select selectClientByTelNumber = selectFrom(PARCEL_LOCKER_NAMESPACE, "clients_by_tel")
            .column("entity_id")
            .whereColumn("tel_number")
            .isEqualTo(literal(telNumber));

        UUID clientId = session.execute(selectClientByTelNumber.build())
            .map((it) -> it.get(0, UUID.class))
            .one();

        return clientId != null ? findById(clientId) : null;
    }

    public void clear() {
        Truncate truncateClientsById = truncate(PARCEL_LOCKER_NAMESPACE, "clients");
        Truncate truncateClientsByTel = truncate(PARCEL_LOCKER_NAMESPACE, "clients_by_tel");

        session.execute(truncateClientsById.build());
        session.execute(truncateClientsByTel.build());
    }
}
