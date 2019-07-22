package com.malex;

import com.malex.model.ImageModel;
import com.malex.service.ImageService;
import com.malex.service.impl.ImageServiceImpl;

import java.net.URL;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

public class Main {

    /**
     * Error message
     */
    private static final String ERROR_MESSAGE = "Error %s the file %s";

    /**
     * init ImageService class
     */
    private static ImageService service;

    static {
        service = new ImageServiceImpl();
    }

    /**
     * Run app
     */
    public static void main(String[] args) {
        String imageName = "java.jpg";
        String inputImagePath = findPathToImageFile(imageName);

        checkState(service.resize(inputImagePath, ImageModel.builder()
                .name("fixed")
                .scaledWidth(1024)
                .scaledHeight(768)
                .extension("jpg")
                .build()));

        checkState(service.resize(inputImagePath, ImageModel.builder()
                .name("small")
                .percent(0.5)
                .extension("jpg")
                .build()));
    }

    /**
     * Find the file path in the resource folder using the class loader
     */
    private static String findPathToImageFile(String pathToFile) {
        URL resourceFolder = ImageServiceImpl.class.getClassLoader().getResource(pathToFile);
        return Optional.ofNullable(resourceFolder)
                .orElseThrow(() -> new IllegalArgumentException(String.format(ERROR_MESSAGE, "reading file", pathToFile)))
                .getPath();
    }
}
