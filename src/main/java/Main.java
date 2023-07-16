import table.SudokuTable;

public class Main {

    public static void main(String[] args) {
        SudokuTable sudokuTable = new SudokuTable();

        //sudokuTable.generate();
        sudokuTable.createPuzzle(24); //numberInPuzzle marad

        System.out.println("puzzle:");
        System.out.println(sudokuTable);

        sudokuTable.solve();
        System.out.println("exactlyOneSolutions:");
        sudokuTable.exactlyOneSolutions();
        System.out.println("solution:");
        System.out.println(sudokuTable);


       //SudokuPanel sudokuPanel = new SudokuPanel(sudokuTable);

    }


}


