package Exercises;

import Models.LogisticRegressor;
import Models.Regressor;
import Utils.Matrix;
import Utils.Normalizer;

import java.io.IOException;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class Exercise2 {
    /**
     * Logistic Regression
     * @param args          -
     * @throws IOException  if CSV is not found
     */
    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("\\Files\\Exercise 2\\ex2data1.txt");
        matrix.setDependentVariable("2");
        Regressor regressor = new LogisticRegressor();
        new Normalizer().fit(matrix);
        regressor.fit(matrix);
        regressor.result();
    }
}