import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import whyxzee.blackboard.display.BlackboardLabel;
import whyxzee.blackboard.equations.EQSequence;
import whyxzee.blackboard.terms.*;
import whyxzee.blackboard.terms.TrigTerm.TrigType;
import whyxzee.blackboard.terms.variables.USub;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.display.BlackboardDisplay;

public class Debugger {
    public static void main(String[] args) throws Exception {
        /* Frame stuff */
        JFrame frame = new JFrame();
        BlackboardDisplay display = new BlackboardDisplay(frame);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        /* Math Debugging */
        EQSequence eq = new EQSequence(
                new PolynomialTerm(3, new Variable("x"), 3),
                new PolynomialTerm(5, new Variable("x")),
                new PolynomialTerm(4),
                new PolynomialTerm(1, new USub(new TrigTerm(1, new Variable("x"), TrigType.SINE)), 2),
                new TrigTerm(1, new Variable("x"), TrigType.COTANGENT));
        PolynomialTerm term = new PolynomialTerm(1, new USub(eq), 35);
        display.appendScript(new BlackboardLabel(term.derive().toString(), 0.02));

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
