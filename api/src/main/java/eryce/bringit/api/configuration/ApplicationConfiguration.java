package eryce.bringit.api.configuration;

import eryce.bringit.api.service.RetrofitFactory;
import eryce.bringit.shared.clients.BackofficeClient;
import eryce.bringit.shared.clients.OrderProcessingClient;
import eryce.bringit.shared.clients.OrderSearchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final BackOfficeConfiguration backOfficeConfiguration;
    private final OrderProcessingConfiguration orderProcessingConfiguration;
    private final OrderSearchConfiguration orderSearchConfiguration;
    private final RetrofitFactory retrofitFactory;

    @Bean
    BackofficeClient getBackOfficeClient() {
        return retrofitFactory.getConfiguredRetrofit(backOfficeConfiguration.getUrl())
                .create(BackofficeClient.class);
    }

    @Bean
    OrderProcessingClient getOrderProcessingClient() {
        return retrofitFactory.getConfiguredRetrofit(orderProcessingConfiguration.getUrl())
                .create(OrderProcessingClient.class);
    }

    @Bean
    OrderSearchClient getOrderSearchClient() {
        return retrofitFactory.getConfiguredRetrofit(orderSearchConfiguration.getUrl())
                .create(OrderSearchClient.class);
    }

}
