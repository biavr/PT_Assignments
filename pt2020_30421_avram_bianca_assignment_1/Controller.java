import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.regex.*;
public class Controller {
    View view;

    public Controller(){
        view = new View();
    }

    public static Polynomial getPolynomialFromTexField(String text) throws InvalidInputException{
        if(text.equals("")){
            throw new InvalidInputException();
        }
        Polynomial p = new Polynomial();
        String polynomialPattern = "([+-]?[0-9]*\\*?(x\\^?[0-9]?)?)+";
        Pattern pattern = Pattern.compile(polynomialPattern);
        Matcher matcher = pattern.matcher(text);
        if(matcher.matches()){
            //the polynomial is in the correct input format
            String splittedstring[] = text.split("(?=[-+])");
            for (String str : splittedstring) {
                int index = str.indexOf('x');
                int coefficient =0;
                int power =0;
                if(index != -1){
                    if(index != 0){
                        if(str.charAt(0) == '+'){
                            coefficient = 1;
                        }
                        else{
                            if(str.charAt(0) == '-' && index == 1){
                                coefficient = -1;
                            }
                            else{
                                coefficient = Integer.parseInt(str.substring(0,str.indexOf('x')));
                            }
                        }
                    }
                    else{
                        coefficient = 1;
                    }
                    int index2 = str.indexOf('^');
                    if(index2 == -1){
                        power = 1;
                    }
                    else{
                        power = Integer.parseInt(str.substring(index2+1));
                    }
                }
                else {
                    coefficient = Integer.parseInt(str);
                    power = 0;
                }
                Monomial m = new Monomial(coefficient,power);
                p.addMonomial(m);
            }
        }
        else{
            throw new InvalidInputException();
        }
        Collections.sort(p.poly,Collections.<Monomial>reverseOrder());
        p.setDegree(p.poly.get(0).getPower());
        return p;
    }

