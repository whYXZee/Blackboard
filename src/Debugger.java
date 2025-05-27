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
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.SignumTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.TrigTerm;
import whyxzee.blackboard.terms.Term.TermType;
import whyxzee.blackboard.terms.TrigTerm.TrigType;
import whyxzee.blackboard.terms.arithmetic.AdditionAbstract;
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
        EQSequence eq = new EQSequence(new PolynomialTerm(2), new PolynomialTerm(1,
                new Variable("x"), 3),
                new PolynomialTerm(2, new Variable("x"), 3));
        EQSequence eq2 = new EQSequence(new PolynomialTerm(2), new PolynomialTerm(1,
                new Variable("x"), 3));

        EQSequence overallEQ = new EQSequence(new ArrayList<Term>() {
            {
                add(new LogarithmicTerm(1, new Variable("x"), 5));
                add(new PolynomialTerm(2, new Variable("y"), 2));
                add(new TrigTerm(1, new Variable("x"), TrigType.SINE));
                add(new ExponentialTerm(1, new Variable("x"), 2));
                add(new TrigTerm(1, new Variable("x"), TrigType.SINE));
                add(new ExponentialTerm(1, new Variable("x"), 2));
                add(new LogarithmicTerm(2, new Variable("x"), 5));
                add(new PolynomialTerm(1, new Variable("y"), 3));
            }
        });

        display.appendScript(new BlackboardLabel(overallEQ.toString(), 0.05));

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
