package ir.saleh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

enum Action {
    UP, DOWN, LEFT, RIGHT
}

public class Main {
    public static int size = 2;

    public static State start;
    public static State goal;
    public static String address;

    public static void read() throws FileNotFoundException {
        String fileAddress = new Scanner(System.in).nextLine();
        address = fileAddress;
        Scanner scanner = new Scanner(new File(fileAddress));
        String line = scanner.nextLine();
        var elements = line.split(" ");
        size = elements.length;

        Map<Position, Integer> map = new HashMap<>();
        Position position = null;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (elements[j].equals("*") || elements[j].equals("**")) {
                    position = new Position(i, j);
                    map.put(position, 0);
                    continue;
                }
                map.put(new Position(i, j), Integer.valueOf(elements[j]));
            }
            elements = scanner.nextLine().split(" ");
        }
        start = new State(position, map);

        elements = scanner.nextLine().split(" ");
        map = new HashMap<>();
        position = null;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (elements[j].startsWith("*")) {
                    position = new Position(i, j);
                    map.put(position, 0);
                    continue;
                }
                map.put(new Position(i, j), Integer.valueOf(elements[j]));
            }
            if (scanner.hasNextLine())
                elements = scanner.nextLine().split(" ");
        }
        goal = new State(position, map);
    }

    public static void main(String[] args) throws IOException {
        read();
        long dTime;
        long bTime;
        long current;
        Dijkstra dijkstra = new Dijkstra(start);
        current = System.currentTimeMillis();
        dijkstra.run();
        dTime = System.currentTimeMillis() - current;
        Bidirectional bidirectional = new Bidirectional(start, goal);
        current = System.currentTimeMillis();
//        bidirectional.run();
        bTime = System.currentTimeMillis() - current;
        var bPath = bidirectional.result();
        var dPath = dijkstra.result(goal);
        chap(bPath, dPath, bTime, dTime);
    }

    static void chap(List<State> bPath, List<State> dPath, long bTime, long dTime) throws IOException {
        FileWriter fileWriter = new FileWriter("Log_" + address.replaceAll("\\.", "").replaceAll("/", "") + "_Javanmard.txt");
        fileWriter.write(address + "\n\n");
        fileWriter.write("Dijkstra:\n");
        fileWriter.write("time " + dTime + "\n");
        fileWriter.write("Act " + (dPath.size() - 2) + "\n\n");
        for (int i = 1; i < dPath.size() - 1; i++) {
            fileWriter.write(dPath.get(i).toString() + "\n");
        }
        fileWriter.write("Bidirectional:\n");
        fileWriter.write("time " + bTime + "\n");
        fileWriter.write("Act " + (bPath.size() - 2) + "\n\n");
        for (int i = 1; i < bPath.size() - 1; i++) {
            fileWriter.write(bPath.get(i).toString() + "\n");
        }
        fileWriter.flush();
    }
}

class Bidirectional {
    public final List<Action> actions = List.of(Action.DOWN, Action.UP, Action.LEFT, Action.RIGHT);
    State start;
    State goal;
    State same;
    Map<State, List<State>> fromGoal = new ConcurrentHashMap<>(); //DFS
    Map<State, List<State>> fromStart = new ConcurrentHashMap<>(); //BFS

    public Bidirectional(State start, State goal) {
        this.start = start;
        this.goal = goal;
        fromGoal.put(goal, new ArrayList<>());
    }

    public void run() {
        Thread thread1 = new Thread(this::BFS);
        Thread thread2 = new Thread(() -> this.DFS(goal));
        thread1.start();
        thread2.start();
        while (same == null) {

        }
    }

    void BFS() {
        Set<State> seen = new HashSet<>();
        Queue<State> states = new ArrayDeque<>();
        states.add(start);
        fromStart.put(start, new ArrayList<>());

        while (!states.isEmpty()) {
            State state = states.poll();
            List<State> nextStates1 = new ArrayList<>();
            actions.forEach(action -> {
                State state1 = state.nextState(action);
                if (state1 != null) nextStates1.add(state1);
            });
            nextStates1.forEach(state1 -> fromStart.putIfAbsent(state1, new ArrayList<>()));
            nextStates1.forEach(state1 -> fromStart.get(state1).add(state));
            nextStates1.stream().filter(s -> !fromStart.containsKey(s)).forEach(states::add);
        }
    }

