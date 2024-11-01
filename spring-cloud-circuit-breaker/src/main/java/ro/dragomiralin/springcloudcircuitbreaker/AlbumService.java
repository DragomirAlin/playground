package ro.dragomiralin.springcloudcircuitbreaker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final CircuitBreakerFactory circuitBreakerFactory;
    private RestTemplate restTemplate = new RestTemplate();



    public String getAlbumList(boolean failure) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");

        return circuitBreaker.run(() -> failure ? fetchAlbumsWithFailure() : fetchAlbums(), throwable -> "Fallback! Failed to fetch albums");
    }

    public String fetchAlbumsWithFailure() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");

        return circuitBreaker.run(() -> {
            log.info("Fetching albums with error");
            throw new RuntimeException("Failed to fetch albums");

        }, throwable -> {
            log.error("Failed to fetch albums", throwable);
            return "Fallback! Failed to fetch albums";
        });
    }

    private String fetchAlbums() {
        log.info("Fetching albums");
        if (Math.random() < 0.5) {
            throw new RuntimeException("Failed to fetch albums");
        }
        String url = "https://jsonplaceholder.typicode.com/albums";
        return restTemplate.getForObject(url, String.class);
    }
}
