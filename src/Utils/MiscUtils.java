package Utils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class MiscUtils {

    public static void line() {
        System.out.println();
        for (int i = 0; i < 50; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    public static Matrix addNDegrees(Matrix matrix, int power) {
        List<String> columns = matrix.getIndependentColumns();
        System.out.println("Indep cols");
        System.out.println(columns);
        Map<String, Integer> columnIndex = new HashMap<>();
        for (int i = 0; i < columns.size(); i++) columnIndex.put(columns.get(i), i);
        int[] powerMatrix = new int[columns.size()];
        int N = powerMatrix.length;
        while (true) {
            if (powerMatrix[N - 1] == power) {
                boolean foundSpot = false;
                for (int i = N - 2; i >= 0; i--) {
                    if (powerMatrix[i] < power) {
                        powerMatrix[i]++;
                        for (int j = i + 1; j < N; j++) powerMatrix[j] = 0;
                        foundSpot = true;
                        break;
                    }
                }
                if (!foundSpot) break;
            } else {
                powerMatrix[N - 1]++;
            }
            addOverMatrix(matrix, powerMatrix, columnIndex);
        }
        return matrix;
    }

    private static void addOverMatrix(Matrix matrix, int[] powerMatrix, Map<String, Integer> columnIndex) {
        List<Double> newColumn = new ArrayList<>();
        for (int i = 0; i < matrix.rowCount; i++) {
            double val = 0;
            for(String col : matrix.getIndependentColumns()) {
                val += powerMatrix[columnIndex.get(col)] == 0 ?
                        0 :
                        Math.pow((double)matrix.get(col, i), powerMatrix[columnIndex.get(col)]);
            }
            newColumn.add(val);
        }
        StringBuilder newColumnName = new StringBuilder();
        for (int i = 0; i < powerMatrix.length; i++)
            newColumnName.append(matrix.getIndependentColumns().get(i))
                         .append("^").append(powerMatrix[i]);
        matrix.addColumn(newColumnName.toString(), newColumn);
    }

    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("\\Files\\test.txt");
        matrix.setDependentVariable("2");
        matrix.attemptNumericalConversion();
        addNDegrees(matrix, 3);
        matrix.toCSV("text-my.csv");
    }
}