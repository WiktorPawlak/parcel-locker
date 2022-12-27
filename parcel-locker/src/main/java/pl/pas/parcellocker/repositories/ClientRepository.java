package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import pl.pas.parcellocker.configuration.SchemaConst;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.repositories.dao.ClientDao;
import pl.pas.parcellocker.repositories.mapper.ClientMapper;

import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom;

public class ClientRepository extends SessionConnector {

    private final ClientDao clientDao;

    public ClientRepository() {
        ClientMapper clientMapper = initClientMapper(session);
        this.clientDao = clientMapper.clientDao();
    }

    private ClientMapper initClientMapper(CqlSession session) {
        return ClientMapper.builder(session)
            .withDefaultKeyspace(SchemaConst.PARCEL_LOCKER_NAMESPACE)
            .build();
    }

    public void save(Client client) {
        clientDao.create(client);
    }

    public void update(Client client) {
        clientDao.update(client, client.getEntityId());
    }

    public void delete(Client client) {
        clientDao.delete(client);
    }

    public PagingIterable<Client> findAll() {
        return clientDao.all();
    }

    public Client findById(UUID id) {
       return clientDao.findById(id);
    }

    public SimpleStatement findByTelNumber(String telNumber) {
        return selectFrom("clients")
            .all()
            .whereColumn("telNumber")
            .isEqualTo(bindMarker())
            .build();
    }
}
