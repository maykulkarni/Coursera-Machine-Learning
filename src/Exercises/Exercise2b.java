package Exercises;

import Models.RegularizedLogisticRegressor;
import Utils.Matrix;

import java.io.IOException;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class Exercise2b {
    /**
     * Logistic Regression with Regularization
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("\\Files\\Exercise 2\\ex2data2.txt");
        matrix.setDependentVariable("2");
        matrix.addNDegrees(6);
        RegularizedLogisticRegressor x = new RegularizedLogisticRegressor();
        x.setAlpha(0.3);
        x.setLambda(1);
        x.setMaxNumberOfIterations(8000);
        x.fit(matrix);
        x.result();
    }
}