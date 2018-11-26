import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 图片加文字水印
 */
public class WatermarkUtil {

    public static BufferedImage waterMarkByText(int width, int heigth, String text, Color color,
                                                Font font, Double degree, float alpha) {
        BufferedImage buffImg = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = buffImg.createGraphics();
        buffImg = g2d.getDeviceConfiguration()
                .createCompatibleImage(width, heigth, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = buffImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (null != degree) {
            g2d.rotate(Math.toRadians(degree), (double) buffImg.getWidth() / 2,
                    (double) buffImg.getHeight() / 2);
        }
        g2d.setColor(color);
        g2d.setFont(font);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        float realWidth = getRealFontWidth(text);
        float fontSize = font.getSize();
        float x = 0.5f * width - 0.5f * fontSize * realWidth;
        float y = 0.5f * heigth + 0.5f * fontSize;
        g2d.drawString(text, x, y);
        g2d.dispose();
        return buffImg;

    }

    private static float getRealFontWidth(String text) {
        int len = text.length();
        float width = 0f;
        for (int i = 0; i < len; i++) {
            if (text.charAt(i) < 256) {
                width += 0.5f;
            } else {
                width += 1.0f;
            }
        }
        return width;
    }

    public static void append(List<BufferedImage> inputFileNameList, String outputFileName, boolean isX) {
        if (inputFileNameList == null || inputFileNameList.size() == 0) {
            return;
        }
        try {
            boolean isFirstPng = true;
            BufferedImage outputImg = null;
            int outputImgW = 0;
            int outputImgH = 0;
            for (BufferedImage pngFileName : inputFileNameList) {
                if (isFirstPng) {
                    isFirstPng = false;
                    outputImg = pngFileName;
                    outputImgW = outputImg.getWidth();
                    outputImgH = outputImg.getHeight();
                } else {
                    BufferedImage appendImg =pngFileName;
                    int appendImgW = appendImg.getWidth();
                    int appendImgH = appendImg.getHeight();

                    if (isX) {
                        outputImgW = outputImgW + appendImgW;
                        outputImgH = outputImgH > appendImgH ? outputImgH : appendImgH;
                    } else {
                        outputImgW = outputImgW > appendImgW ? outputImgW : appendImgW;
                        outputImgH = outputImgH + appendImgH;
                    }
                    Graphics2D g2d = outputImg.createGraphics();
                    BufferedImage imageNew = g2d.getDeviceConfiguration().createCompatibleImage(outputImgW, outputImgH,
                            Transparency.TRANSLUCENT);
                    g2d.dispose();
                    g2d = imageNew.createGraphics();

                    int oldImgW = outputImg.getWidth();
                    int oldImgH = outputImg.getHeight();
                    g2d.drawImage(outputImg, 0, 0, oldImgW, oldImgH, null);
                    if (isX) {
                        g2d.drawImage(appendImg, oldImgW, 0, appendImgW, appendImgH, null);
                    } else {
                        g2d.drawImage(appendImg, 0, oldImgH, appendImgW, appendImgH, null);
                    }
                    g2d.dispose();
                    outputImg = imageNew;
                }
            }
            writeImageLocal(outputFileName, outputImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeImageLocal(String fileName, BufferedImage image) {
        if (fileName != null && image != null) {
            try {
                File file = new File(fileName);
                ImageIO.write(image, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void composeImage(String inPath,String outPath,String text,int width,int heigth,String fontStyle,int fontSize) throws IOException {
        Font font = new Font(fontStyle, Font.ROMAN_BASELINE, fontSize);
        BufferedImage bi = waterMarkByText(width, heigth, text, Color.black, font, 0.0, 0.9f);
        BufferedImage appendImg = ImageIO.read(new File(inPath));
        List<BufferedImage> bufferedImages=new ArrayList<>();
        bufferedImages.add(bi);
        bufferedImages.add(appendImg);
        append(bufferedImages, outPath, false);

    }

    public static void main(String[] args) {
        Font font = new Font("宋体", Font.ROMAN_BASELINE, 12);
        BufferedImage waterMarkByText=waterMarkByText(96,96,"哈尔滨还能达科技", Color.black,font,0.0, 0.9f);
        writeImageLocal("D://images//test//test4.png", waterMarkByText);
//        long start=System.currentTimeMillis();
//        for(int i=0;i<700;i++) {
//            try {
//                composeImage("D://images//peopleMarkerYellow.png", "D://images//test//test4"+i+".png", "ceshisuyin"+i, 96, 70, "宋体", 12);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.printf((System.currentTimeMillis()-start)+"");
    }
}