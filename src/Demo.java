import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mayur on 6/4/17.
 */
public class Demo {
    public static void main(String[] args) {
        Map<String, List> map = new HashMap<>();
        List<Long> baya = new ArrayList<>();
        map.put("a", baya);
        List<Integer> ss = new ArrayList<>();
        map.put("aa", ss);
//        int num = map.get("a").get(9).intValue();
        Number a = new Double(2.7812897);
        if (a instanceof Integer) {
            System.out.println("si");
        }
        map.get(":").add(78);
//        System.out.println(aa);
    }
}
