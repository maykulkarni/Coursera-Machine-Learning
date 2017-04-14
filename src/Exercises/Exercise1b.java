package Exercises;

import Models.LinearRegressor;
import Models.Regressor;
import Utils.Matrix;
import Utils.Normalizer;

import java.io.IOException;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class Exercise1b {
    /**
     * Linear Regression with multiple variables and Normalization
     * @param args          -
     * @throws IOException  if CSV file not found
     */
    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("\\Files\\Exercise 1\\ex1data2.txt");
        matrix.setDependentVariable("2");
        Regressor regressor = new LinearRegressor();
        Normalizer normalizer = new Normalizer();
        Matrix normalized = normalizer.fit(matrix);
        regressor.fit(normalized);
        regressor.result();
    }
}