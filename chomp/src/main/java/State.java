import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class State {
    private final List<Integer> chocolates;

    public State(List<Integer> chocolates) {
        this.chocolates = chocolates;
    }

    @Override
    public String toString() {
        return "" + chocolates;

    }

    public List<Integer> getChocolates() {
        return chocolates;
    }

    public State eat(int x, int y) {
        List<Integer> nextChocolates = new ArrayList<>();
        for (int i = 0; i < this.chocolates.size(); i++) {
            if (i >= x) {
                nextChocolates.add(Math.min(chocolates.get(i), y));
            } else {
                nextChocolates.add(chocolates.get(i));
            }
        }
        return new State(nextChocolates);
    }

    public List<State> getNext() {
        List<State> states = new ArrayList<>();
        for (int i = 0; i < chocolates.size(); i++) {
            for (int j = 0; j < chocolates.get(i); j++) {
                if (i == 0 && j == 0) continue;
                states.add(eat(i, j));
            }
        }
        return states;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return chocolates.equals(state.getChocolates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(chocolates);
    }
}
