package com.imageresizer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Utility class for resizing and cropping images.
 */
public class ImageResizer {

    /**
     * Resizes the given BufferedImage to the specified width and height.
     *
     * @param originalImage the original image to be resized
     * @param targetWidth   the desired width
     * @param targetHeight  the desired height
     * @return a new BufferedImage containing the resized image
     */
    public static BufferedImage resize(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        // Use bilinear interpolation for better quality
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resizedImage;
    }

    /**
     * Crops the given BufferedImage to the center based on the desired crop width and height.
     * If the requested crop dimensions exceed the original image size, they are adjusted accordingly.
     *
     * @param originalImage the original image to be cropped
     * @param cropWidth     the desired crop width
     * @param cropHeight    the desired crop height
     * @return a new BufferedImage containing the center-cropped area
     */
    public static BufferedImage cropToCenter(BufferedImage originalImage, int cropWidth, int cropHeight) {
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();

        // Ensure the crop dimensions do not exceed the original dimensions
        cropWidth = Math.min(cropWidth, imageWidth);
        cropHeight = Math.min(cropHeight, imageHeight);

        // Calculate the top-left corner for center crop
        int cropX = (imageWidth - cropWidth) / 2;
        int cropY = (imageHeight - cropHeight) / 2;

        return originalImage.getSubimage(cropX, cropY, cropWidth, cropHeight);
    }
}
