package command;

import table.SudokuTable;

import java.util.Set;
import java.util.regex.Pattern;

public class PutCommand extends AbstractCommand{

    private static final Pattern NUMERIC_PATTERN = Pattern.compile("[1-9]"); //user input validation (1 db karakter lehet 1...9 értékkel, semmi más)

    public PutCommand(SudokuTable table) {
        super(Set.of("put", "p"), table);
    }

    @Override
    public void execute(String[] commandWithParams) {
        // put 5 4 1
        if (commandWithParams.length != 4) {
            System.out.println("Invalid number of parameters");
            System.out.println("Usage: put <row> <column> <value>");
            return;
        }

        //user input validation**
        for (int i = 1; i < commandWithParams.length; i++) {
            if (!NUMERIC_PATTERN.matcher(commandWithParams[i]).matches()) {
                System.out.println("Invalid parameter value: " + commandWithParams[i]);
                return;
            }
        }
        // **validated data:
        int row = Integer.parseInt(commandWithParams[1]);
        int column = Integer.parseInt(commandWithParams[2]);
        int value = Integer.parseInt(commandWithParams[3]);
        if (table.setValue(row - 1, column - 1, value)) { // a -1ek a regex [1-9] miatt vannak /user input 1...9/
            System.out.println(table);
        } else {
            System.out.printf("Not possible to set [%d,%d] to %d%n", row, column, value);
        }
    }
}
