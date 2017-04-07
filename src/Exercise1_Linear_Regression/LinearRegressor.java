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
    Map<String, Double> predictors;
    List<String> columnList;
    int rowCount = 0;
    double alpha = 0;
    double epsilon = 1e-8;
    private Matrix matrix;
    private String dependentVariable;
    private int indepVarSize;

    public static void main(String[] args) {
        new LinearRegressor().costFunction();
    }

    public void fit(Matrix inputMatrix) {
        this.matrix = inputMatrix;
        this.dependentVariable = matrix.dependentVariable;
        this.indepVarSize = matrix.values.size() - 1;
        predictors = new HashMap<>(indepVarSize + 1);
        columnList = matrix.columnList;
        rowCount = matrix.values.get(matrix.values.keySet().iterator().next()).size();
        for (String str : columnList) predictors.put(str, 0d);
        predictors.put("def", 0d);
        predictors.remove(dependentVariable);
        System.out.println("Dependent Variable : " + dependentVariable);
        System.out.println("Predictors " + predictors);
        System.out.println("Column List : " + columnList);
    }

    public double costFunction() {
        double error = 0;
        for (int i = 0; i < rowCount; i++) {
            error += Math.pow((predict(matrix.tuple(i)) - (Double) matrix.get(dependentVariable, i)), 2);
        }
        return error * (1D / (2D * rowCount));
    }

    public double predict(Map<String, Object> tuple) {
        double prediction = 0;
        tuple.put("def", 1d);
        for (String str : predictors.keySet()) {
            prediction += predictors.get(str) * (Double) tuple.get(str);
        }
        return prediction;
    }

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

    public void gradientDescent() {
        alpha = 0.01;
        Map<String, Object> temp = new HashMap<>(rowCount);
        int iteration = 0;

        while (costFunction() >= epsilon && iteration < 2000) {
            if (iteration % 1500 == 0)
                System.out.println(costFunction());
            for (String col : predictors.keySet()) {
                temp.put(col, predictors.get(col) - alpha * gradient(col));
            }
            predictors = (Map) ((HashMap) temp).clone();
            iteration++;
        }
        System.out.println("Iterations : " + iteration);
        System.out.println(predictors);
    }
}

