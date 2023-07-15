import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GenerateStep {

    //láncolt lista
    private GenerateStep previous;
    private List<Integer> possibleValues;

    //minden egyes lépésnél tároljuk el a sort és az oszlopot:
    public int row;
    public int column;

    public GenerateStep(GenerateStep previous, int row, int column, Set<Integer> possibleValues) {
        this.previous = previous;
        this.row = row;
        this.column = column;
        this.possibleValues = new ArrayList<>(possibleValues);
        Collections.shuffle(this.possibleValues);
    }

    public GenerateStep getPrevious() {
        return previous;
    }

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}