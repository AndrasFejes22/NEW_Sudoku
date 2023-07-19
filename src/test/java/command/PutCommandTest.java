package command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PutCommandTest {

    @Test
    void execute() {
    }

    @Test
    void greenColoredTest() { //NumberFormatException
        int value = 1;
        assertEquals(1, PutCommand.greenColored(value));
    }
}