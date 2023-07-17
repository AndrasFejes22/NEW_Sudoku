import table.SudokuTable;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
        SudokuTable sudokuTable = new SudokuTable();

        //sudokuTable.generate();
        sudokuTable.createPuzzle(30); //numberInPuzzle marad

        System.out.println("puzzle:");
        System.out.println(sudokuTable);

        sudokuTable.solve();
        System.out.println("exactlyOneSolutions:");
        sudokuTable.exactlyOneSolutions();
        System.out.println("solution:");
        System.out.println(sudokuTable);


        //SudokuPanel sudokuPanel = new SudokuPanel(sudokuTable);
        */

        try (Scanner input = new Scanner(System.in)){
            do {
                System.out.println("? ");
                String line = input.nextLine();
                String[] commandBits = line.split(" "); //pl.: put 4 5 1 /rakd a 4,5 koordinátára az 1-et)

            }while (true);

        }


    }


}


