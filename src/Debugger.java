import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import whyxzee.blackboard.utils.*;
import whyxzee.blackboard.display.*;
import whyxzee.blackboard.equations.*;
import whyxzee.blackboard.terms.*;
import whyxzee.blackboard.terms.arithmetic.*;
import whyxzee.blackboard.terms.arithmetic.logarithmic.ExpandLog;
import whyxzee.blackboard.terms.arithmetic.special.*;
import whyxzee.blackboard.terms.variables.*;

public class Debugger {
    public static void main(String[] args) throws Exception {
        /* Frame stuff */
        JFrame frame = new JFrame();
        BlackboardDisplay display = new BlackboardDisplay(frame);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        /* Math Debugging */
        PowerTerm powTerm = new PowerTerm(-1, new Variable("x"), 4, 3);
        display.appendScript(new BlackboardLabel(powTerm.derive().toString(), 0.05));

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
