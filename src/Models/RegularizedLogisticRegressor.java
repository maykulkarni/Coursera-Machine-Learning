package Models;


import Utils.Matrix;
import Utils.MiscUtils;

import java.util.Map;

import static Utils.Functions.sigmoid;


/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class RegularizedLogisticRegressor extends Regressor{
    /**
     * Regularization parameter
     */
    private double lambda = 0.1;

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public double costFunction() {
        double error = 0;
        double predictorSquared = 0;
        for (int i = 0; i < rowCount; i++) {
            double actual = (double) matrix.get(dependentVariable, i);
            double prediction = predict(matrix.tuple(i));
            error += actual == 1D ?  Math.log(prediction) : Math.log(1 - prediction);
        }
        // Don't regularize 0th ("def") parameter
        for (int i = 1; i < predictorArray.length; i++) {
            predictorSquared += Math.pow(predictorArray[i], 2);
        }
        return (-error / rowCount) + (lambda/(2*rowCount)) * predictorSquared;
    }

    @Override
    public void fit(Matrix inputMatrix) {
        init(inputMatrix);
        printInitialParams();
        gradientDescent();
    }

    @Override
    public double predict(Map<String, Double> tuple) {
        double prediction = 0;
        for (String str : predictorIndex.keySet()) {
            prediction += predictorArray[predictorIndex.get(str)] * tuple.get(str);
        }
        return sigmoid(prediction);
    }

    private void gradientDescent() {
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
        this.iterations = iteration;
    }

    private double gradient(String col) {
        double sum = 0;
        for (int i = 0; i < rowCount; i++) {
            double pred = predict(matrix.tuple(i));
            double actual = (double) matrix.get(dependentVariable, i);
            sum += (pred - actual)
                    * (col.equals("def") ? 1 : (double) matrix.get(col, i));
        }
        // Don't regularize 0th (def) term
        if (col.equals("def"))
            return sum / rowCount;
        else
            return (sum / rowCount) + (lambda / rowCount) * predictorArray[predictorIndex.get(col)];
    }

    @Override
    public void printInitialParams() {
        MiscUtils.line();
        System.out.println("Alpha: " + alpha);
        System.out.println("Regularization parameter(Lambda): " + lambda);
        System.out.println("Dependent variable : " + dependentVariable);
        System.out.println("Initial Cost : " + costFunction());
        MiscUtils.line();
    }

    public static void main(String[] args) {

    }
}