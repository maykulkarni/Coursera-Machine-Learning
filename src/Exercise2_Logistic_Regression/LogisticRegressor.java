package Exercise2_Logistic_Regression;


import Utils.Matrix;
import Utils.MiscUtils;

import java.util.Arrays;
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
    private Map<String, Integer> predictors;

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
    private long maxNumberOfIterations = 20000;

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

    private HashMap<String, Integer> columnIndex;

    private double[] predictorArray;

    public LogisticRegressor() {
        columnIndex = new HashMap<>();
    }


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
        predictorArray = new double[indepVarSize + 1];
        columnList = matrix.columnList;
        rowCount = matrix.values.get(matrix.values.keySet().iterator().next()).size();
        predictors.put("def", 0);
        for (int i = 0; i < columnList.size(); i++) predictors.put(columnList.get(i), i + 1);
        predictors.remove(dependentVariable);
        MiscUtils.line();
        System.out.println("Dependent variable : " + dependentVariable);
        System.out.println("Predictors " + predictors);
        System.out.println("Initial Cost : " + costFunction());
        System.out.println("Gradient at initial theta");
        for (String col : predictors.keySet()) {
            System.out.println(col + " : " + gradient(col));
        }
        MiscUtils.line();
        gradientDescent();
    }

    /**
     * Calculates the error with current values of
     * predictors
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
        for (String str : predictors.keySet()) {
            prediction += predictorArray[predictors.get(str)] * tuple.get(str);
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

        while ((currentCost = costFunction()) >= epsilon
                && iteration < maxNumberOfIterations
                && previousCost - currentCost > 1e-8) {
            previousCost = currentCost;
            for (String col : predictors.keySet()) {
                int index = predictors.get(col);
                predictorArray[index] = predictorArray[index] - alpha*gradient(col);
            }
            iteration++;
        }
        System.out.println("Iterations : " + iteration);
        System.out.println(predictors);
        for (String col : predictors.keySet()) {
            System.out.println(col + " : " + predictorArray[predictors.get(col)]);
        }
        System.out.println("Error after descending: ");
        System.out.println(costFunction());
        System.out.println(Arrays.toString(predictorArray));
    }
}