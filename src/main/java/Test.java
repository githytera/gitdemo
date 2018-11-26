import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Test {
    public static void main(String[] args) throws IOException {
        String filePath = "d:\\images\\peopleMarkerYellow.png";
        String watermark = "d:\\images\\test\\test1.png";
        String filePathOut = "d:\\images\\test\\test2.png";
        //watermark(位置，水印图，透明度)
        Thumbnails.of(filePath)
                .size(64, 64)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(watermark)), 1.0f)
                .outputQuality(0.8f)
                .toFile(filePathOut);

//        Thumbnails.of("images/a380_1280x1024.jpg")
//                .size(1280, 1024)
//                .watermark(Positions.CENTER, ImageIO.read(new File("images/watermark.png")), 0.5f)
//                .outputQuality(0.8f)
//                .toFile("c:/a380_watermark_center.jpg");

    }


}
