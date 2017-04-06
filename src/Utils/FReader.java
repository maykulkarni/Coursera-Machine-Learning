package Utils;

import java.io.*;

/**
 * Created by Mayur Kulkarni on 4/5/2017.
 * Email : mayurkulkarni012@gmail.com
 */
public class FReader {
    private BufferedReader br;

    public FReader(String file) throws IOException {
        String absPath = new java.io.File(".").getCanonicalPath();
        br = new BufferedReader(new FileReader(absPath + file));
    }


//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new FileReader(absPath + "\\Files\\ex1data1.txt"));
//        String x = "";
//
//        while ((x = br.readLine()) != null) {
//            System.out.println(x);
//        }
//    }
}
