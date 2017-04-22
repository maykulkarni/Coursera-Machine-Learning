package Models;


import Utils.Matrix;
import Utils.MiscUtils;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public abstract class Regressor {
    /**
     * Holds the index of predictors
     * stored in predictor array
     */
    Map<String, Integer> predictorIndex;

    /**
     * Holds the prediction coefficients.
     * Prediction = \sum (Coeff_i * X_i)
     */
    double[] predictorArray;

    /**
     * List of the columns
     */
    List<String> independentColumnList;

    /**
     * Number of rows in the matrix
     */
    int rowCount = 0;

    /**
     * Controls the learning rate of gradient
     * descent
     */
    double alpha = 0.1;

    /**
     * States maximum number of iterations in
     * gradient descent
     */
    long maxNumberOfIterations = 2000;

    /**
     * Minimum accuracy to be achieved before
     * converging.
     * Note: Will terminate if #iterations > max number
     * of iterations
     */
    double epsilon = 1e-5;


    long iterations = 0;


    Matrix matrix;

    /**
     * Dependent variable in the matrix
     */
    String dependentVariable;

    public String name;

    public void setName(String name) {
        this.name = name;
    }

    HashMap<String, Integer> columnIndex;

    public abstract double costFunction();

    public abstract void fit(Matrix inputMatrix);

    public abstract double predict(int row);

    public abstract double predict(Map<String, Double> tuple);

    public void setAlpha(double newAlpha) {
        this.alpha = newAlpha;
    }

    public void setMaxNumberOfIterations(int maxNumberOfIterations) { this.maxNumberOfIterations = maxNumberOfIterations;}

    public void result() {
        System.out.println("Iterations: " + iterations);
        System.out.println("Cost after descending: " + costFunction());
//        for (String params : predictorIndex.keySet()) {
//            System.out.println(params + " : " + predictorArray[predictorIndex.get(params)]);
//        }
        MiscUtils.line();
    }


    void init(Matrix inputMatrix) {
        this.matrix = inputMatrix;
        this.dependentVariable = matrix.getDependentVariable();
        int indepVarSize = matrix.values.size() - 1;
        predictorIndex = new HashMap<>(indepVarSize + 1);
        predictorArray = new double[indepVarSize + 2];
        independentColumnList = matrix.getIndependentColumns();
        inputMatrix.attemptNumericalConversion();
        rowCount = matrix.values.get(matrix.values.keySet().iterator().next()).size();
        // first value in the predictorArray will be the
        // coefficient for default value, hence the index
        // of default will be 0
        predictorIndex.put("def", 0);
        for (int i = 0, size = 1; i < independentColumnList.size(); i++) {
            predictorIndex.put(independentColumnList.get(i), size);
            size++;
        }
        predictorIndex.remove(dependentVariable);
    }

    public void printInitialParams() {
        MiscUtils.line();
        System.out.println("Alpha: " + alpha);
        System.out.println("Dependent variable : " + dependentVariable);
        System.out.println("Initial Cost : " + costFunction());
        MiscUtils.line();
    }


    public void saveResultToCSV (String fileName) throws FileNotFoundException {
        try(PrintWriter writer = new PrintWriter(fileName.endsWith("csv") ? fileName : fileName + ".csv")) {
            for (String predictorName : predictorIndex.keySet()) {
                writer.print(predictorName + ",");
                writer.println(predictorArray[predictorIndex.get(predictorName)]);
            }
        }
    }


    public void copyPredictorValuesFromCSV (String filePath) throws IOException {
        String absPath = new java.io.File(".").getCanonicalPath();
        BufferedReader br = new BufferedReader(new FileReader(absPath + filePath));
        String currLine;
        Map<String, Double> tempMap = new HashMap<>();
        while ((currLine = br.readLine()) != null) {
            String[] predictorVal = currLine.split(",");
            tempMap.put(predictorVal[0], Double.parseDouble(predictorVal[1]));
        }
        this.predictorArray = new double[tempMap.size() + 1];
        this.predictorIndex = new HashMap<>();
        int index = 0;
        // WARNING: "def" index may or may not be 0
        for (String predictor : tempMap.keySet()) {
            predictorIndex.put(predictor, index);
            predictorArray[index] = tempMap.get(predictor);
            index++;
        }
    }

    public static void main(String[] args) throws IOException {
        // random test
        Regressor x = new LogisticRegressor();
        x.copyPredictorValuesFromCSV("\\result_1.csv");
        System.out.println(Arrays.toString(x.predictorArray));
        for (String zz : x.predictorIndex.keySet()) {
            System.out.println(zz + " " + x.predictorArray[x.predictorIndex.get(zz)]);
        }
    }
}

















