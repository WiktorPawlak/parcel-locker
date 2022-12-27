package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.type.codec.ExtraTypeCodecs;
import com.datastax.oss.driver.api.core.type.codec.TypeCodec;
import com.datastax.oss.driver.api.core.type.codec.registry.CodecRegistry;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import pl.pas.parcellocker.configuration.SchemaConst;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.repositories.dao.delivery.DeliveryByClientDao;
import pl.pas.parcellocker.repositories.dao.delivery.DeliveryByIdDao;
import pl.pas.parcellocker.repositories.mapper.DeliveryMapper;

import java.util.List;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.insertInto;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static pl.pas.parcellocker.configuration.DeliverySchemaNames.ARCHIVED;
import static pl.pas.parcellocker.configuration.DeliverySchemaNames.ENTITY_ID;
import static pl.pas.parcellocker.configuration.DeliverySchemaNames.LOCKER_ID;
import static pl.pas.parcellocker.configuration.DeliverySchemaNames.PACKAGE_ID;
import static pl.pas.parcellocker.configuration.DeliverySchemaNames.RECEIVER_ID;
import static pl.pas.parcellocker.configuration.DeliverySchemaNames.SHIPPER_ID;
import static pl.pas.parcellocker.configuration.DeliverySchemaNames.STATUS;
import static pl.pas.parcellocker.configuration.SchemaConst.PARCEL_LOCKER_NAMESPACE;

public class DeliveryRepository extends SessionConnector {

    private final DeliveryByIdDao deliveryByIdDao;
    //private final DeliveryByClientDao deliveryByClientDao;

    public DeliveryRepository() {
        DeliveryMapper deliveryMapper = initDeliveryMapper(session);
        this.deliveryByIdDao = deliveryMapper.deliveryByIdDao();
        //this.deliveryByClientDao = deliveryMapper.deliveryByClientDao(CqlIdentifier.fromCql(PARCEL_LOCKER_NAMESPACE));
    }

    private DeliveryMapper initDeliveryMapper(CqlSession session) {
        return DeliveryMapper.builder(session)
            .withDefaultKeyspace(PARCEL_LOCKER_NAMESPACE)
            .build();
    }

    public void save(Delivery delivery) {
        Insert insertIntoDeliveryById = insertInto("delivery_by_id")
            .value(ENTITY_ID, literal(delivery.getEntityId()))
            .value(SHIPPER_ID, literal(delivery.getShipper()))
            .value(RECEIVER_ID, literal(delivery.getReceiver()))
            .value(STATUS, ExtraTypeCodecs.)
            .value(PACKAGE_ID, literal(delivery.getPack()))
            .value(LOCKER_ID, literal(delivery.getLocker()))
            .value(ARCHIVED, literal(delivery.isArchived()));

//        Insert insertIntoDeliveryByClient = insertInto("delivery_by_client")
//            .value(ENTITY_ID, literal(delivery.getEntityId()))
//            .value(RECEIVER_ID, literal(delivery.getReceiver()))
//            .value(ARCHIVED, literal(delivery.isArchived()));

        BatchStatement batchStatement = BatchStatement.builder(BatchType.LOGGED)
            .setKeyspace(PARCEL_LOCKER_NAMESPACE)
            .addStatement(insertIntoDeliveryById.build())
            //.addStatement(insertIntoDeliveryByClient.build())
            .build();

        session.execute(batchStatement);
    }

//    public void update(Client client) {
//        clientDao.update(client, client.getEntityId());
//    }
//
//    public void delete(Client client) {
//        clientDao.delete(client);
//    }

    public List<Delivery> findAllById() {
        return deliveryByIdDao.all().all();
    }

//    public Client findById(UUID id) {
//       return clientDao.findById(id);
//    }

//    public SimpleStatement findByTelNumber(String telNumber) {
//        return selectFrom("clients")
//            .all()
//            .whereColumn("telNumber")
//            .isEqualTo(bindMarker())
//            .build();
//    }
}
