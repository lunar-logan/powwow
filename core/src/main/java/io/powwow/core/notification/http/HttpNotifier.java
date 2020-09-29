package io.powwow.core.notification.http;

import io.powwow.core.breaker.CircuitBreakerService;
import io.powwow.core.notification.Notification;
import io.powwow.core.notification.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Properties;


@Component
public class HttpNotifier implements Notifier {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final List<String> SCHEMES = List.of("http", "https");

    private final CircuitBreakerService circuitBreakerService;
    private final HttpClient httpClient;

    public HttpNotifier(CircuitBreakerService circuitBreakerService, HttpClient httpClient) {
        this.circuitBreakerService = circuitBreakerService;
        this.httpClient = httpClient;
    }

    @Override
    public List<String> schemes() {
        return SCHEMES;
    }

    @Override
    public void send(Notification notification) {
        final String key = getCircuitBreakerKey(notification);
        circuitBreakerService.get(key, () -> sendInternal(notification));
    }

    private String sendInternal(Notification notification) throws java.io.IOException, InterruptedException {
        HttpRequest request = buildRequest(notification);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if ((response.statusCode() / 100) == 2) {
            log.info("Successful notified url: {} status: {}", notification.getConnectionUri(), response.statusCode());
        } else {
            log.info("Failed to notify url: {} status: {}", notification.getConnectionUri(), response.statusCode());
        }
        return response.body();
    }

    private String getCircuitBreakerKey(Notification notification) {
        URI uri = notification.getConnectionUri();
        return "GET" + ":" + uri.getScheme() + ":" + uri.getHost() + ":" + uri.getPath();
    }

    private HttpRequest buildRequest(Notification notification) {
        // set the uri
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(notification.getConnectionUri()).GET();

        // Set the headers
        Properties properties = notification.getConnectionProperties();
        if (notification.getConnectionProperties() != null) {
            notification.getConnectionProperties()
                    .stringPropertyNames()
                    .forEach(key -> requestBuilder.header(key, properties.getProperty(key)));
        }

        // build the request
        return requestBuilder.build();
    }
}
