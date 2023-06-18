package eryce.bringit.orderprocessing.repository.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class ConnectionProperties {
    private final String host;
    private final int port;
}
