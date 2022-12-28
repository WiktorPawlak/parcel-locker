package pl.pas.parcellocker.repositories;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.repositories.dao.delivery.DeliveryByClientDao;
import pl.pas.parcellocker.repositories.dao.delivery.DeliveryByIdDao;
import pl.pas.parcellocker.repositories.mapper.DeliveryMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.truncate;
import static pl.pas.parcellocker.configuration.SchemaConst.PARCEL_LOCKER_NAMESPACE;

public class DeliveryRepository extends SessionConnector {

    private final DeliveryByIdDao deliveryByIdDao;
    private final DeliveryByClientDao deliveryByClientDao;

    public DeliveryRepository() {
        DeliveryMapper deliveryMapper = initDeliveryMapper(session);
        this.deliveryByIdDao = deliveryMapper.deliveryByIdDao();
        this.deliveryByClientDao = deliveryMapper.deliveryByClientDao();
    }

    private DeliveryMapper initDeliveryMapper(CqlSession session) {
        return DeliveryMapper.builder(session)
            .withDefaultKeyspace(PARCEL_LOCKER_NAMESPACE)
            .build();
    }

    public void save(Delivery delivery) {
        deliveryByIdDao.create(delivery);
    }

    public void update(Delivery delivery) {
        deliveryByIdDao.update(delivery, delivery.getEntityId());
    }

    public void delete(Delivery delivery) {
        deliveryByIdDao.delete(delivery);
    }

    public List<Delivery> findAll() {
        return deliveryByIdDao.all().all();
    }

    public Delivery findById(UUID id) {
       return deliveryByIdDao.findById(id);
    }

    public List<Delivery> findByClientId(UUID clientId) {
        List<UUID> deliveryIds =  deliveryByClientDao.findByClientId(clientId)
            .map((it) -> it.get(0, UUID.class))
            .all();
        return deliveryIds.stream().map(deliveryByIdDao::findById).collect(Collectors.toList());
    }

    public List<Delivery> findArchivedByClientId(UUID clientId) {
        Select select = selectFrom(PARCEL_LOCKER_NAMESPACE, "delivery_by_client")
            .column("entity_id")
            .whereColumn("receiver_id")
            .isEqualTo(literal(clientId))
            .whereColumn("archived")
            .isEqualTo(literal(true))
            .allowFiltering();

        List<UUID> deliveryIds = session.execute(select.build())
            .map((it) -> it.get(0, UUID.class))
            .all();

        return deliveryByIdDao.findByIds(deliveryIds).all();
    }

    public void clear() {
        Truncate truncateDeliveryById = truncate("parcel_locker", "delivery_by_id");
        session.execute(truncateDeliveryById.build());
    }
}
