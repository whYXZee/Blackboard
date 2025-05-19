import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import whyxzee.blackboard.display.BlackboardLabel;
import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.PolynomialTerm;
import whyxzee.blackboard.terms.TrigTerm;
import whyxzee.blackboard.terms.TrigTerm.TrigType;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.USubTerm;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.display.BlackboardDisplay;

public class Debugger {
    public static void main(String[] args) throws Exception {
        /* Frame stuff */
        JFrame frame = new JFrame();
        BlackboardDisplay display = new BlackboardDisplay(frame);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        /* Math Debugging */
        TrigTerm term = new TrigTerm(2, new Variable("x", 1), TrigType.COSECANT);
        display.appendScript(new BlackboardLabel(term.toString() + "=" + Double.toString(term.solve(3)), 0.1));

        // PolynomialTerm term = new PolynomialTerm(1, new Variable("x", 2));
        // EQSequence eq = new EQSequence(term, new PolynomialTerm(1));
        // display.appendScript(new BlackboardLabel(eq.derive().toString(), 0.1));

        // PolynomialTerm term = new PolynomialTerm(2.5, new Variable("x", -1, 2));
        // display.appendScript(new BlackboardLabel(term.derive().toString(), 0.1));

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
