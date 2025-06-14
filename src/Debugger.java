import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.display.*;
import whyxzee.blackboard.math.pure.algebra.equations.CompleteSquare;
import whyxzee.blackboard.math.pure.algebra.equations.SolveFor;
import whyxzee.blackboard.math.pure.equations.SequentialFunc;
import whyxzee.blackboard.math.pure.numbers.*;
import whyxzee.blackboard.math.pure.numbers.BUncountable.UncountableType;
import whyxzee.blackboard.math.pure.numbers.uncountables.*;
import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.TermUtils;
import whyxzee.blackboard.math.pure.terms.variables.USub;
import whyxzee.blackboard.math.pure.terms.variables.Variable;

public class Debugger {
        public static void main(String[] args) throws Exception {
                /* Frame stuff */
                JFrame frame = new JFrame();
                BlackboardDisplay display = new BlackboardDisplay(frame);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                /* Math Debugging */
                Variable xVar = new Variable("x");
                SequentialFunc lFunc = new SequentialFunc(new PowerTerm(1, xVar, 2));
                SequentialFunc rFunc = new SequentialFunc(new PowerTerm(-25));
                SequentialFunc quadFunc = new SequentialFunc(new PowerTerm(2, xVar, 2), new PowerTerm(-12, xVar),
                                new PowerTerm(6));
                // CompleteSquare.performOp("x", quadFunc.getTerms(), new BNumber(18, 0))
                display.appendScript(new BlackboardLabel(
                                SolveFor.performOp("x", lFunc, rFunc).toString(), 0.05));

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
