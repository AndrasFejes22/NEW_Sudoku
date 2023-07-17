package table;

import java.util.*;

import position.Position;
import step.GenerateStep;
import step.RemoveStep;

import static position.Position.position;

public class SudokuTable {

    /**
     * TODO konstansokat kiemelni (3, 8,stb)
     * data[row][column] != EMPTY_VALUE -->isEmpty method
     * console UI
     * int [][] data-val kapcsolatos feladatok külön osztályban (Grid? Table?) elválasztva a business logic-tól
     * kevesebbszer állítunk be értéket, mint ahányszor lekérünk
     * */

    public static int SUDOKU_SIZE = 9;
    public static int NUMBER_OF_ZONES = SUDOKU_SIZE / 3;
    public static int ZONE_SIZE = SUDOKU_SIZE / NUMBER_OF_ZONES;
    private static int EMPTY_VALUE = 0;
    private static Set<Integer> ALL_POSSIBLE_VALUES = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    private int [][] data;
    private final Random random = new Random();
    private final Set<Position> userFilledPositions = new HashSet<>();

    public SudokuTable(){
        this(new int[SUDOKU_SIZE][SUDOKU_SIZE]);
    }

    public SudokuTable(int[][] data) {
        validateInputData(data);
        this.data = data;
    }

    public void solve() {
        solve(1, false); //1 megoldást keressen, ne csinálja vissza
    }


