package Exercises;

import Models.OneVsAllClassifier;
import Utils.Matrix;

import java.io.IOException;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class Exercise3 {
    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("\\Files\\Exercise 3\\MNIST_train.csv");
        matrix.setDependentVariable("400");
        OneVsAllClassifier o = new OneVsAllClassifier(matrix, null);
        o.useTrainedRegressors();
//        o.trainClassifier();
        o.test("\\Files\\Exercise 3\\MNIST_test.csv");
    }
}