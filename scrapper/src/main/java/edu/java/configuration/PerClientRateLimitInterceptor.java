package edu.java.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class PerClientRateLimitInterceptor implements HandlerInterceptor {

    @Value("${bucket4j.capacity}")
    private Integer bucketCapacity;
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @SuppressWarnings("checkstyle:MagicNumber") @Override
    public boolean preHandle(
        @NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
        @NotNull Object handler
    ) throws Exception {

        String ip = getIp(request);
        Bucket bucket = cache.computeIfAbsent(ip, this::newBucket);

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            response.sendError(
                HttpStatus.TOO_MANY_REQUESTS.value(),
                "You have exhausted your API Request Quota"
            );
            return false;
        }
    }

    private Bucket newBucket(String ip) {
        Bandwidth bandwidth = Bandwidth.builder()
            .capacity(bucketCapacity)
            .refillIntervally(bucketCapacity, Duration.ofHours(1))
            .build();
        return Bucket.builder()
            .addLimit(bandwidth)
            .build();
    }

    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