    private void validateInputData(int[][] data) {
        if (data.length != SUDOKU_SIZE) {
            throw new IllegalArgumentException("Sudoku size must be 9x9");
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i].length != SUDOKU_SIZE) {
                throw new IllegalArgumentException("Sudoku size must be 9x9");
            }
        }
    }

    public Set<Integer> getPossibleValues(int row, int column){
        //Set-ekből metszetek készítése a lehetséges (beírható) értékekhez
        Set<Integer> possibleValues = getPossibleValuesInRow(row);
        possibleValues.retainAll(getPossibleValuesInColumn(column));
        possibleValues.retainAll(getPossibleValuesInSubgrid(convertRowAndColumnToZone(row, column)));
        return possibleValues;//nincs kész
    }

    private Set<Integer> getPossibleValues(Position position) {
        return getPossibleValues(position.row(), position.column());
    }

    //lehetséges értékek a sorokban
    public Set<Integer> getPossibleValuesInRow(int row){
        Set<Integer> result = new HashSet<>(ALL_POSSIBLE_VALUES);
        for (int i = 0; i < data[row].length; i++) {
            result.remove(data[row][i]);
        }
        return result;
    }

    //lehetséges értékek az oszlopokban
    public Set<Integer> getPossibleValuesInColumn(int column){
        Set<Integer> result = new HashSet<>(ALL_POSSIBLE_VALUES);
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            result.remove(data[i][column]);
        }
        return result;
    }

    //lehetséges értékek a subgridekben
    public Set<Integer> getPossibleValuesInSubgrid(int subgrid){
        Set<Integer> result = new HashSet<>(ALL_POSSIBLE_VALUES);
        int column = (subgrid % 3) *3;
        int row = (subgrid / 3) *3;

        for (int i = 0; i < SUDOKU_SIZE / 3; i++) {
            for (int j = 0; j < SUDOKU_SIZE / 3; j++) {
                result.remove(data[row + i][column + j]);
            }
        }
        return result;
    }

    private int convertRowAndColumnToZone(int row, int column) {
        return (row / ZONE_SIZE) * ZONE_SIZE + (column / ZONE_SIZE);
    }

    public void createPuzzle(int numberInPuzzle) {
        clear();
        init();
        solve(1, false);//1 megoldást keressen, ne csinálja vissza
        List<Position> allFilledPositions = getAllFilledPositions();
        Position position = allFilledPositions.get(random.nextInt(allFilledPositions.size()));
        RemoveStep step = new RemoveStep(null, position, getValue(position));
        clear(position);

        int removedCount = 1;

        while (true) {
            int count = solve(2, true);
            if (count == 1) {
                if (removedCount == SUDOKU_SIZE * SUDOKU_SIZE - numberInPuzzle) {
                    break;
                }
                allFilledPositions = getAllFilledPositions();
                allFilledPositions.removeAll(step.previouslyRemovedPositions());
                if (allFilledPositions.isEmpty()) {
                    RemoveStep previous = step.previous();
                    if (previous == null) {
                        throw new IllegalStateException("Not possible to create puzzle");
                    }
                    setValueInternal(step.position(), step.value());
                    removedCount--;
                    previous.previouslyRemovedPositions().add(step.position());
                    step = previous;
                    continue;
                }
                position = allFilledPositions.get(random.nextInt(allFilledPositions.size()));
                step = new RemoveStep(step, position, getValue(position));
                clear(position);
                removedCount++;
            } else {
                RemoveStep previous = step.previous();
                if (previous == null) {
                    throw new IllegalStateException("Not possible to create puzzle");
                }
                setValueInternal(step.position(), step.value());
                removedCount--;
                previous.previouslyRemovedPositions().add(step.position());
                step = previous;
            }
        }
    }

    private void init() {
        for (int zone : Set.of(0, 4, 8)) {
            List<Integer> randomNumbers = new ArrayList<>(ALL_POSSIBLE_VALUES);
            Collections.shuffle(randomNumbers);
            Iterator<Integer> iterator = randomNumbers.iterator();
            Position position = convertZoneToTopLeftPosition(zone);
            for (int i = 0; i < ZONE_SIZE; i++) {
                for (int j = 0; j < ZONE_SIZE; j++) {
                    setValueInternal(position.row() + i, position.column() + j, iterator.next());
                }
            }
        }
    }

    private Position convertZoneToTopLeftPosition(int zone) {
        int column = (zone % ZONE_SIZE) * ZONE_SIZE;
        int row = (zone / ZONE_SIZE) * ZONE_SIZE;
        return position(row, column);
    }

    // maxCount : megoldások száma
    private int solve(int maxCount, boolean undoChanges) { // visszatérési érték: int; csak a megoldások számát adja meg
        Position position = findNextEmptyPosition(position(0, -1))
                .orElseThrow(() -> new IllegalStateException("The table is already full"));
        GenerateStep step = new GenerateStep(null, position, getPossibleValues(position));
        int count = 0;
        while (count < maxCount) {
            if (!step.possibleValues().isEmpty()) {
                Integer value = step.possibleValues().remove(0);
                setValueInternal(position, value);
                if (allCellsAreFilled()) {
                    count++;
                    continue;
                }
                Position error = position;
                position = findNextEmptyPosition(position)
                        .orElseThrow(() -> new IllegalStateException("No more positions to fill: " + error));
                step = new GenerateStep(step, position, getPossibleValues(position));
            } else {
                clear(position);
                step = step.previous();
                if (step == null) {
                    return count;
                }
                position = step.position();
            }
        }

        if (undoChanges) { // a data[][] tömböt kinullázza
            while (step != null) {
                clear(step.position());
                step = step.previous();
            }
        }
        return count;
    }



    private List<Position> getAllFilledPositions(){
        List<Position> results = new ArrayList<>();
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int column = 0; column < SUDOKU_SIZE; column++) {
                if(data[row][column] != EMPTY_VALUE){
                   results.add(new Position(row, column));
                }
            }
        }
        return results;
    }

    public void exactlyOneSolutions(){

        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int column = 0; column < SUDOKU_SIZE; column++) {
                Position position = new Position(row,column);
                if(this.getPossibleValues(row, column).size() == 1 && getValue(position) == 0){
                    System.out.printf("[%d][%d]%n", row, column);
                }
            }
        }
    }

    public void clear() {
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int column = 0; column < SUDOKU_SIZE; column++) {
                clear(row, column);
            }
        }
    }

    /*a clear, getValue, setValue metódusoknak van Position-os, és (row, column)-os megoldása is, hogy
    * ahol nem kell, ott feleslegesen ne hozzunk létre Position objektumot*/

    private void clear(Position position) {
        clear(position.row(), position.column());
    }

    private void clear(int row, int column) {
        setValueInternal(row, column, EMPTY_VALUE);
    }

    private int getValue(Position position) {
        return getValue(position.row(), position.column());
    }

    private int getValue(int row, int column) {
        return data[row][column];
    }

    public boolean setValue(int row, int column, int value) {
        Position position = position(row, column);
        if (ALL_POSSIBLE_VALUES.contains(value)
                && (isEmpty(row, column) || userFilledPositions.contains(position))
                && getPossibleValues(row, column).contains(value)) {
            setValueInternal(row, column, value);
            userFilledPositions.add(position);
            return true;
        }
        return false;
    }

    private boolean isEmpty(int row, int column) {
        return getValue(row, column) == EMPTY_VALUE;
    }

    private void setValueInternal(Position position, int value) {
        setValueInternal(position.row(), position.column(), value);
    }

    private void setValueInternal(int row, int column, int value) {
        data[row][column] = value;
    }


    private boolean hasCellWithExactlyPossibleValue(){
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int column = 0; column < SUDOKU_SIZE; column++) {
                if(data[row][column] == EMPTY_VALUE && getPossibleValues(row, column).size() == 1){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean allCellsAreFilled(){
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int column = 0; column < SUDOKU_SIZE; column++) {
                if(data[row][column] == EMPTY_VALUE){
                    return false;
                }
            }
        }
        return true;
    }



    private Optional<Position> findNextEmptyPosition(Position position) {
        Position current = position;
        while (current.hasNext()) {
            current = current.next();
            if (isEmpty(current)) {
                return Optional.of(current);
            }
        }
        return Optional.empty();
    }

    private boolean isEmpty(Position position) {
        return isEmpty(position.row(), position.column());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            if (row % ZONE_SIZE == 0) {
                builder.append("+---+---+---+\n");
            }
            for (int column = 0; column < SUDOKU_SIZE; column++) {
                if (column % ZONE_SIZE == 0) {
                    builder.append("|");
                }
                builder.append(getValue(row, column));
            }
            builder.append("|\n");
        }
        builder.append("+---+---+---+");
        return builder.toString();
    }


}
