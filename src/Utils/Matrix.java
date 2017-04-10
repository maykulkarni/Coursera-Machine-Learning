package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */

public class Matrix {

    /**
     * Holds list of columns indexed by column
     * name.
     */
    public Map<String, List> values;

    /**
     * column names
     */
    public List<String> columnList;

    public String dependentVariable;
    private long rowSize = 0;

    public Matrix() {
        values = new HashMap<>();
    }

    /**
     * Create matrix from CSV
     *
     * @param filePath file path of CSV
     * @return matrix created from CSV file
     * @throws IOException
     */
    public static Matrix fromCSV(String filePath) throws IOException {
        Matrix matrix = new Matrix();
        String absPath = new java.io.File(".").getCanonicalPath();
        BufferedReader br = new BufferedReader(new FileReader(absPath + filePath));
        String currLine = "";
        String[] firstLine = br.readLine().split(",");
        for (int i = 0; i < firstLine.length; i++)
            matrix.values.put(String.valueOf(i), new ArrayList<Double>());
        for (int i = 0; i < firstLine.length; i++)
            matrix.values.get(String.valueOf(i)).add(Double.parseDouble(firstLine[i]));
        long rowSize = 0;
        while ((currLine = br.readLine()) != null) {
            String[] splitLine = currLine.split(",");
            for (int i = 0; i < splitLine.length; i++)
                matrix.values.get(String.valueOf(i)).add(Double.parseDouble(splitLine[i]));
            rowSize++;
        }
        matrix.columnList = new ArrayList<>(matrix.values.keySet());
        matrix.rowSize = rowSize;
        return matrix;
    }

    public static void main(String[] args) throws IOException {
        Matrix matrix = Matrix.fromCSV("/Files/ex1data1.txt");
        System.out.println(matrix.dependentVariable);
        matrix.setDependentVariable("1");
        System.out.println(matrix.dependentVariable);
    }

    /**
     * Print complete matrix
     */
    public void completeMatrix() {
        System.out.println("RowSize : " + rowSize);
        for (int i = 0; i < rowSize; i++) {
            for (String str : columnList) {
                System.out.print(get(str, i) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Get column indexed by column name
     * @param columnName    column name
     * @return List of values
     */
    public List<Object> getColumn(String columnName) {
        return values.get(columnName);
    }

    // TODO
    public void append(String columnName, Number value) {

    }

    /**
     * Returns a row in the matrix
     * @param row       row index
     * @return row of a matrix
     */
    public Map<String, Object> tuple(int row) {
        Map<String, Object> tuple = new HashMap<>();
        for (String str : columnList) {
            tuple.put(str, values.get(str).get(row));
        }
        return tuple;
    }

    /**
     * Get a particular value in matrix
     * indexed by row and column
     * @param column    column name (String)
     * @param index     integer index
     * @return value at the column and row
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

