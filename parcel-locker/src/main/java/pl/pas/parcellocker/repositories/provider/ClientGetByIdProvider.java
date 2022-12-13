package pl.pas.parcellocker.repositories.provider;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import pl.pas.parcellocker.model.Client;

public class ClientGetByIdProvider {
    private final CqlSession session;

    private EntityHelper<Client> clientEntityHelper;

    public ClientGetByIdProvider(MapperContext ctx, EntityHelper<Client> clientEntityHelper) {
        this.session = ctx.getSession();
        this.clientEntityHelper = clientEntityHelper;
    }

//    AbstractEntity findByUid(UUID id) {
//        Select select = QueryBuilder
//            .selectFrom(CqlIdentifier.fromCql("clients"))
//            .all()
//            .where(Relation.column("entity_id").isEqualTo(id.))
//    }
}
