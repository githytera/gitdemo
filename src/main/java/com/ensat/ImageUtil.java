package com.ensat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author cong
 * @date 2018年08月29日 10:50
 * @desc 创建图片工具类
 */
public class ImageUtil {

    /**
     * 创建新的图片
     */
    public static BufferedImage getImage(String waterMarkContent, String fileExt) throws Exception {
        int width = 64;
        int height = 12;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        return addWatermark(bi, waterMarkContent);
    }

    /**
     * @description 生成水印图片
     * @param sourceImgPath    源图片路径
     * @param waterMarkContent 水印内容
     */
    public static BufferedImage addWatermark(BufferedImage sourceImgPath, String waterMarkContent) {
        // 水印字体，大小
        Font font = new Font("宋体", Font.BOLD, 12);
        // 水印颜色
        Color markContentColor = Color.blue;
        // 设置水印文字的旋转角度
        Integer degree = 0;
        // 设置水印透明度
        float alpha = 0.5f;
        OutputStream outImgStream = null;
        try {
            // 文件转化为图片
            Image srcImg = sourceImgPath;
            // 获取图片的宽
            int srcImgWidth = srcImg.getWidth(null);
            // 获取图片的高
            int srcImgHeight = srcImg.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            // 得到画笔
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            // 设置水印颜色
            g.setColor(markContentColor);
            // 设置字体
            g.setFont(font);
            // 设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree));
            }
            // 画出水印,并设置水印位置
            g.drawString(waterMarkContent, 0, 9);
            // 释放资源
            g.dispose();
            // 直接输出文件用来测试结果
            ImageIO.write(bufImg, "png", new File("d:/images/test/test1.png"));
            return transferAlpha(bufImg);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            try {
                if (outImgStream != null) {
                    outImgStream.flush();
                    outImgStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        }
        return null;
    }

    /**
     * @description 转化成透明背景的图片
     * @param bufImg    源图片
     */
    public static BufferedImage transferAlpha(BufferedImage bufImg) {

        try {
            Image image = bufImg;
            ImageIcon imageIcon = new ImageIcon(image);
            BufferedImage bufferedImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
            int alpha = 0;
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                    int rgb = bufferedImage.getRGB(j2, j1);
                    int R = (rgb & 0xff0000) >> 16;
                    int G = (rgb & 0xff00) >> 8;
                    int B = (rgb & 0xff);
                    if (((255 - R) < 30) && ((255 - G) < 30) && ((255 - B) < 30)) {
                        rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                    }
                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }
            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
            // 直接输出文件用来测试结果
            //ImageIO.write(bufferedImage, "png", new File("C:/Users/cong/Desktop/test2.png"));
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        BufferedImage bufferedImage=getImage("生成水印图片","");
//        BufferedImage bufferedImage = ImageIO.read(new FileInputStream("D:\\images\\peopleMarkerYellow.png"));
//        addWatermark(bufferedImage,"12313");




    }
}
