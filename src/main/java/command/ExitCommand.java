package command;

import table.SudokuTable;

import java.util.Set;

public class ExitCommand extends AbstractCommand{

    public ExitCommand(SudokuTable table) {
        super(Set.of("exit"), table);
    }


    @Override
    public void execute(String[] commandWithParams) {
        System.out.println("GAME OVER");
        System.exit(0);
    }
}
