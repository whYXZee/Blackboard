import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import whyxzee.blackboard.display.BlackboardLabel;
import whyxzee.blackboard.terms.*;
import whyxzee.blackboard.terms.variables.Variable;
import whyxzee.blackboard.display.BlackboardDisplay;

public class Debugger {
    public static void main(String[] args) throws Exception {
        /* Frame stuff */
        JFrame frame = new JFrame();
        BlackboardDisplay display = new BlackboardDisplay(frame);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        /* Math Debugging */
        // PolynomialTerm term = new PolynomialTerm(1, new Variable("x", 2));
        // EQSequence eq = new EQSequence(
        // new PolynomialTerm(1, new Variable("x", 2)),
        // new PolynomialTerm(1));
        // USub uSub = new USub(3, eq);
        // display.appendScript(new BlackboardLabel(uSub.derive().toString(), 0.1));
        AbsoluteValTerm term = new AbsoluteValTerm(2, new Variable("x", 1));
        display.appendScript(new BlackboardLabel(Double.toString(term.solve(-3)), 0.1));

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
