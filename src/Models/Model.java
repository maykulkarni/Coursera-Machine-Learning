package Models;

import Utils.Matrix;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 * Dont know if this is relevant fixme
 */
public interface Model {
    void fit(Matrix inputMatrix);
    double costFunction();
}
