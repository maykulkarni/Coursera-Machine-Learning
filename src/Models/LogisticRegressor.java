package Models;


import Utils.Matrix;

import java.util.HashMap;
import java.util.Map;

import static Utils.Functions.sigmoid;

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
    @Override
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
    @Override
    public double costFunction() {
        double error = 0;
        for (int i = 0; i < rowCount; i++) {
            double actual = (double) matrix.get(dependentVariable, i);
            double prediction = predict(i);
            error += actual == 1D ?  Math.log(prediction) : Math.log(1 - prediction);
        }

        return -error / rowCount;
    }


    /**
     * Predicts on a given row using sigmoid function
     *
     * @param row input row
     * @return prediction value
     */
    @Override
    public double predict(int row) {
        double prediction = 0;
        for (String str : predictorIndex.keySet()) {
            prediction += predictorArray[predictorIndex.get(str)] * (double) matrix.get(str, row);
        }
        return sigmoid(prediction);
    }

    @Override
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
        if (col.equals("def")) {
            for (int i = 0; i < rowCount; i++) {
                double pred = predict(i);
                double actual = (double) matrix.get(dependentVariable, i);
                sum += (pred - actual);
            }
            return sum / rowCount;
        } else {
            for (int i = 0; i < rowCount; i++) {
                double pred = predict(i);
                double actual = (double) matrix.get(dependentVariable, i);
                sum += (pred - actual)
                        * (double) matrix.get(col, i);
            }

            return sum / rowCount;
        }
    }

    /**
     * Apply gradient descent
     */
    public void gradientDescent() {
        long iteration = 0;
        double currentCost;
        double previousCost = Double.MAX_VALUE;
        double[] temp = new double[predictorArray.length];
        System.out.println();
        while ((currentCost = costFunction()) >= epsilon
                && iteration < maxNumberOfIterations
                && previousCost - currentCost > 1e-8) {
            previousCost = currentCost;
            System.out.print("\rIteration: " + iteration + " Cost: " + currentCost);
            for (String col : predictorIndex.keySet()) {
                int index = predictorIndex.get(col);
                temp[index] = predictorArray[index] - alpha*gradient(col);
            }
            System.arraycopy(temp, 0, predictorArray, 0, predictorArray.length);
            iteration++;
        }
        System.out.println();
    }
}