package ro.dragomiralin.springcloudcircuitbreaker;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoService {

    @CircuitBreaker(name = "circuitbreaker", fallbackMethod = "fallbackPhotos")
    public String fetchPhotos() {
        return "Fetching photos";
    }

    private String fallbackPhotos(Throwable throwable) {
        return "Fallback! Failed to fetch photos";
    }
}
