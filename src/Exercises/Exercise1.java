package Exercises;


import Models.LinearRegressor;
import Models.Regressor;
import Utils.Matrix;

import java.io.IOException;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class Exercise1 {
    /**
     * Exercise 1 Linear Regression in one variable
     * @param args          -
     * @throws IOException  if file CSV not found
     */
    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("\\Files\\Exercise 1\\ex1data1.txt");
        matrix.setDependentVariable("1");
        Regressor linearRegressor = new LinearRegressor();
        linearRegressor.setAlpha(0.01);
        linearRegressor.fit(matrix);
        linearRegressor.result();
    }
}