    public static void main(String[] args){
        final Controller controller = new Controller();
        class InstructionsActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.view.poly31.setVisible(false);
                controller.view.polynomail3 = new JLabel("Result                     ");
                JOptionPane.showMessageDialog(controller.view.frame,"Instructions\nIn the available text fields input 2 polynomials and select an operation\nBe careful to obey to the following format:\na0x^b0+a1x^b1+…+anx^bn");
            }
        }
        controller.view.instr.addActionListener(new InstructionsActionListener());

        class AdditionActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                String text1 = controller.view.text1.getText();
                String text2 = controller.view.text2.getText();
                controller.view.poly31.setVisible(false);
                controller.view.polynomail3 = new JLabel("Result                     ");
                try{
                    Polynomial p1 = controller.getPolynomialFromTexField(text1);
                    Polynomial p2 = controller.getPolynomialFromTexField(text2);
                    Polynomial p3 = p1.add(p2);
                    controller.view.text3.setText(p3.toString());
                }
                catch (InvalidInputException ex){
                    JOptionPane.showMessageDialog(controller.view.frame,ex.getMessage());
                }
            }
        }
        controller.view.addition.addActionListener(new AdditionActionListener());

        class SubtractionActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                String text1 = controller.view.text1.getText();
                String text2 = controller.view.text2.getText();
                controller.view.poly31.setVisible(false);
                controller.view.polynomail3 = new JLabel("Result                     ");
                try{
                    Polynomial p1 = controller.getPolynomialFromTexField(text1);
                    Polynomial p2 = controller.getPolynomialFromTexField(text2);
                    Polynomial p3 = p1.subtract(p2);
                    controller.view.text3.setText(p3.toString());
                }
                catch (InvalidInputException ex){
                    JOptionPane.showMessageDialog(controller.view.frame,ex.getMessage());
                }
            }
        }
        controller.view.subtraction.addActionListener(new SubtractionActionListener());

        class MultiplyActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                String text1 = controller.view.text1.getText();
                String text2 = controller.view.text2.getText();
                controller.view.poly31.setVisible(false);
                controller.view.polynomail3 = new JLabel("Result                     ");
                try{
                    Polynomial p1 = controller.getPolynomialFromTexField(text1);
                    Polynomial p2 = controller.getPolynomialFromTexField(text2);
                    Polynomial p3 = p1.multiply(p2);
                    controller.view.text3.setText(p3.toString());
                }
                catch (InvalidInputException ex){
                    JOptionPane.showMessageDialog(controller.view.frame,ex.getMessage());
                }
            }
        }
        controller.view.multiplication.addActionListener(new MultiplyActionListener());

        class DivisionActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                String text1 = controller.view.text1.getText();
                String text2 = controller.view.text2.getText();
                try{
                    Polynomial p1 = controller.getPolynomialFromTexField(text1);
                    Polynomial p2 = controller.getPolynomialFromTexField(text2);
                    try{
                        Polynomial.QRPair result = p1.divide(p2);
                        controller.view.text3.setText(result.quotient.toString());
                        controller.view.poly31.setVisible(true);
                        controller.view.polynomail3.setText("Quotient                   ");
                        controller.view.text31.setText(result.remainder.toString());
                    }
                    catch (IllegalArgumentException ex){
                        JOptionPane.showMessageDialog(controller.view.frame,"Illegal division by 0! Make sure the second polynomial is not 0");
                    }
                }
                catch (InvalidInputException ex){
                    JOptionPane.showMessageDialog(controller.view.frame,ex.getMessage());
                }
            }
        }
        controller.view.division.addActionListener(new DivisionActionListener());

        class Derivation1ActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = controller.view.text1.getText();
                controller.view.poly31.setVisible(false);
                controller.view.polynomail3 = new JLabel("Result                     ");
                try{
                    Polynomial in = controller.getPolynomialFromTexField(text);
                    Polynomial result = in.derivate();
                    controller.view.text3.setText(result.toString());
                }
                catch(InvalidInputException ex){
                    JOptionPane.showMessageDialog(controller.view.frame,ex.getMessage());
                }
            }
        }
        controller.view.derivate1.addActionListener(new Derivation1ActionListener());

        class Derivation2ActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = controller.view.text2.getText();
                controller.view.poly31.setVisible(false);
                controller.view.polynomail3 = new JLabel("Result                     ");
                try{
                    Polynomial in = controller.getPolynomialFromTexField(text);
                    Polynomial result = in.derivate();
                    controller.view.text3.setText(result.toString());
                }
                catch(InvalidInputException ex){
                    JOptionPane.showMessageDialog(controller.view.frame,ex.getMessage());
                }
            }
        }
        controller.view.derivate2.addActionListener(new Derivation2ActionListener());

        class Integration1ActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = controller.view.text1.getText();
                controller.view.poly31.setVisible(false);
                controller.view.polynomail3 = new JLabel("Result                     ");
                try{
                    Polynomial in = controller.getPolynomialFromTexField(text);
                    Polynomial result = in.integrate();
                    controller.view.text3.setText(result.toString());
                }
                catch(InvalidInputException ex){
                    JOptionPane.showMessageDialog(controller.view.frame,ex.getMessage());
                }
            }
        }
        controller.view.integrate1.addActionListener(new Integration1ActionListener());

        class Integration2ActionListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = controller.view.text2.getText();
                controller.view.poly31.setVisible(false);
                controller.view.polynomail3 = new JLabel("Result                     ");
                try{
                    Polynomial in = controller.getPolynomialFromTexField(text);
                    Polynomial result = in.integrate();
                    controller.view.text3.setText(result.toString());
                }
                catch(InvalidInputException ex){
                    JOptionPane.showMessageDialog(controller.view.frame,ex.getMessage());
                }
            }
        }
        controller.view.integrate2.addActionListener(new Integration2ActionListener());
        controller.view.showFrame();
    }
}
