package eryce.bringit.orderprocessing.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import eryce.bringit.orderprocessing.configuration.MongoDbConfiguration;
import eryce.bringit.orderprocessing.entity.Order;
import eryce.bringit.shared.model.order.OrderStatus;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@Repository
@Qualifier("default")
public class MongoDbOrderRepository extends MongoDbRepository implements OrderRepository {

    private final MongoDatabase database;
    private final MongoCollection<Order> collection;
    private final static String ID_FIELD = "_id";
    private final static String STATUS_FIELD = "orderStatus";

    public MongoDbOrderRepository(final MongoDbConfiguration properties) {
        super(properties);
        this.database = mongoClient.getDatabase(properties.getDatabase()).withCodecRegistry(pojoCodecRegistry);
        this.collection = this.database.getCollection(properties.getCollection(), Order.class);
    }

    @Override
    public Optional<Order> createOrder(Order order) {
        InsertOneResult insertOneResult = collection.insertOne(order);

        if (documentInserted(insertOneResult)) {
            order.setId(insertOneResult.getInsertedId().asObjectId().getValue());
            return Optional.of(order);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Order> findById(String id) {

        Bson filter = eq(ID_FIELD, new ObjectId(id));

        Order result = collection.find(filter).first();
        return Optional.ofNullable(result);
    }

    @Override
    public boolean updateStatus(String id, OrderStatus status) {
        Bson filter = eq(ID_FIELD, new ObjectId(id));
        Bson updateOperation = set(STATUS_FIELD, status.name());

        UpdateResult result = collection.updateOne(filter, updateOperation, new UpdateOptions().upsert(true));

        return documentUpdated(result);
    }
}
