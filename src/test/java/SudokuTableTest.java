import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SudokuTableTest {

    @Test
    void getPossibleValues() {
        int[][] data = {
                {9, 3, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 0, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 0},
                {4, 0, 0, 8, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 7, 0}
        };

        SudokuTable sudokuTable = new SudokuTable(data);
        Set<Integer> possibleValues = sudokuTable.getPossibleValues(7, 7);
        List<Integer> integerList = new ArrayList<>(possibleValues);
        List<Integer> list = Arrays.asList(3);
        assertEquals(integerList, list);


        Set<Integer> possibleValues65 = sudokuTable.getPossibleValues(6, 5);
        List<Integer> integerList65 = new ArrayList<>(possibleValues65);
        List<Integer> list65 = Arrays.asList(3,7);
        assertEquals(integerList65, list65);

    }

    @Test
    void getPossibleValuesInRow() {

        int[][] data = {
                {9, 3, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 0, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 0},
                {4, 0, 0, 8, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 7, 0}
        };

        SudokuTable sudokuTable = new SudokuTable(data);

        Set<Integer> possibleValuesInRow2 = sudokuTable.getPossibleValuesInRow(2);
        List<Integer> integerList = new ArrayList<>(possibleValuesInRow2);
        List<Integer> list1 = Arrays.asList(1,2,3,4,5,7,9);
        assertEquals(integerList, list1);

        Set<Integer> possibleValuesInRow0 = sudokuTable.getPossibleValuesInRow(0);
        List<Integer> integerList0 = new ArrayList<>(possibleValuesInRow0);
        List<Integer> list0 = Arrays.asList(1,2,4,5,6,7,8);
        assertEquals(integerList0, list0);
    }

    @Test
    void getPossibleValuesInColumn() {
        int[][] data = {
                {9, 3, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 0, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 0},
                {4, 0, 0, 8, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 7, 0}
        };

        SudokuTable sudokuTable = new SudokuTable(data);
        Set<Integer> possibleValuesInColumn0 = sudokuTable.getPossibleValuesInColumn(0);
        List<Integer> integerList0 = new ArrayList<>(possibleValuesInColumn0);
        List<Integer> list0 = Arrays.asList(1,2,3,5,6,7);
        assertEquals(integerList0, list0);
    }

    @Test
    void getPossibleValuesInSubgrid() {
        int[][] data = {
                {9, 3, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 0, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 0},
                {4, 0, 0, 8, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 2, 0, 0, 0, 0},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 0, 7, 0}
        };

        SudokuTable sudokuTable = new SudokuTable(data);
        Set<Integer> possibleValuesInSubgrid2 = sudokuTable.getPossibleValuesInSubgrid(2);
        List<Integer> integerList2 = new ArrayList<>(possibleValuesInSubgrid2);
        List<Integer> list2 = Arrays.asList(1,2,3,4,5,7,8,9);
        assertEquals(integerList2, list2);

        Set<Integer> possibleValuesInSubgrid0 = sudokuTable.getPossibleValuesInSubgrid(0);
        List<Integer> integerList0 = new ArrayList<>(possibleValuesInSubgrid0);
        List<Integer> list0 = Arrays.asList(1,2,4,5,6,7);
        assertEquals(integerList0, list0);

    }
}