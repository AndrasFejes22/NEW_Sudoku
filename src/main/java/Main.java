import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        SudokuTable sudokuTable = new SudokuTable();

        sudokuTable.generate();

        System.out.println(sudokuTable);


       //SudokuPanel sudokuPanel = new SudokuPanel(sudokuTable);

    }


}


