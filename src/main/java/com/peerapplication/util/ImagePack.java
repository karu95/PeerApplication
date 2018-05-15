package com.peerapplication.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.Serializable;
import java.util.Arrays;

public class ImagePack implements Serializable {
    private byte[] imageBytes;
    private int height;
    private int width;
    private int imageType;

    public ImagePack(BufferedImage image) {
        imageBytes = ((DataBufferByte) image.getData().getDataBuffer()).getData();
        height = image.getHeight();
        width = image.getWidth();
        imageType = image.getType();
    }

    public BufferedImage getImage() {
        BufferedImage image = new BufferedImage(width, height, imageType);
        image.setData(Raster.createRaster(image.getSampleModel(), new DataBufferByte(imageBytes, imageBytes.length), new Point()));
        return image;
    }

    public boolean equals(ImagePack imagePack) {
        boolean equal = true;
        if (height != imagePack.height) {
            equal = false;
            System.out.println(6.1);
        } else if (width != imagePack.width) {
            equal = false;
            System.out.println(6.2);
        } else if (imageType != imagePack.imageType) {
            equal = false;
            System.out.println(6.3);
        } else if (!(Arrays.equals(imageBytes, imagePack.imageBytes))) {
            equal = false;
            System.out.println(6.4);
        }
        return equal;
    }
}
