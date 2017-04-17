package Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Mayur Kulkarni on 4/16/2017.
 */
public class ImageViewer {
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\kulkarni_my\\Desktop\\Coursera-Machine-Learning\\Files\\Exercise 3\\character_data.csv";
        BufferedReader br = new BufferedReader(new FileReader(path));
        for (int i = 0; i < 2430; i++) {
            br.readLine();
        }
        String line = br.readLine();
        String[] splitLine = line.split(",");
        double[] arr = new double[400];
        for (int i = 0; i < 400; i++) {
            arr[i] = Double.parseDouble(splitLine[i]);
        }
        int x = 0;
        int[][] nicearr = new int[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++, x++) {
                nicearr[i][j] = (int) Math.abs(arr[x] * 200);
            }
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.println(nicearr[i][j]);
            }
        }
        createImage(nicearr);
    }

    public static void createImage(int[][] arr) {
        int width = 20;
        int height = 20;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        File f = null;
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int a = 235; //alpha
                int ans = arr[x][y];
                int r, g, b = ans == 0 ? 0 : ans; //arr[x][y] < 0 ? ans + 20 : ans;
                System.out.println(b);
                r = g = b;
                int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
                img.setRGB(x, y, p);
            }
        }
        //write image
        try{
            f = new File("D:\\Output.png");
            ImageIO.write(img, "png", f);
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }
}
