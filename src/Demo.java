import Utils.Matrix;

import java.io.IOException;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class Demo {
    public static void main(String[] args) throws IOException {

        Matrix matrix = Matrix.fromCSV("\\Files\\Exercise 3\\MNIST.csv");
        double time = System.currentTimeMillis();
        double[][] vals = matrix.getValueArray();
        double res = System.currentTimeMillis() - time;
//        for (int i = 0; i < vals.length; i++) {
//            for (int j = 0; j < vals[0].length; j++) {
//                System.out.print(vals[i][j] + " ");
//            }
//            System.out.println();
//        }
        System.out.println(res);
    }
}