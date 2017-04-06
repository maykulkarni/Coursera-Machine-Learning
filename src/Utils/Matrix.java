package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mayur Kulkarni on 4/5/2017.
 * Email : mayurkulkarni012@gmail.com
 */

public class Matrix {
    List<Number[]> array;
    private String[] featureList;
    private HashMap<String, Integer> columnMap;

    public Matrix(String[] featureList) {
        array = new ArrayList<>();
        this.featureList = featureList;
        this.columnMap = new HashMap<>(featureList.length);
        for(int i = 0; i < featureList.length; i++) {
            columnMap.put(featureList[i], i);
        }
    }

    public void append(String columnName, Number value) {

    }

    public Number get(int index, String column) {
        return array.get(columnMap.get(column))[index];
    }

    public Matrix() {
        array = new ArrayList<>();
    }

    public static Matrix fromCSV(String filePath) throws IOException {
        Matrix matrix = new Matrix();
        String absPath = new java.io.File(".").getCanonicalPath();
        BufferedReader br = new BufferedReader(new FileReader(absPath + filePath));
        String currLine = "";
        currLine = br.readLine();
        String[] firstLine = currLine.split(",");
        List<Number> numberList = new ArrayList<>();
        for (String str : firstLine) {
            try {
                Long firstVal = Long.parseLong(str);
                numberList.add(123);
            } catch (Exception e) {
                System.out.println("Oops!");
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Matrix mx = new Matrix();
        Integer[] firstCol = new Integer[10];
        Double[] secondCol = new Double[10];
        mx.array.add(firstCol);
        mx.array.add(secondCol);
        System.out.println(Arrays.toString(mx.array.get(1)));
    }
}

