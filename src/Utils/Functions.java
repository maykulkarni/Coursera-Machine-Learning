package Utils;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class Functions {
    public static double sigmoid(double value) {
        return 1D/(1D + Math.exp(-value));
    }
}