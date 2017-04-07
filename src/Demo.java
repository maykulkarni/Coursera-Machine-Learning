import Exercise1_Linear_Regression.LinearRegressor;
import Utils.Matrix;

import java.io.IOException;

/**
 * Created by mayur on 6/4/17.
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("\\Files\\ex1data1.txt");
        matrix.setDependentVariable("1");
        LinearRegressor lr = new LinearRegressor();
        lr.fit(matrix);
//        System.out.println(lr.costFunction());
        lr.gradientDescent();
//        matrix.completeMatrix();
    }
}
