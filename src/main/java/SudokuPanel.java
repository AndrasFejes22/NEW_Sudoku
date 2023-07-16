import table.SudokuTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuPanel implements ActionListener {

    JFrame frame = new JFrame();

    JPanel title_panel = new JPanel();

    JLabel textfield = new JLabel();

    JPanel button_panel = new JPanel();

    JButton[] buttons = new JButton[88];




    SudokuPanel(SudokuTable sudokuTable) {
        frame.setTitle("SUDOKU v1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocation(1250, 430);


        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        //textfield.setFont(new Font("Ink Free",Font.BOLD,75));
        textfield.setFont(new Font("Arial", Font.BOLD, 55));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        button_panel.setLayout(new GridLayout(9, 9));
        button_panel.setBackground(new Color(150, 150, 150));

        //sudokuTable.generate();

        for (int i = 0; i < 81; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            //buttons[i].setText(sudokuTable.toString());
            buttons[i].addActionListener(this);
        }

        frame.add(button_panel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
