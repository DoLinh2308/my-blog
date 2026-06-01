package dolinh.mblog.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.minio")
public record MinioProperties (
        String endpoint,
        String accessKey,
        String secretKey,
        String defaultBucket,
        String presignUrlExpiry
) {
}
