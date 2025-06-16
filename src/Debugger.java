import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.display.*;
import whyxzee.blackboard.math.pure.algebra.AlgebraUtils;
import whyxzee.blackboard.math.pure.algebra.equations.SolveFor;
import whyxzee.blackboard.math.pure.equations.AdditiveEQ;
import whyxzee.blackboard.math.pure.equations.MultiplyEQ;
import whyxzee.blackboard.math.pure.numbers.*;
import whyxzee.blackboard.math.pure.numbers.BUncountable.UncountableType;
import whyxzee.blackboard.math.pure.numbers.uncountables.*;
import whyxzee.blackboard.math.pure.terms.PowerTerm;
import whyxzee.blackboard.math.pure.terms.Term;
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
                ArrayList<Term> lTerms = new ArrayList<Term>() {
                        {
                                add(new PowerTerm(1, xVar, 2));
                                add(new PowerTerm(1, xVar));
                                add(new PowerTerm(3));
                        }
                };

                ArrayList<Term> rTerms = new ArrayList<Term>() {
                        {
                                add(new PowerTerm(18));
                        }
                };

                // TermUtils.MultiplyTerms.performMultiply(mEQ.getTerms())
                display.appendScript(new BlackboardLabel(
                                SolveFor.performOp("x", new AdditiveEQ(lTerms), new AdditiveEQ(rTerms))
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
