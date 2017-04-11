package Exercise2_Logistic_Regression;


import Utils.Matrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class LogisticRegressor {
    /**
     * Holds the coefficient of every input
     * value.
     * Prediction = \sum (Pi * Xi)
     */
    private Map<String, Double> predictors;

    /**
     * List of the columns
     */
    private List<String> columnList;

    /**
     * Number of rows in the matrix
     */
    private int rowCount = 0;

    /**
     * Controls the learning rate of gradient
     * descent
     */
    private double alpha = 0.1;

    /**
     * States maximum number of iterations in
     * gradient descent
     */
    private long maxNumberOfIterations = 2000;

    /**
     * Minimum accuracy to be achieved before
     * converging.
     * Note: Will terminate if #iterations > max number
     * of iterations
     */
    private double epsilon = 1e-5;

    /**
     * Regularization parameter
     */
    private double lambda = 0.1;


    private Matrix matrix;

    /**
     * Dependent variable in the matrix
     */
    private String dependentVariable;


    /**
     * Train linear regression using Gradient Descent
     *
     * @param inputMatrix matrix to be trained on
     */
    public void fit(Matrix inputMatrix) {
        this.matrix = inputMatrix;
        this.dependentVariable = matrix.dependentVariable;
        int indepVarSize = matrix.values.size() - 1;
        predictors = new HashMap<>(indepVarSize + 1);
        columnList = matrix.columnList;
        rowCount = matrix.values.get(matrix.values.keySet().iterator().next()).size();
        for (String str : columnList) predictors.put(str, 0d);
        predictors.put("def", 0d);
        predictors.remove(dependentVariable);
        System.out.println("Dependent Variable : " + dependentVariable);
        System.out.println("Predictors " + predictors);
        gradientDescent();
    }

    /**
     * Calculates the error with current values of
     * predictors
     *
     * @return Mean Squared Error
     */
    public double costFunction() {
        double error = 0;
        for (int i = 0; i < rowCount; i++) {
            double cost = Math.pow((predict(matrix.tuple(i)) - (Double) matrix.get(dependentVariable, i)), 2);
            error += sigmoid(cost);
        }
        return error * (1D / (2D * rowCount));
    }

    private double sigmoid(double value) {
        return 1D/(1D + Math.exp(-value));
    }

    /**
     * Predicts on a given row
     *
     * @param tuple input row
     * @return prediction value
     */
    public double predict(Map<String, Object> tuple) {
        double prediction = 0;
        tuple.put("def", 1d);
        for (String str : predictors.keySet()) {
            prediction += predictors.get(str) * (Double) tuple.get(str);
        }
        return prediction;
    }

    /**
     * Calculates the gradient
     * @param col       column to calculate on
     * @return
     */
    private double gradient(String col) {
        double sum = 0;
        for (int i = 0; i < rowCount; i++) {
            double pred = predict(matrix.tuple(i));
            double actual = (Double) matrix.get(dependentVariable, i);
            sum += (pred - actual)
                    * (col.equals("def") ? 1 : (Double) matrix.get(col, i));
        }
        return sum / rowCount;
    }

    /**
     * Apply gradient descent
     */
    public void gradientDescent() {
        alpha = 0.01;
        Map<String, Object> temp = new HashMap<>(rowCount);
        long iteration = 0;

        while (costFunction() >= epsilon && iteration < maxNumberOfIterations) {
            for (String col : predictors.keySet()) {
                temp.put(col, predictors.get(col) - alpha * gradient(col));
            }
            predictors = (Map) ((HashMap) temp).clone();
            iteration++;
        }
        System.out.println("Iterations : " + iteration);
        System.out.println(predictors);
        System.out.println("Error after descending: ");
        System.out.println(costFunction());
    }
}