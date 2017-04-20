package Utils;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.stream.Collectors;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class Normalizer {
    public Map<String, Double> meanMap = new HashMap<>();
    public Map<String, Double> standardDeviationMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("\\Files\\test.txt");
        matrix.setDependentVariable("1");
        new Normalizer().fit(matrix);
        System.out.println(matrix.values);
    }

    /**
     * Uses Z-Score normalization and applies on every
     * column of the matrix
     *
     * @param matrix        input matrix
     * @return              Normalized matrix
     */
    public Matrix fit(Matrix matrix) {
        matrix.attemptNumericalConversion();
        for (String columns : matrix.columnList)
            if (!columns.equals(matrix.getDependentVariable())) {
                List<Double> currentColumn = matrix.getColumn(columns);
                double mean = mean(currentColumn);
                double standardDeviation = standardDeviation(currentColumn, mean);
                meanMap.put(columns, mean);
                standardDeviationMap.put(columns, standardDeviation);
                List<Double> modifiedColumn = currentColumn.stream()
                        .map(x -> (x - mean) / standardDeviation)
                        .collect(Collectors.toList());
                matrix.values.replace(columns, currentColumn, modifiedColumn);
            }
        return matrix;
    }

    /**
     * Calculates Standard Deviation
     * @param currentColumn column to be calculated on
     * @param mean          mean of the current column
     * @return              standard deviation
     */
    private double standardDeviation(List<Double> currentColumn, double mean) {
        DoubleAdder da = new DoubleAdder();
        currentColumn.stream()
                .mapToDouble(x -> x)
                .forEach((x) -> da.add(Math.pow(x - mean, 2)));
        return Math.sqrt(da.doubleValue() / currentColumn.size());
    }

    /**
     * Calculates mean
     * @param currentColumn column to be calculated on
     * @return              mean
     */
    private double mean(List<Double> currentColumn) {
        double sum = currentColumn.stream()
                .mapToDouble(x -> x)
                .sum();
        return sum / currentColumn.size();
    }
}