import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DawsonChess dawsonChess = new DawsonChess();
        Scanner scanner = new Scanner(System.in);
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            integers.add(scanner.nextInt());
        System.err.println(dawsonChess.calculate(integers));
    }
}
