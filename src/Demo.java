import Exercise1_Linear_Regression.LinearRegressor;
import Utils.Matrix;
import Utils.Normalizer;

import java.io.IOException;

/**
 * Created by mayur on 6/4/17.
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("\\Files\\ex1data2.txt");
        matrix.setDependentVariable("2");
        LinearRegressor lr = new LinearRegressor();
        Normalizer nr = new Normalizer();
        matrix = nr.fit(matrix);
        lr.fit(matrix);
        System.out.println(lr.costFunction());
        lr.gradientDescent();
        System.out.println(nr.meanMap);
        System.out.println(nr.standardDeviationMap);
    }
}
