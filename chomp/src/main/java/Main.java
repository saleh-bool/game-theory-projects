import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final int PROBLEM_SIZE = 7;

    public static void main(String[] args) {
        Chomp chomp = new Chomp(PROBLEM_SIZE);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            List<Integer> integers = new ArrayList<>();
            for (int i = 0; i < PROBLEM_SIZE; i++) integers.add(scanner.nextInt());
            State state = new State(integers);
            if (chomp.isPState(state))
                System.out.println("PState");
            else {
                System.out.println("NState");
                List<State> next = state.getNext();
                next.forEach(state1 -> {
                    if (chomp.isPState(state1)) {
                        System.out.println(state1);
                    }
                });
            }
        }
    }
}
