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
    Matrix matrix;

    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("\\Files\\test.txt");
        matrix.setDependentVariable("1");
        new Normalizer().fit(matrix);
        System.out.println(matrix.values);
    }

    public Matrix fit(Matrix matrix) {
        for (String columns : matrix.columnList)
            if (!columns.equals(matrix.dependentVariable)) {
                List<Object> currentColumn = matrix.getColumn(columns);
                double mean = mean(currentColumn);
                double standardDeviation = standardDeviation(currentColumn, mean);
                meanMap.put(columns, mean);
                standardDeviationMap.put(columns, standardDeviation);
                List<Object> modifiedColumn = currentColumn.stream()
                        .map((x) -> ((double) x - mean) / standardDeviation)
                        .collect(Collectors.toList());
                matrix.values.replace(columns, currentColumn, modifiedColumn);
            }
        matrix.completeMatrix();
        return matrix;
    }

    private double standardDeviation(List currentColumn, double mean) {
        DoubleAdder da = new DoubleAdder();
        currentColumn.stream()
                .mapToDouble(x -> (double) x)
                .forEach((x) -> da.add(Math.pow(x - mean, 2)));
        return Math.sqrt(da.doubleValue() / currentColumn.size());
    }

    private double mean(List currentColumn) {
        double sum = currentColumn.stream()
                .mapToDouble(x -> (double) x)
                .sum();
        return sum / currentColumn.size();
    }
}