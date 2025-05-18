package org.example.inconceptproject.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FilenameUtils;
import org.example.inconceptproject.deserializer.CoverImageDeserializer;
import org.example.inconceptproject.enums.FileDownloadStatus;
import org.example.inconceptproject.model.Book;
import org.example.inconceptproject.model.FileInfo;
import org.example.inconceptproject.repository.BookRepository;
import org.example.inconceptproject.repository.FileInfoRepository;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Service
public class CoverImageService {

    private final String imageStorageDir;
    private final String thumbnailDir;

    public CoverImageService(
            @Value("${app.image.storage.dir:uploads/images/}") String imageStorageDir,
            @Value("${app.thumbnail.dir:uploads/thumbnails/}") String thumbnailDir) {
        this.imageStorageDir = imageStorageDir;
        this.thumbnailDir = thumbnailDir;
    }

    private static final Logger logger = Logger.getLogger(CoverImageService.class.getName());

    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .callTimeout(7, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private FileInfoRepository fileInfoRepository;


    @Transactional
    @Async
    public void processBookCover(List<Book> books) {
        List<CompletableFuture<Void>> futures = books.stream().map(book -> {
            return downloadAsync(book.getCoverImg()).thenAccept(newPath -> {
                book.getCoverImg().setFileName(newPath);
            });
        }).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        System.out.println("Processed " + books.size() + " books");
        System.out.println("Done");
    }

    public CompletableFuture<String> downloadAsync(FileInfo fileInfo) {
        return CompletableFuture.completedFuture(download(fileInfo));
    }

    public String download(FileInfo fileInfo) {
        String imageUrl = null;
        fileInfo.setStatus(FileDownloadStatus.DOWNLOADING);
        try {
            imageUrl = fileInfo.getFileUrl();
            if (imageUrl == null || imageUrl.isEmpty()) return null;

            String fileName = generateFileName(imageUrl);
            File originalFile = new File(imageStorageDir + fileName);
            File thumbnailFile = new File(thumbnailDir + fileName);

            if (originalFile.exists() && thumbnailFile.exists()) {
                return originalFile.getPath();
            }


            BufferedImage image = downloadImage(imageUrl);
            if (image == null) return null;

            ImageIO.write(image, "jpg", originalFile);

            BufferedImage thumbnail = Scalr.resize(image, Scalr.Method.SPEED, 100, 100);
            ImageIO.write(thumbnail, "jpg", thumbnailFile);

            System.out.println(originalFile.getPath());
            fileInfo.setStatus(FileDownloadStatus.COMPLETED);
            fileInfo.setFileFormat("jpg");
            fileInfo.setFileUrl(imageUrl);
            fileInfo.setFilePath(originalFile.getName());
            fileInfo.setFileName(originalFile.getName());
            fileInfoRepository.save(fileInfo);
            return originalFile.getPath();

        } catch (Exception e) {
            fileInfo.setStatus(FileDownloadStatus.FAILED);
            logger.warning("Image download/processing failed: " + imageUrl + " - " + e.getMessage());
            return null;
        }
    }

    private BufferedImage downloadImage(String url) {
        Request request = new Request.Builder().url(url).build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                logger.warning("Failed to download image: " + url);
                return null;
            }
            try (InputStream input = response.body().byteStream()) {
                return ImageIO.read(input);
            }
        } catch (Exception e) {
            logger.warning("Error during image request: " + url + " - " + e.getMessage());
            return null;
        }
    }

    private String generateFileName(String url) {
        String extension = FilenameUtils.getExtension(url);
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        if (!extension.equalsIgnoreCase("jpg")) {
            fileName = FilenameUtils.getBaseName(fileName) + ".jpg";
        }
        return fileName;
    }

    @PostConstruct
    public void setupStorage() {
        ensureDirectories();
        logger.info("Storage directories created at startup.");
    }

    @PreDestroy
    public void cleanupStorage() {
        deleteDirectory(new File(imageStorageDir));
        deleteDirectory(new File(thumbnailDir));
        logger.info("Storage directories deleted at shutdown.");
    }

    private void ensureDirectories() {
        new File(imageStorageDir).mkdirs();
        new File(thumbnailDir).mkdirs();
    }

    private void deleteDirectory(File dir) {
        if (dir.exists()) {
            File[] contents = dir.listFiles();
            if (contents != null) {
                for (File file : contents) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            dir.delete();
        }
    }

}