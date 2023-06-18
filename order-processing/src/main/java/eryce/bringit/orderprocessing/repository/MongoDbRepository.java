package eryce.bringit.orderprocessing.repository;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import eryce.bringit.orderprocessing.configuration.MongoDbConfiguration;
import eryce.bringit.orderprocessing.repository.configuration.ConnectionProperties;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;
import java.util.Collections;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Connects to an instance of MongoDB.
 * Responsible of closing the connection to MongoDB.
 */
public class MongoDbRepository extends Connectible implements AutoCloseable{

    protected MongoClient mongoClient;
    protected CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    public MongoDbRepository(final MongoDbConfiguration configuration) {
        super(new ConnectionProperties(configuration.getHost(), configuration.getPort()));
    }

    protected boolean documentUpdated(final UpdateResult result) {
        return result.getUpsertedId() != null || result.getModifiedCount() != 0;
    }

    protected boolean documentInserted(final InsertOneResult result){
        return result.getInsertedId() != null;
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    @Override
    public void connect() {
        mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Collections.singletonList(new ServerAddress(super.connectionProperties.getHost(), super.connectionProperties.getPort()))))
                        .build());
    }
}
