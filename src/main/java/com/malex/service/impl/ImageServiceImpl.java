package com.malex.service.impl;

import com.malex.model.ImageModel;
import com.malex.service.ImageService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This program demonstrates how to resize an image
 */
public class ImageServiceImpl implements ImageService {

    /**
     * Default coordinates X and Y value
     */
    private static final int DEFAULT_START_COORDINATE = 0;

    public boolean resize(String inputImagePath, ImageModel imageModel) {
        try {
            ImageModel resizeImageModel = determineImageModel(imageModel, inputImagePath);
            int scaledHeight = resizeImageModel.getScaledHeight();
            int scaledWidth = resizeImageModel.getScaledWidth();
            String imageExtension = resizeImageModel.getExtension();
            String imageFullName = resizeImageModel.getFullFileName();

            // reads input image
            File inputFile = new File(inputImagePath);
            BufferedImage inputImage = ImageIO.read(inputFile);

            // creates output image
            BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

            // scales the input image to the output image
            drawsSpecifiedImage(scaledWidth, scaledHeight, inputImage, outputImage);

            // writes to output file
            return ImageIO.write(outputImage, imageExtension, new File(imageFullName));

        } catch (IOException ex) {
            throw new IllegalArgumentException("The `" + inputImagePath + "` file not found, error message", ex);
        }
    }

    /**
     * Draws the input image to the output image
     */
    private void drawsSpecifiedImage(int scaledWidth,
                                     int scaledHeight,
                                     BufferedImage inputImage,
                                     BufferedImage outputImage) {
        Graphics2D g2d = outputImage.createGraphics();
        Image image = createScaleBufferedImage(scaledWidth, scaledHeight, inputImage);
        g2d.drawImage(image, DEFAULT_START_COORDINATE, DEFAULT_START_COORDINATE, scaledWidth, scaledHeight, null);
        g2d.dispose();
    }

    /**
     * Creates image with additional params
     */
    private Image createScaleBufferedImage(int scaledWidth, int scaledHeight, BufferedImage inputImage) {
        return inputImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
    }

    private ImageModel determineImageModel(ImageModel imageModel, String inputImagePath) {
        return imageModel.isImageScalable()
                ? imageModel
                : determineScalabilityImageModel(imageModel, inputImagePath);
    }

    /**
     * Determine the scalability of the image model
     */
    private ImageModel determineScalabilityImageModel(ImageModel imageModel, String inputImagePath) {
        File inputFile = new File(inputImagePath);
        try {
            double percent = imageModel.getPercent();
            if (percent == 0) {
                return imageModel;
            }

            BufferedImage inputImage = ImageIO.read(inputFile);
            imageModel.setScaledHeight((int) (inputImage.getHeight() * percent));
            imageModel.setScaledWidth((int) (inputImage.getWidth() * percent));

            return imageModel;
        } catch (IOException ex) {
            throw new IllegalArgumentException("The `" + inputFile + "` file not found, error message", ex);
        }
    }
}
