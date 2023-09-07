import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DawsonChess {
    public Map<Integer, Integer> dawsonValues = new HashMap<>();

    {
        dawsonValues.put(0, 0);
        dawsonValues.put(1, 1);
        dawsonValues.put(2, 1);
        for (int i = 3; i < 1403; i++) {
            Set<Integer> childValues = getChildValues(i);
            dawsonValues.put(i, getGValue(childValues));
        }
    }

    public int calculate(List<Integer> list) {
        if (list.stream().anyMatch(integer -> integer > 1402)) return -1;
        AtomicInteger result = new AtomicInteger();
        list.forEach(integer -> result.set(result.get() ^ dawsonValues.get(integer)));
        return result.get();
    }

    Set<Integer> getChildValues(int n) {
        Set<Integer> integers = new HashSet<>();
        integers.add(dawsonValues.get(n - 2));
        for (int i = 0; i < Math.abs(n / 2); i++) {
            integers.add(dawsonValues.get(i) ^ dawsonValues.get(n - i - 3));
        }
        return integers;
    }

    private int getGValue(Set<Integer> values) {
        int i;
        for (i = 0; values.contains(i); i++) ;
        return i;
    }
}
