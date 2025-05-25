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
import whyxzee.blackboard.terms.SignumTerm;
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
        EQSequence eq = new EQSequence(new PolynomialTerm(2), new PolynomialTerm(1, new Variable("x"), 3));
        display.appendScript(new BlackboardLabel(SignumTerm.multiply(new ArrayList<Term>() {
            {
                add(new SignumTerm(2, new USub(eq)));
                add(new SignumTerm(1, new Variable("x")));
                add(new SignumTerm(2, new Variable("x")));

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
