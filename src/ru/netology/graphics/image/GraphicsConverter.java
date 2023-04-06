package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GraphicsConverter implements TextGraphicsConverter {

    protected int maxHeight;
    protected int maxWidth;
    protected double maxRatio;
    private double ratio;
    protected TextColorSchema textColorSchema;
    private double changeSize;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        int width = img.getWidth();
        int height = img.getHeight();


        if (width > height) {
            ratio = (double) width / height;
        }
        if (height > width) {
            ratio = (double) height / width;
        }


        if (ratio > maxRatio) {
            throw new BadImageSizeException(ratio, maxRatio);
        }

        int newWidth = width;
        int newHeight = height;

        if (newWidth > maxWidth) {
            changeSize = (double) newWidth / maxWidth;
            newWidth /= changeSize;
            newHeight /= changeSize;
        }

        if (newHeight > maxHeight) {
            changeSize = (double) newHeight / maxHeight;
            newWidth /= changeSize;
            newHeight /= changeSize;
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        ImageIO.write(bwImg, "png", new File("out.png"));
        WritableRaster bwRaster = bwImg.getRaster();

        char[][] picture = new char[newHeight][newWidth]; // массив для хранения символов 'c' [высота картинки, т.е. кол-во строк] [ширина картинки, т.е. кол-во столбцов]

        for (int i = 0; i < bwRaster.getHeight(); i++) { // индекс i пробегается по высоте, т.е. перебираем строки
            for (int ii = 0; ii < bwRaster.getWidth(); ii++) { // индекс ii пробегается по ширине, т.е. перебираем столбцы
                int color = bwRaster.getPixel(ii, i, new int[3])[0]; // пиксель на нужных нам координатах, сначала номер столбца (индекс ii), потом номер строки (индекс i)
                char c = new ColorSchema().convert(color);
                picture[i][ii] = c; // заполняем массив picture[номер строки][номер столбца] подобранными символами 'c'
            }
        }

        StringBuilder result = new StringBuilder();
        for (char[] x : picture) { // сначала пробегаемся по строкам  - первый индекс массива picture [i]
            for (char y : x) { // затем - по столбцам - второй индекс массива picture [ii]
                result.append(y); // берем символ
                result.append(y);
            }
            result.append("\n"); // в конце строки переходим на новую
        }
        return result.toString(); // вывод на экран
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.textColorSchema = schema;
    }
}