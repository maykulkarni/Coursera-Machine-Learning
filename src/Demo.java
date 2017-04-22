
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author Mayur Kulkarni <mayurkulkarni012@gmail.com>
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        String inputData = "C:\\Users\\kulkarni_my\\Desktop\\Coursera-Machine-Learning\\Files\\Exercise 3\\MNIST.csv";
        String train = "C:\\Users\\kulkarni_my\\Desktop\\Coursera-Machine-Learning\\Files\\Exercise 3\\MNIST_train.csv";
        String test = "C:\\Users\\kulkarni_my\\Desktop\\Coursera-Machine-Learning\\Files\\Exercise 3\\MNIST_test.csv";
        BufferedReader inputReader = new BufferedReader(new FileReader(inputData));
        PrintWriter testWriter = new PrintWriter(test);
        PrintWriter trainWriter = new PrintWriter(train);
        String inputLine;
        int counter = 0;
        while ((inputLine = inputReader.readLine()) != null) {
            if (counter == 491) {
                counter = 0;
                for (int i = 0; i < 10; i++) {
                    testWriter.println(inputLine);
                    inputLine = inputReader.readLine();
                }
            } else {
                trainWriter.println(inputLine);
                counter++;
            }
        }
        testWriter.close();
        trainWriter.close();
        inputReader.close();
    }
}