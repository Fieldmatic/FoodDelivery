package eryce.bringit.orderprocessing.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mongodb")
@Getter
@Setter
public class MongoDbConfiguration {
    private String host;
    private int port;
    private String database;
    private String collection;
}
