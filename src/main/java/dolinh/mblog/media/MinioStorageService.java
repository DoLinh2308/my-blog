package dolinh.mblog.media;

import dolinh.mblog.properties.MinioProperties;
import dolinh.mblog.utils.RetryUtil;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioStorageService {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    @Qualifier("uploadExecutor")
    private final Executor executor;
    @SneakyThrows
    @PostConstruct
    public void createBucketIfNotExists(){
        boolean isExist = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(minioProperties.defaultBucket()).build()
        );
    }

    @SneakyThrows
    public String store(MultipartFile file, String storedName, Authentication authentication){
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename != null
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        long fileSize = file.getSize();
        String contentType = file.getContentType();
        String generatedFilename = UUID.randomUUID() + ext;
        String folderName = Objects.requireNonNull(authentication.getPrincipal()).toString();
        String objectName = folderName + generatedFilename;
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minioProperties.defaultBucket())
                        .object(objectName)
                        .stream(file.getInputStream(), fileSize, (long) -1 )
                        .contentType(contentType)
                        .build()
        );
        return "Upload success!";
    }

    @SneakyThrows
    private CompletableFuture<String> uploadAsync(MultipartFile file, String storedName, Authentication authentication){
        return CompletableFuture.supplyAsync(()->{
            long start = System.currentTimeMillis();
            try{
                return RetryUtil.execute(3, 1000, () -> {
                    return this.store(file, storedName, authentication);

                });
            }
            finally {
                long time = System.currentTimeMillis() - start;
                log.info("upload time {} ms for file {} ", time, file.getOriginalFilename());
            }
        }, executor);
    }

    @SneakyThrows
    public void storeMultipleFile(List<MultipartFile> files) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        List<CompletableFuture<String>> futures = files.stream()
                .map(file -> {
                    String storedName = UUID.randomUUID()
                            + "_" + file.getOriginalFilename();

                    return uploadAsync(file, storedName, authentication);
                })
                .toList();

        CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        ).join();
    }
}
