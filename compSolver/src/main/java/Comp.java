import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comp {
    static int size = 4;
    static Map<List<Integer>, StateStatus> stateMap = new HashMap<>();

    static {
        for (int i = 1; i <= size; i++) {
            List<Integer> list = new ArrayList<>();
            list.add(i);
            for(int j=1;j<i;j++) {
                list.add(1);
            }
            normalize(list);
            stateMap.put(list, StateStatus.PRE);
        }

        for(int i=1;i<=size-1;i++) {
            List<Integer> list = new ArrayList<>();
            list.add(i+1);
            list.add(i);
            normalize(list);
            stateMap.put(list, StateStatus.PRE);
        }

        for(int i=1;i<=size-1;i++) {
            List<Integer> list = new ArrayList<>();
            for (int j=0;j<i;j++) {
                list.add(2);
            }
            list.add(1);
            normalize(list);
            stateMap.put(list, StateStatus.PRE);
        }
    }

    public static void main(String[] args) {
        System.err.println("ha");
    }

    static void normalize(List<Integer> list) {
        int size1 = list.size();
        for (int i = 0; i < size - size1; i++)
            list.add(0);
    }

    static List<List<Integer>> expand() {
        
    }

}
