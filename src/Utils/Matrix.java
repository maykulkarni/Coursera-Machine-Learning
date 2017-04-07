package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mayur Kulkarni on 4/5/2017.
 * Email : mayurkulkarni012@gmail.com
 */

public class Matrix {
    public Map<String, List> values;
    private String[] featureList;
    private HashMap<String, Integer> columnMap;
    public List<String> columnList;
    public String dependentVariable;

    public Matrix(String[] featureList, String dependentVariable) {
        values = new HashMap<>();
        this.featureList = featureList;
        this.dependentVariable = dependentVariable;
        this.columnMap = new HashMap<>(featureList.length);
        for(int i = 0; i < featureList.length; i++) {
            columnMap.put(featureList[i], i);
        }
    }

    public void append(String columnName, Number value) {

    }

    public Map<String, Object> tuple(int row) {
        Map<String, Object> tuple = new HashMap<>();
        for (String str : columnList) tuple.put(str, values.get(str).get(row));
        return tuple;
    }

    public Object get(String column, int index) {
        return values.get(column).get(index);
    }

    public Matrix() {
        values = new HashMap<>();
    }

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
        while ((currLine = br.readLine()) != null) {
            String[] splitLine = currLine.split(",");
            for (int i = 0; i < splitLine.length; i++)
                matrix.values.get(String.valueOf(i)).add(Double.parseDouble(splitLine[i]));
        }
        for (Map.Entry e : matrix.values.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
        return matrix;
    }

    public static void main(String[] args) throws IOException {
        Matrix.fromCSV("/Files/ex1data1.txt");
    }
}

