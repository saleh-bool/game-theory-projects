import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Keyles {
    public Map<Integer, Integer> keylesValues = new HashMap<>();

    {
        keylesValues.put(0, 0);
    }

    public void execute() {
        for (int i = 1; i < 1403; i++) {
            Set<Integer> values = getChildValues(i);
            keylesValues.put(i, getGValue(values));
        }
    }

    private Set<Integer> getChildValues(int n) {
        Set<Integer> childes = new HashSet<>();
        if (n == 0) return childes;
        if (n == 1) return Set.of(0);
        if (n == 2) return Set.of(0, 1);
        childes.add(keylesValues.get(n - 1));
        childes.add(keylesValues.get(n - 2));
        for (int i = 1; i <= Math.abs(n / 2); i++) {
            childes.add(keylesValues.get(i) ^ keylesValues.get(n - 1 - i));
        }
        for (int i = 1; i <= Math.abs(n / 2) - 1 ; i++) {
            childes.add(keylesValues.get(i) ^ keylesValues.get(n - 2 - i));
        }
        return childes;
    }

    private int getGValue(Set<Integer> values) {
        int i;
        for (i = 0; values.contains(i); i++) ;
        return i;
    }
}
