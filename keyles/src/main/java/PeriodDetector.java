import java.util.Map;

public class PeriodDetector {
    private void haha(Map<Integer, Integer> values) {
        for (int i = 1; i < values.size() - 1; i++) {
            if (values.get(i) == 7 && values.get(i + 1) == 4 && values.get(i + 2) == 1)
                System.out.println(i);
        }
    }

    void execute(Map<Integer, Integer> values) {
        for (int i = 71; i < 1100; i += 12) {
            if (values.get(i + 0) == 7 &&
                    values.get(i + 1) == 4 &&
                    values.get(i + 2) == 1 &&
                    values.get(i + 3) == 2 &&
                    values.get(i + 4) == 8 &&
                    values.get(i + 5) == 1 &&
                    values.get(i + 6) == 4 &&
                    values.get(i + 7) == 7 &&
                    values.get(i + 8) == 2 &&
                    values.get(i + 9) == 1 &&
                    values.get(i + 10) == 8 &&
                    values.get(i + 11) == 2) continue;
            return;
        }
        System.err.println("I found It.");
        System.err.println("from n = 71 this pattern is on.");
        System.err.println("{7, 4, 1, 2, 8, 1, 4, 7, 2, 1, 8, 2}");
    }
}
