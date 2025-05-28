import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import whyxzee.blackboard.utils.*;
import whyxzee.blackboard.display.*;
import whyxzee.blackboard.equations.*;
import whyxzee.blackboard.terms.*;
import whyxzee.blackboard.terms.arithmetic.*;
import whyxzee.blackboard.terms.variables.*;

public class Debugger {
    public static void main(String[] args) throws Exception {
        /* Frame stuff */
        JFrame frame = new JFrame();
        BlackboardDisplay display = new BlackboardDisplay(frame);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        /* Math Debugging */
        MultiplicationAbstract multiplyFunction = new MultiplicationAbstract();
        ArrayList<Term> terms = new ArrayList<Term>() {
            {
                add(new PowerTerm(1, new Variable("x")));
                add(new PowerTerm(1, new Variable("x"), 9));
                add(new PowerTerm(7, new Variable("x"), 4));
                add(new PowerTerm(1, new Variable("x"), 13));
                add(new PowerTerm(1, new Variable("x"), 2));
                add(new PowerTerm(5, new Variable("y")));
                // add(new LogarithmicTerm(1, new Variable("x"), Math.E));
                // add(new LogarithmicTerm(2, new Variable("x"), Math.E));
                // add(new LogarithmicTerm(1, new Variable("x"), 2));
                // add(new LogarithmicTerm(1, new Variable("x"), 10));
                // add(new PowerTerm(1, new USub(new LogarithmicTerm(5, new Variable("x"), 2)),
                // 2));
            }
        };

        display.appendScript(new BlackboardLabel(
                new EQMultiplication(multiplyFunction.performMultiplication(terms)).toString(), 0.05));

        // display.appendScript(new BlackboardLabel(
        // new EQMultiplication(addFunction.performAddition(terms)).toString(), 0.05));

        // EQSequence eq = new EQSequence(new ArrayList<Term>() {
        // {
        // add(new LogarithmicTerm(2, new Variable("x"), Math.E));
        // add(new LogarithmicTerm(1, new Variable("x"), Math.E));
        // }
        // });
        // CondenseLog logCondensor = new CondenseLog();
        // display.appendScript(new BlackboardLabel(new
        // EQSequence(logCondensor.performFunction((new ArrayList<Term>() {
        // {
        // add(new LogarithmicTerm(2, new Variable("x"), Math.E));
        // add(new LogarithmicTerm(1, new Variable("x"), Math.E));
        // }
        // }))).toString(), 0.05));

        /* Displaying */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setContentPane(display);
    }
}
