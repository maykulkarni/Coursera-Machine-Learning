package Models;

import Utils.Matrix;
import Utils.MiscUtils;

import java.io.*;
import java.util.*;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class OneVsAllClassifier {
    /*
    1. Create pixelData.csv from MNIST.csv
    1a Visualize pixel data
    2. Binarize
    3. for each output variable, train a LogRegressor
    4. classify pixelData.csv using these predictors
     */

    private Matrix matrix;
    private Regressor regressor;

    public OneVsAllClassifier(){}

    public OneVsAllClassifier(Matrix matrix, Regressor regressor) {
        this.matrix = matrix;
        this.regressor = regressor;
    }

    List<String> uniqueOutcomesList;
    List<Regressor> regressorList;

    public List getUniqueOutcomes() {
        if (uniqueOutcomesList == null) {
            List outputColumn = matrix.getColumn(matrix.getDependentVariable());
            Set uniqueVals = new HashSet(outputColumn);
            uniqueOutcomesList = new ArrayList<>(uniqueVals);
        }
        return uniqueOutcomesList;
    }

    public void binarize() {
        String dependentColumn = matrix.getDependentVariable();
        for (Object uniqueVal : getUniqueOutcomes()) {
            List<Double> newColumn = new ArrayList<>((int)matrix.rowCount);
            List dependentColumnValues = matrix.getColumn(dependentColumn);
            for (Object value : dependentColumnValues) {
                if (Objects.equals(value, uniqueVal)) newColumn.add(1D);
                else newColumn.add(0D);
            }
            matrix.addColumn("out_" + uniqueVal.toString(), newColumn, true);
        }
    }

    public void trainClassifier() throws FileNotFoundException {
        regressorList = new ArrayList<>();
        binarize();
        System.out.println(matrix.getIndependentColumns());
        for (Object currentDependentColumn : getUniqueOutcomes()) {
            matrix.setDependentVariable("out_" + currentDependentColumn.toString());
            Regressor currentRegressor = new LogisticRegressor();
            currentRegressor.maxNumberOfIterations = 15;
            currentRegressor.fit(matrix);
            currentRegressor.setName("out_" + currentDependentColumn.toString());
            currentRegressor.result();
            currentRegressor.saveResultToCSV("result_" + currentDependentColumn.toString());
            regressorList.add(currentRegressor);
        }
    }

    public void predict(Map<String, Double> tuple) {
        double threshold = 0.5d;
        double prediction;
        List<Double> list = new ArrayList<>();
        for (Regressor currRegressor : regressorList) {
            prediction = currRegressor.predict(tuple);
            System.out.println("Classifier " + currRegressor.name + " has proba: " + prediction);
            list.add(prediction);
        }
        double max = list.stream().max(Double::compareTo).get();
        for (int i = 0; i < regressorList.size(); i++) {
            if (list.get(i) == max) {
                System.out.println("Max: " + (i + 1));
            }
        }
    }

    public void useTrainedRegressors() throws IOException {
        regressorList = new ArrayList<>();
        for (Object currentDependentColumn : getUniqueOutcomes()) {
            Regressor currentRegressor = new LogisticRegressor();
            currentRegressor.setName(currentDependentColumn.toString());
            currentRegressor.copyPredictorValuesFromCSV("\\result_" + currentDependentColumn.toString() + ".csv");
            regressorList.add(currentRegressor);
        }
    }

    public void test(String fileName) throws IOException {
        String absPath = new File(".").getCanonicalPath();
        BufferedReader br = new BufferedReader(new FileReader(absPath + fileName));
        String currLine;
        while ((currLine = br.readLine()) != null) {
            Map<String, Double> tuple = new HashMap<>();
            String[] splitLine = currLine.split(",");
            int index = 0;
            for (String str : splitLine) {
                tuple.put(String.valueOf(index), Double.parseDouble(str));
                index++;
            }
            Double actual = tuple.get("400");
            tuple.remove("400");
            MiscUtils.line();
            System.out.println("Actual: " + actual);
            predict(tuple);
            MiscUtils.line();
        }
    }

    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("\\Files\\Exercise 3\\MNIST_train.csv");
        matrix.setDependentVariable("400");
        matrix.toCSV("imadiscodancer");
        OneVsAllClassifier o = new OneVsAllClassifier(matrix, null);
        o.useTrainedRegressors();
//        o.trainClassifier();
        o.test("\\Files\\Exercise 3\\MNIST_test.csv");
    }
}