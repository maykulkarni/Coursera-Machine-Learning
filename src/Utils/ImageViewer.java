package Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mayur Kulkarni on 4/16/2017.
 *
 */
public class ImageViewer {

    public static int[][] completeGrid() throws IOException {
        int[][] completeGrid = new int[200][200];
        String path = "C:\\Users\\kulkarni_my\\Desktop\\Coursera-Machine-Learning\\Files\\Exercise 3\\character_data.csv";
        BufferedReader br = new BufferedReader(new FileReader(path));
        int[] jumper = createJumper(100, 4900);
        int jumperSize = 0;
        for(int h = 0; h < 10; h++) {
            for (int i = 0; i < 10; i++, jumperSize++) {
                for (int j = 0; j < jumper[jumperSize]; j++) {
                    br.readLine();
                }
                String[] splitLine = br.readLine().split(",");
                int stringCounter = 0;
                int offsetX = h*20;
                int offsetY = i*20;
                for (int j = offsetX ; j < offsetX + 20; j++) {
                    for (int k = offsetY; k < offsetY + 20; k++, stringCounter++) {
                        completeGrid[j][k] = (int) (Double.parseDouble(splitLine[stringCounter]) * 20);
                    }
                }
            }
        }
        return completeGrid;
    }

    private static int[] createJumper(int size, int limit) {
        int[] jumper = new int[size];
        java.util.List<Integer> jumperList = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            jumperList.add(random.nextInt(limit));
        }
        jumperList.sort(Integer::compareTo);
        jumper[0] = jumperList.get(0);
        for (int i = jumperList.size() - 1; i > 0; i--) {
            jumper[i] = jumperList.get(i) - jumperList.get(i - 1);
        }
        return jumper;
    }

    public static void main(String[] args) throws IOException {
        EventQueue.invokeLater(() -> {
            ImageFrame imageFrame = null;
            try {
                imageFrame = new ImageFrame(completeGrid());
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            imageFrame.setResizable(false);
            imageFrame.setVisible(true);
        });
    }

    public static Image createImage(int[][] arr) {
        int width = 200;
        int height = 200;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        File f = null;
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int a = 235; //alpha
                int ans = arr[x][y];
                int r, g, b = ans == 0 ? 255 : ans; //arr[x][y] < 0 ? ans + 20 : ans;
                r = g = b;
                int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
                img.setRGB(x, y, p);
            }
        }
        return img;
    }
}

class ImageFrame extends JFrame{
    public ImageFrame(int[][] arr) {
        setTitle("Hello !");
        setSize(210, 210);
        add(new ImageComponent(arr));
    }
}

class ImageComponent extends JComponent {
    private Image img;
    public ImageComponent(int[][] arr) {
        img = ImageViewer.createImage(arr);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }
}