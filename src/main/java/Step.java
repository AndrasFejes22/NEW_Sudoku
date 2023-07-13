import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Step {

    //láncolt lista
    private Step previous;
    private List<Integer> possibleValues;

    public Step(Step previous, Set<Integer> possibleValues) {
        this.previous = previous;
        this.possibleValues = new ArrayList<>(possibleValues);
        Collections.shuffle(this.possibleValues);
    }

    public Step getPrevious() {
        return previous;
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }
}
