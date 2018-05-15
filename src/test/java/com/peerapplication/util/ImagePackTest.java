package com.peerapplication.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ImagePackTest {

    private static final BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
    private static final ImagePack accurateImagePack = new ImagePack(bufferedImage);

    private ImagePack imagePack;

    @AfterEach
    void tearDown() {
        System.out.println("Test completed!");
    }

    @Test
    void equals() {
        imagePack = new ImagePack(new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR));
        assertTrue(imagePack.equals(accurateImagePack));
    }

}