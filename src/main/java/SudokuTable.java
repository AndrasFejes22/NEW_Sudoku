import java.util.HashSet;
import java.util.Set;

public class SudokuTable {

    public static int sudokuSize = 9;

    private int [][] data;

    private static Set<Integer> allPossibleValues = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public SudokuTable(){
        this(new int[sudokuSize][sudokuSize]);
    }

    public SudokuTable(int[][] data) {
        validateInputData(data);
        this.data = data;
    }

    private void validateInputData(int[][] data) {
        if (data.length != sudokuSize) {
            throw new IllegalArgumentException("Sudoku size must be 9x9");
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i].length != sudokuSize) {
                throw new IllegalArgumentException("Sudoku size must be 9x9");
            }
        }
    }

    public Set<Integer> getPossibleValues(int row, int column){
        //Set-ekből metszetek készítése a lehetséges (beírható) értékekhez
        Set<Integer> possibleValues = getPossibleValuesInRow(row);
        possibleValues.retainAll(getPossibleValuesInColumn(column));
        possibleValues.retainAll(getPossibleValuesInSubgrid(convertRowAndColumnToSubgridIndex(row, column)));
        return possibleValues;//nincs kész
    }

    //lehetséges értékek a sorokban
    public Set<Integer> getPossibleValuesInRow(int row){
        Set<Integer> result = new HashSet<>(allPossibleValues);
        for (int i = 0; i < data[row].length; i++) {
            result.remove(data[row][i]);
        }
        return result;
    }

    //lehetséges értékek az oszlopokban
    public Set<Integer> getPossibleValuesInColumn(int column){
        Set<Integer> result = new HashSet<>(allPossibleValues);
        for (int i = 0; i < sudokuSize; i++) {
            result.remove(data[i][column]);
        }
        return result;
    }

    //lehetséges értékek a subgridekben
    public Set<Integer> getPossibleValuesInSubgrid(int subgrid){
        Set<Integer> result = new HashSet<>(allPossibleValues);
        int column = (subgrid % 3) *3;
        int row = (subgrid / 3) *3;

        for (int i = 0; i < sudokuSize / 3; i++) {
            for (int j = 0; j < sudokuSize / 3; j++) {
                result.remove(data[row + i][column + j]);
            }
        }
        return result;
    }

    private int convertRowAndColumnToSubgridIndex(int row, int column){
        return (row / 3) * 3 +column / 3;
    }
}
