import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class Chomp {
    Set<State> pStates = new HashSet<>();
    Set<State> nStates = new HashSet<>();

    public Chomp(int n) {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        for (int i = 1; i < n; i++) integers.add(0);
        pStates.add(new State(integers));
    }

    public boolean isPState(State state) {
        addState(state);
        return pStates.contains(state);
    }

    void addState(State state) {
        List<State> next = state.getNext();
        AtomicBoolean flag = new AtomicBoolean(true);
        next.forEach(stateNext -> {
            if (!pStates.contains(stateNext) && !nStates.contains(stateNext))
                addState(stateNext);
            if (pStates.contains(stateNext)) {
                nStates.add(state);
                flag.set(false);
            }
        });
        if (flag.get())
            pStates.add(state);
    }
}
