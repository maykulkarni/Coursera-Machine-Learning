package Models;


import Utils.Matrix;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class LogisticRegressor extends Regressor{

    public LogisticRegressor() {
        columnIndex = new HashMap<>();
    }


    /**
     * Train linear regression using Gradient Descent
     *
     * @param inputMatrix matrix to be trained on
     */
    public void fit(Matrix inputMatrix) {
        init(inputMatrix);
        printInitialParams();
        gradientDescent();
    }

    /**
     * Calculates the error with current values of
     * predictorIndex
     *
     * @return Sigmoid error
     */
    public double costFunction() {
        double error = 0;
        for (int i = 0; i < rowCount; i++) {
            double actual = (double) matrix.get(dependentVariable, i);
            double prediction = predict(matrix.tuple(i));
            error += actual == 1D ?  Math.log(prediction) : Math.log(1 - prediction);
        }
        return -error / rowCount;
    }


    public double costFunctionRegularised() {
        double error = 0;
        double predictorSquared = 0;
        for (int i = 0; i < rowCount; i++) {
            double actual = (double) matrix.get(dependentVariable, i);
            double prediction = predict(matrix.tuple(i));
            error += actual == 1D ?  Math.log(prediction) : Math.log(1 - prediction);
        }
        for (int i = 0; i < predictorArray.length; i++) {
            predictorSquared += Math.pow(predictorArray[i], 2);
        }
        return (-error / rowCount) + (lambda/(2*rowCount)) * predictorSquared;
    }


    private double sigmoid(double value) {
        return 1D/(1D + Math.exp(-value));
    }

    /**
     * Predicts on a given row using sigmoid function
     *
     * @param tuple input row
     * @return prediction value
     */
    public double predict(Map<String, Double> tuple) {
        double prediction = 0;
        for (String str : predictorIndex.keySet()) {
            prediction += predictorArray[predictorIndex.get(str)] * tuple.get(str);
        }
        return sigmoid(prediction);
    }

    /**
     * Calculates the gradient
     * @param col       column to calculate on
     * @return          gradient
     */
    private double gradient(String col) {
        double sum = 0;
        for (int i = 0; i < rowCount; i++) {
            double pred = predict(matrix.tuple(i));
            double actual = (double) matrix.get(dependentVariable, i);
            sum += (pred - actual)
                    * (col.equals("def") ? 1 : (double) matrix.get(col, i));
        }
        return sum / rowCount;
    }

    /**
     * Apply gradient descent
     */
    public void gradientDescent() {
        long iteration = 0;
        double currentCost;
        double previousCost = Double.MAX_VALUE;
        double[] temp = new double[predictorArray.length];
        while ((currentCost = costFunction()) >= epsilon
                && iteration < maxNumberOfIterations
                && previousCost - currentCost > 1e-8) {
            previousCost = currentCost;
            for (String col : predictorIndex.keySet()) {
                int index = predictorIndex.get(col);
                temp[index] = predictorArray[index] - alpha*gradient(col);
            }
            System.arraycopy(temp, 0, predictorArray, 0, predictorArray.length);
            iteration++;
        }
        System.out.println("Iterations : " + iteration);
        System.out.println(predictorIndex);
        for (String col : predictorIndex.keySet()) {
            System.out.println(col + " : " + predictorArray[predictorIndex.get(col)]);
        }
        System.out.println("Error after descending: ");
        System.out.println(costFunction());
        System.out.println(Arrays.toString(predictorArray));
    }
}