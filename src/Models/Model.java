package Models;

import Utils.Matrix;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public interface Model {
    void fit(Matrix inputMatrix);
    double costFunction();
}
