import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import whyxzee.blackboard.utils.SpecialFunctions;
import whyxzee.blackboard.display.BlackboardDisplay;
import whyxzee.blackboard.display.BlackboardLabel;
import whyxzee.blackboard.equations.EQMultiplication;
import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.ExponentialTerm;
import whyxzee.blackboard.terms.LogarithmicTerm;
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.Term;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;

public class Debugger {
    public static void main(String[] args) throws Exception {
        /* Frame stuff */
        JFrame frame = new JFrame();
        BlackboardDisplay display = new BlackboardDisplay(frame);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        /* Math Debugging */
        // System.out.println(PolynomialTerm.simplifyEQMulti(new ArrayList<Term>() {
        // {
        // add(new PolynomialTerm(2, new Variable("x"), 1, 2));
        // add(new PolynomialTerm(2, new Variable("x"), 1));
        // add(new PolynomialTerm(3, new Variable("x"), 3));
        // add(new PolynomialTerm(1, new Variable("y"), 1));
        // add(new PolynomialTerm(4, new Variable("y"), 3, 5));
        // }
        // }).printConsole());

        EQSequence eq = new EQSequence(new PolynomialTerm(2), new PolynomialTerm(1, new Variable("x"), 3));
        display.appendScript(new BlackboardLabel(ExponentialTerm.add(new ArrayList<Term>() {
            {
                add(new ExponentialTerm(4, new Variable("x"), 2));
                add(new ExponentialTerm(4, new Variable("x"), 2));
                add(new ExponentialTerm(1, new USub(eq), 2));

            }
        }).toString(), 0.1));

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
