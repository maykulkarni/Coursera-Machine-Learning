package Exercise1_Linear_Regression;

import Utils.Matrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mayur Kulkarni on 4/5/2017.
 * Email : mayurkulkarni012@gmail.com
 */

public class LinearRegressor {
    private Matrix matrix;
    private String dependentVariable;
    private int indepVarSize;
    Map<String, Double> predictors;
    List<String> columnList;
    int rowCount = 0;
    double alpha = 0;
    double epsilon = 1e-5;

    public void fit(Matrix inputMatrix) {
        this.matrix = inputMatrix;
        this.dependentVariable = matrix.dependentVariable;
        this.indepVarSize = matrix.values.size() - 1;
        predictors = new HashMap<>(indepVarSize + 1);
        columnList = matrix.columnList;
        columnList.add("def");
        rowCount = matrix.values.get(dependentVariable).size();
        for (String str : columnList) predictors.put(str, 0d);
        predictors.put("def", 0d);
    }

    public double costFunction() {
        double error = 0;
        for (int i = 0; i < rowCount; i++) {
            error += (1 / rowCount) * Math.pow((predict(matrix.tuple(i)) - (Double) matrix.get(dependentVariable, i)), 2);
        }
        return error;
    }

    public double predict(Map<String, Object> tuple) {
        double prediction = 0;
        tuple.put("def", 1d);
        for (String str : columnList) {
            prediction += predictors.get(str) * (Double) tuple.get(str);
        }
        return prediction;
    }

    public double gradient(String col) {
        double sum = 0;
        for (int i = 0; i < rowCount; i++) {
            sum += (predict(matrix.tuple(i)) - (Double) matrix.get(dependentVariable, i))
                    * (col.equals("def") ? 1 : (Double) matrix.get(col, i));
        }
        return sum / rowCount;
    }


    public void gradientDescent() {
        Map<String, Object> temp = new HashMap<>(rowCount);
        while (costFunction() < epsilon) {
            for (String col : columnList) {
                temp.put(col, predictors.get(col) - alpha * gradient(col));
            }
            predictors = (Map) ((HashMap) temp).clone();
        }
    }

    public static void main(String[] args) {
        new LinearRegressor().costFunction();
    }
}

