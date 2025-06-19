import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.display.*;
import whyxzee.blackboard.math.pure.algebra.AlgebraUtils;
import whyxzee.blackboard.math.pure.algebra.solver.AlgebraSolver;
import whyxzee.blackboard.math.pure.combinatorics.CombinatoricsUtils;
import whyxzee.blackboard.math.pure.equations.*;
import whyxzee.blackboard.math.pure.numbers.*;
import whyxzee.blackboard.math.pure.numbers.BUncountable.UncountableType;
import whyxzee.blackboard.math.pure.numbers.uncountables.*;
import whyxzee.blackboard.math.pure.terms.*;
import whyxzee.blackboard.math.pure.terms.variables.*;

@SuppressWarnings("unused")
public class Debugger {
        public static void main(String[] args) throws Exception {
                /* Frame stuff */
                JFrame frame = new JFrame();
                BlackboardDisplay display = new BlackboardDisplay(frame);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                /* Math Debugging */
                Variable xVar = new Variable("x");
                Variable yVar = new Variable("y");
                ArrayList<Term> lTerms = new ArrayList<Term>() {
                        {
                                // add(new PowerTerm(2, xVar, 1 / Math.sqrt(2)));
                                add(new PowerTerm(new BNumber(1, -3), xVar, 2));
                                add(new PowerTerm(32));
                        }
                };

                ArrayList<Term> rTerms = new ArrayList<Term>() {
                        {
                                add(new PowerTerm(0));
                        }
                };

                display.appendScript(new BlackboardLabel(
                                AlgebraSolver.performOp(xVar, lTerms, rTerms)
                                                .toString(),
                                0.05));

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
