import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class View {
    JFrame frame;
    JPanel basepane;
    JPanel poly1;
    JLabel polynomial1;
    JTextField text1;
    JPanel poly2;
    JLabel polynomial2;
    JTextField text2;
    JPanel poly3;
    JLabel polynomail3;
    JTextField text3;
    JPanel poly31;
    JLabel polynomail31;
    JTextField text31;
    JPanel buttonsBinaryOperations;
    JButton addition;
    JButton subtraction;
    JButton multiplication;
    JButton division;
    JPanel buttonsUnaryOperations;
    JButton derivate1;
    JButton integrate1;
    JButton derivate2;
    JButton integrate2;
    JPanel instructionpane;
    JButton instr;

    public View(){
        frame = new JFrame("Polynomial calculator");
        basepane = new JPanel();
        basepane.setLayout(new FlowLayout());
        poly1 = new JPanel();
        poly1.setLayout(new FlowLayout());
        polynomial1 = new JLabel("First polynomial     ");
        text1 = new JTextField(20);
        poly1.add(polynomial1);
        poly1.add(text1);
        basepane.add(poly1);

        poly2 = new JPanel();
        poly2.setLayout(new FlowLayout());
        polynomial2 = new JLabel("Second polynomial");
        text2 = new JTextField(20);
        poly2.add(polynomial2);
        poly2.add(text2);
        basepane.add(poly2);


        poly3 = new JPanel();
        poly3.setLayout(new FlowLayout());
        polynomail3 = new JLabel("Result                     ");
        text3 = new JTextField(20);
        text3.setEditable(false);
        poly3.add(polynomail3);
        poly3.add(text3);
        basepane.add(poly3);

        poly31 = new JPanel();
        poly31.setLayout(new FlowLayout());
        polynomail31 = new JLabel("Remainder              ");
        text31 = new JTextField(20);
        text31.setEditable(false);
        poly31.add(polynomail31);
        poly31.add(text31);
        poly31.setVisible(false);
        basepane.add(poly31);

        buttonsBinaryOperations = new JPanel();
        buttonsBinaryOperations.setBorder(new TitledBorder("Binary operations"));
        addition = new JButton("Add");
        subtraction = new JButton("Subtract");
        multiplication = new JButton("Multiply");
        division = new JButton("Divide");
        buttonsBinaryOperations.add(addition);
        buttonsBinaryOperations.add(subtraction);
        buttonsBinaryOperations.add(multiplication);
        buttonsBinaryOperations.add(division);
        basepane.add(buttonsBinaryOperations);

        buttonsUnaryOperations = new JPanel();
        buttonsUnaryOperations.setBorder(new TitledBorder("Unary operations"));
        derivate1 = new JButton("Derivate first");
        integrate1 = new JButton("Integrate first");
        derivate2 = new JButton("Derivate second");
        integrate2 = new JButton("Integrate second");
        buttonsUnaryOperations.add(derivate1);
        buttonsUnaryOperations.add(integrate1);
        buttonsUnaryOperations.add(derivate2);
        buttonsUnaryOperations.add(integrate2);
        basepane.add(buttonsUnaryOperations);

        instructionpane = new JPanel();
        instructionpane.setLayout(new FlowLayout());
        instr = new JButton("Instructions");
        instructionpane.add(instr);
        basepane.add(instructionpane);

        frame.add(basepane);
    }

    public void showFrame(){
        frame.setSize(600, 360);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