    void DFS(State state) {
        if (fromStart.containsKey(state)) {
            same = state;
            return;
        }
        List<State> nextStates1 = new ArrayList<>();
        actions.forEach(action -> {
            State state1 = state.nextState(action);
            if (state1 != null && !fromGoal.containsKey(state1)) nextStates1.add(state1);
        });
        nextStates1.forEach(state1 -> {
            fromGoal.putIfAbsent(state1, new ArrayList<>());
            fromGoal.get(state1).add(state);
            DFS(state1);
        });
    }

    public List<State> result() {
        if(true) return new ArrayList<>();
        List<State> path = new ArrayList<>();
        State state = same;
        path.add(state);
        while (!fromStart.get(state).isEmpty()) {
            path.add(fromStart.get(state).get(0));
            state = fromStart.get(state).get(0);
        }

        List<State> path2 = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            path2.add(path.get(i));
        }
        state = same;
        while (!fromGoal.get(state).isEmpty()) {
            path2.add(fromGoal.get(state).get(0));
            state = fromGoal.get(state).get(0);
        }
        return path2;
    }
}

class Dijkstra {
    public final State start;
    public final Set<State> seen = new HashSet<>();
    public final Map<State, Integer> distance = new HashMap<>();
    public final Map<State, State> lastState = new HashMap<>();
    public final List<Action> actions = List.of(Action.DOWN, Action.UP, Action.LEFT, Action.RIGHT);

    public Dijkstra(State start) {
        this.start = start;
        distance.put(start, 0);
    }

    public void run() {
        Queue<State> states = new ArrayDeque<>();
        states.add(start);
        while (!states.isEmpty()) {
            List<State> list = execute(states.poll());
            states.addAll(list);
        }
    }

    public List<State> execute(State state) {
        int currentDistance = distance.get(state);

        seen.add(state);

        List<State> nextStates = new ArrayList<>();
        actions.forEach(action -> {
            State state1 = state.nextState(action);
            if (state1 != null) nextStates.add(state1);
        });

        nextStates.forEach(state1 -> {
            if (!distance.containsKey(state1) || distance.get(state1) > currentDistance + 1) {
                distance.put(state1, currentDistance + 1);
                lastState.put(state1, state);
            }
        });
        return nextStates.stream().filter(state1 -> !seen.contains(state1)).collect(Collectors.toList());
    }

    public List<State> result(State goal) {
        List<State> path = new ArrayList<>(List.of(goal));
        State state = goal;
        while (!state.equals(start)) {
            path.add(lastState.get(state));
            state = lastState.get(state);
        }
        List<State> path2 = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            path2.add(path.get(i));
        }
        return path2;
    }
}


class State {
    Position empty;
    Map<Position, Integer> puzzle;

    public State(Position empty, Map<Position, Integer> puzzle) {
        this.empty = empty;
        this.puzzle = puzzle;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < Main.size; i++) {
            for (int j = 0; j < Main.size; j++) {
                s += puzzle.get(new Position(i, j));
            }
            s += "\n";
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        return toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }

    public State creatNext(Position position) {
        Map<Position, Integer> puzzle = new HashMap<>(this.puzzle);
        int newEmpty = puzzle.get(position);
        puzzle.put(empty, newEmpty);
        puzzle.put(position, 0);
        return new State(position, puzzle);
    }

    public State nextState(Action action) {
        Position next;
        switch (action) {
            case UP:
                next = new Position(empty.getI() - 1, empty.getJ());
                return next.isValid() ? creatNext(next) : null;
            case DOWN:
                next = new Position(empty.getI() + 1, empty.getJ());
                return next.isValid() ? creatNext(next) : null;
            case LEFT:
                next = new Position(empty.getI(), empty.getJ() - 1);
                return next.isValid() ? creatNext(next) : null;
            case RIGHT:
                next = new Position(empty.getI(), empty.getJ() + 1);
                return next.isValid() ? creatNext(next) : null;
        }
        return null;
    }
}

class Position {
    private int i;
    private int j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return i == position.i && j == position.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString() {
        return "Position{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }

    public boolean isValid() {
        return i >= 0 && i < Main.size && j >= 0 && j < Main.size;
    }
}
