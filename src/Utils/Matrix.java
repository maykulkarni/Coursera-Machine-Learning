package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */

public class Matrix {

    /**
     * Holds list of columns indexed by column name
     * name.
     */
    public Map<String, List> values;

    /**
     * column names
     */
    public List<String> columnList;

    public String dependentVariable;
    private long rowSize = 0;

    public boolean isNumericalData = false;

    public Matrix() {
        values = new HashMap<>();
    }

    /**
     * Create matrix from CSV
     *
     * @param filePath      file path of CSV
     * @return              matrix created from CSV file
     * @throws IOException  if the CSV is not found
     */
    public static Matrix fromCSV(String filePath) throws IOException {
        Matrix matrix = new Matrix();
        String absPath = new java.io.File(".").getCanonicalPath();
        BufferedReader br = new BufferedReader(new FileReader(absPath + filePath));
        String currLine;
        String[] firstLine = br.readLine().split(",");
        for (int i = 0; i < firstLine.length; i++)
            matrix.values.put(String.valueOf(i), new ArrayList<>());
        for (int i = 0; i < firstLine.length; i++)
            matrix.values.get(String.valueOf(i)).add(firstLine[i]);
        long rowSize = 1;
        while ((currLine = br.readLine()) != null) {
            String[] splitLine = currLine.split(",");
            for (int i = 0; i < splitLine.length; i++)
                matrix.values.get(String.valueOf(i)).add(splitLine[i]);
            rowSize++;
        }
        matrix.columnList = new ArrayList<>(matrix.values.keySet());
        matrix.rowSize = rowSize;
        return matrix;
    }

    public void attemptNumericalConversion() {
        System.out.println("Attempting to convert data to numerical");
        try {
            for (String col : columnList) {
                List<String> currCol = values.get(col);
                List<Double> modifiedList = currCol.stream()
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());
                values.replace(col, currCol, modifiedList);
            }
            isNumericalData = true;
            System.out.println("Numerical conversion successful");
        } catch (Exception e) {
            System.err.println("Numerical Conversion failed, some operation cannot be permitted");
        }
    }


    /**
     * Print complete matrix
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Row size : ").append(rowSize).append("\n");
        for (int i = 0; i < rowSize; i++) {
            for (String str : columnList) {
                String currValue = String.valueOf(get(str, i));
                sb.append(currValue.length() > 13 ? currValue.substring(0, 13) : currValue).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Get column indexed by column name
     * @param columnName    column name
     * @return              List of values
     */
    public List<Double> getColumn(String columnName) {
        return values.get(columnName);
    }


    /**
     * Returns a row in the matrix
     * @param row       row index
     * @return          row of a matrix
     */
    public Map<String, Double> tuple(int row) {
        Map<String, Double> tuple = new HashMap<>();
        tuple.put("def", 1d);
        for (String str : columnList) {
            tuple.put(str, (double) values.get(str).get(row));
        }
        return tuple;
    }

    /**
     * Get a particular value in matrix
     * indexed by row and column
     * @param column    column name (String)
     * @param index     integer index
     * @return          value at the column and row
     */
    public Object get(String column, int index) {
        return values.get(column).get(index);
    }

    /**
     * Set the current dependent variable
     * @param dependentVariable new dependent variable name
     * @throws RuntimeException if dependent variable is invalid
     */
    public void setDependentVariable(String dependentVariable) {
        if (!values.keySet().contains(dependentVariable)) throw new RuntimeException("unknown Dependent variable");
        this.dependentVariable = dependentVariable;
    }
}

