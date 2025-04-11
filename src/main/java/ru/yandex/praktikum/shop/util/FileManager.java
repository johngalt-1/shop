package ru.yandex.praktikum.shop.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileManager {
    private final Path imagesPath;

    public FileManager(@Value("${images.dir}") String imagesDir) {
        this.imagesPath = Path.of(imagesDir);
        createImagesDirectory();
    }

    protected void createImagesDirectory() {
        File imagesDirectory = new File(imagesPath.toString());
        if (!imagesDirectory.exists()) {
            try {
                Files.createDirectory(imagesPath);
            } catch (IOException e) {
                throw new RuntimeException("Error while creating directory for images");
            }
        }
    }
}
