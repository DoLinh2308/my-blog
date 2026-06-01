package dolinh.mblog.media;

public record StorageResponse(
        String nameFile,
        String objectName,
        Long fileSize,
        String contentType,
        String presignedUrlDownload
) {
}