import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import whyxzee.blackboard.utils.SortingUtils;
import whyxzee.blackboard.utils.SpecialFunctions;
import whyxzee.blackboard.display.BlackboardDisplay;
import whyxzee.blackboard.display.BlackboardLabel;
import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.ExponentialTerm;
import whyxzee.blackboard.terms.LogarithmicTerm;
import whyxzee.blackboard.terms.PowerTerm;
import whyxzee.blackboard.terms.SignumTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.TrigTerm;
import whyxzee.blackboard.terms.Term.TermType;
import whyxzee.blackboard.terms.TrigTerm.TrigType;
import whyxzee.blackboard.terms.arithmetic.AdditionAbstract;
import whyxzee.blackboard.terms.arithmetic.MultiplicationAbstract;
import whyxzee.blackboard.terms.arithmetic.special.CondenseLog;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;

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
                add(new LogarithmicTerm(1, new Variable("x"), Math.E));
                add(new LogarithmicTerm(2, new Variable("x"), Math.E));
                add(new LogarithmicTerm(1, new Variable("x"), 2));
                add(new LogarithmicTerm(1, new Variable("x"), 10));
                add(new PowerTerm(1, new USub(new LogarithmicTerm(5, new Variable("x"), 2)), 2));
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
