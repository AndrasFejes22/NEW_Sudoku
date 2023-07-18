import command.Command;
import command.ExitCommand;
import command.NewCommand;
import command.PutCommand;
import table.SudokuTable;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SudokuTable sudokuTable = new SudokuTable();
        /*


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

        List<Command> commands = List.of(new NewCommand(sudokuTable), new ExitCommand(sudokuTable), new PutCommand(sudokuTable));

        try (Scanner input = new Scanner(System.in)){
            do {
                System.out.println("? ");
                String line = input.nextLine();
                String[] commandBits = line.split(" "); //pl.: put 4 5 1 /rakd a 4,5 koordinátára az 1-et)
                Optional<Command> matchingCommand = findCommand(commands, commandBits[0]);
                if(matchingCommand.isPresent()){
                    matchingCommand.get().execute(commandBits);
                }else {
                    System.out.println("Unknown command");
                }
            }while (true);

        }


    }

    private static Optional<Command> findCommand (List<Command> commands, String enteredCommand){
        for (Command command: commands) {
            if(command.acceptsCommand(enteredCommand)){
                return Optional.of(command);
            }
        }
        return Optional.empty();
    }


}


