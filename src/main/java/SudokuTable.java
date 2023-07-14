import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SudokuTable {

    public static int SUDOKU_SIZE = 9;

    private int [][] data;

    private static Set<Integer> ALL_POSSIBLE_VALUES = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    private static int EMPTY_VALUE = 0;

    public SudokuTable(){
        this(new int[SUDOKU_SIZE][SUDOKU_SIZE]);
    }

    public SudokuTable(int[][] data) {
        validateInputData(data);
        this.data = data;
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
        possibleValues.retainAll(getPossibleValuesInSubgrid(convertRowAndColumnToSubgridIndex(row, column)));
        return possibleValues;//nincs kész
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

    private int convertRowAndColumnToSubgridIndex(int row, int column){
        return (row / 3) * 3 +column / 3;
    }

    public void generate(){
        int row = 0;
        int column = 0;
        Step step = new Step(null, row, column, getPossibleValues(row, column));
        while (true){
            //backtrack algoritmus
            // tudni kell mi az az állapot, ahova vissza akarunk lépni (labirintus előző elágazása)
           if(!step.getPossibleValues().isEmpty()){ // ha van még miből választani, akkor válasszunk egyet
               Integer value = step.getPossibleValues().get(0);
               step.getPossibleValues().remove(0); // ha kivettük, ki is kell törölni, már nem használható fel
               //Integer value = step.getPossibleValues().remove(0); // előző 2 lépés összevonva
               data[row][column] = value;
               //utána léptetni kell(peremfeltételekkel):
               if(allCellsAreFilled()){//ha lehet
                   break;
               }
               row = column == 8 ? row + 1 : row; //*itt a row + 1-nél túlindexelődik
               column = column == 8 ? 0 : column + 1;
               //az egészet bele kell rakni egy új Step-be:
               step = new Step(step, row, column, getPossibleValues(row, column)); // következő cella
               // tehát eddig a step-eket felfűztük egy láncolt listára
           } else { // eljutottunk egy 0-ához, vissza kell lépni:
               data[row][column] = EMPTY_VALUE;
               step = step.getPrevious(); // igazi labirintusban való keresésben ezt a sort is ellenőrizni kell egy if-el, ha nincs hova visszalépni, akkor a kiindulási pontban
               row = step.getRow();         // vagyunk, és nincs megoldása a labirintusnak
               column = step.getColumn();
           }
            System.out.println(this); //utolsó előtti lépést mutatja*
        }
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

    @Override
    public String toString() {
        System.out.println("table:");
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            s.append(Arrays.toString(data[i])).append("\n");
        }
        return s.toString();
    }


}
