package Models;

import Utils.Matrix;

import java.io.FileNotFoundException;
import java.io.IOException;
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
        for (Object currentDependentColumn : getUniqueOutcomes()) {
            matrix.setDependentVariable("out_" + currentDependentColumn.toString());
            Regressor currentRegressor = new LogisticRegressor();
            currentRegressor.maxNumberOfIterations = 10;
            currentRegressor.fit(matrix);
            currentRegressor.result();
            currentRegressor.saveResultToCSV("result_" + currentDependentColumn.toString());
            regressorList.add(currentRegressor);
        }
    }

    public void predict() {

    }

    public static void main(String[] args) throws IOException {

        Matrix matrix = Matrix.fromCSV("\\Files\\Exercise 3\\MNIST_train.csv");
        matrix.setDependentVariable("400");
        OneVsAllClassifier o = new OneVsAllClassifier(matrix, null);
        o.trainClassifier();
    }
}