package Exercise1_Linear_Regression;

import Utils.Matrix;

/**
 * Created by Mayur Kulkarni on 4/5/2017.
 * Email : mayurkulkarni012@gmail.com
 */
public class LinearRegressor {
    private Matrix matrix;
    private String dependentVariable;

    public void fit(Matrix inputMatrix, String dependentVariable) {
        this.matrix = inputMatrix;
        this.dependentVariable = dependentVariable;

    }

    public double costFunction() {
        return 12.0;
    }

    public static void main(String[] args) {

    }
}

