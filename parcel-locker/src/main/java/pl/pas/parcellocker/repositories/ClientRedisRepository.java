package pl.pas.parcellocker.repositories;

import pl.pas.parcellocker.model.Client;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.SearchResult;

import java.text.MessageFormat;

public class ClientRedisRepository extends AbstractRedisRepository<Client> {

    public ClientRedisRepository() {
        super(Client.class);
    }

    public Client findByTelNumber(String telNumber) {
        MessageFormat formatter = new MessageFormat("@\\$\\.telNumber:{0}");
        Query query = new Query(formatter.format(new Object[]{telNumber}));
        SearchResult searchResult = pool.ftSearch("parcel-locker", query);
        Document first = searchResult.getDocuments().stream().findFirst().orElse(null);
        return first == null ? null : jsonb.fromJson(first.get("$").toString(), Client.class);
    }

}
