import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.display.*;
import whyxzee.blackboard.math.pure.algebra.AlgebraUtils;
import whyxzee.blackboard.math.pure.algebra.equations.SolveFor;
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
                                add(new PowerTerm(2, xVar, 1 / Math.sqrt(2)));
                                add(new PowerTerm(32));
                        }
                };

                ArrayList<Term> rTerms = new ArrayList<Term>() {
                        {
                                add(new PowerTerm(0));
                        }
                };

                BNumber base = new BNumber(6325, 23);
                BNumber rInf = new GeneralInfinity(false);
                BNumber iInf = new BNumber(0, 10);
                BNumber power;
                // power = new BNumber(.5, 0);
                power = BUncountable.createCustomUncountable(rInf, iInf);
                BNumber num = new BNumber(8.0 / 3, 0);
                BNumber denom = new BNumber(23, -6);

                display.appendScript(new BlackboardLabel(
                                // SolveFor.performOp("x", new AdditiveEQ(lTerms), new AdditiveEQ(rTerms))
                                BNumber.divide(num, denom)
                                                .toString(),
                                0.05));

                /* Displaying */
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setSize((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5));
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(true);
                // frame.setContentPane(new JScrollPane(display,
                // JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                // JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
                frame.setContentPane(display);
        }
}
