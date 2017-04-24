package Models;

import Utils.Matrix;

import java.util.Map;


/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */

public class LinearRegressor extends Regressor{


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
     * @return Mean Squared Error
     */
    @Override
    public double costFunction() {
        double error = 0;
        for (int i = 0; i < rowCount; i++) {
            double prediction = predict(i);
            double actual = (double) matrix.get(dependentVariable, i);
            error += Math.pow(prediction - actual, 2);
        }
        return error / (2D * rowCount);

    }

    /**
     * Predicts on a given row
     *
     * @param row input row
     * @return prediction value
     */
    public double predict(int row) {
        double prediction = 0;
        for (String str : predictorIndex.keySet()) {
            prediction += predictorArray[predictorIndex.get(str)] * (double) matrix.get(str, row);
        }
        return prediction;
    }

    @Override
    public double predict(Map<String, Double> tuple) {
        double prediction = 0;
        tuple.put("def", 1d);
        for (String str : predictorIndex.keySet()) {
            prediction += predictorArray[predictorIndex.get(str)] * tuple.get(str);
        }
        return prediction;
    }

    /**
     * Calculates the gradient
     *
     * @param col       column to calculate on
     * @return          gradient
     */
    private double gradient(String col) {
        double sum = 0;
        for (int i = 0; i < rowCount; i++) {
            double prediction = predict(i);
            double actual = (double) matrix.get(dependentVariable, i);
            sum += (prediction - actual)
                    * (col.equals("def") ? 1 : (double) matrix.get(col, i));
        }
        return sum / rowCount;
    }

    /**
     * Applies gradient descent
     */
    public void gradientDescent() {
        int iteration = 0;
        double[] temp = new double[predictorArray.length];
        double currentCost;
        double previousCost = Double.MAX_VALUE;

        while ((currentCost = costFunction()) >= epsilon
                && iteration < maxNumberOfIterations
                &&(previousCost - currentCost) > 1e-8) {
            previousCost = currentCost;
            for (String col : predictorIndex.keySet()) {
                int index = predictorIndex.get(col);
                temp[index] = predictorArray[index] - alpha*gradient(col);
            }
            System.arraycopy(temp, 0, predictorArray, 0, predictorArray.length);
            iteration++;
        }
        this.iterations = iteration;
    }
}

