package Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mayur Kulkarni on 4/16/2017.
 *
 */
public class ImageViewer {

    public static int[][] completeGrid() throws IOException {
        int[][] completeGrid = new int[200][200];
        String inputData = "C:\\Users\\kulkarni_my\\Desktop\\Coursera-Machine-Learning\\Files\\Exercise 3\\character_data.csv";
        String predictions = "C:\\Users\\kulkarni_my\\Desktop\\Coursera-Machine-Learning\\Files\\Exercise 3\\output.csv";
        BufferedReader inputReader = new BufferedReader(new FileReader(inputData));
        BufferedReader predictionReader = new BufferedReader(new FileReader(predictions));
        PrintWriter writer = new PrintWriter("C:\\Users\\kulkarni_my\\Desktop\\Coursera-Machine-Learning\\Files\\Exercise 3\\pixelData.csv");
        int[] jumper = createJumper(100, 4900);
        int jumperSize = 0;
        for(int h = 0; h < 10; h++) {
            for (int i = 0; i < 10; i++, jumperSize++) {
                for (int j = 0; j < jumper[jumperSize]; j++) {
                    inputReader.readLine();
                    predictionReader.readLine();
                }
                String currLine = inputReader.readLine();
                writer.print(currLine + ",");
                String[] splitLine = currLine.split(",");
                String output = predictionReader.readLine();
                writer.println(output);
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
        writer.close();
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
        ImageFrame imageFrame = null;
        imageFrame = new ImageFrame(completeGrid());
        imageFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        imageFrame.setResizable(false);
        imageFrame.setVisible(true);
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
                int r, g, b = ans == 0 ? 255 : ans;
                r = g = b;
                int p = (a<<24) | (r<<16) | (g<<8) | b;
                img.setRGB(x, y, p);
            }
        }
        return img;
    }
}

class ImageFrame extends JFrame{
    public ImageFrame(int[][] arr) {
        setTitle("Digits");
        setSize(210, 240);
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