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
import whyxzee.blackboard.utils.Loggy;
import whyxzee.blackboard.utils.UnicodeUtils;

@SuppressWarnings("unused")
public class Debugger {
        private static final Loggy loggy = new Loggy(true);

        // #region main()
        public static void main(String[] args) throws Exception {
                /* Frame stuff */
                JFrame frame = new JFrame();
                BlackboardDisplay display = new BlackboardDisplay(frame);
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                /* Math Debugging */

                AdditiveEQ eq = new AdditiveEQ(new PowerTerm(2), new PowerTerm(new BNumber(2, 4), xVar));
                AdditiveEQ superEQ = new AdditiveEQ(new PowerTerm(2), new PowerTerm(new BNumber(2, 4), xVar),
                                new PowerTerm(3, xVar, 2));

                ArrayList<Term> pmTerms = new ArrayList<Term>() {
                        {
                                add(new PlusMinusTerm(3));
                                add(new PlusMinusTerm(5));
                                add(new PlusMinusTerm(10));
                        }
                };

                ArrayList<BNumber> nums = new ArrayList<BNumber>() {
                        {
                                add(new Infinitesimal("x"));
                                add(new BNumber(3, 0));
                                add(new BNumber(2, 5));
                                add(new BNumber(4, -3));
                                add(new BNumber(-2, -1));
                        }
                };

                display.appendScript(
                                // TermUtils.addConstantPlusMinusTerms(pmTerms),
                                new BNumber(3, 0).equals(3),
                                0.05);

                /* Displaying */
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setSize((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5));
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(true);
                frame.setContentPane(display);
        }
        // #endregion

        // #region Variables
        private static final Variable xVar = new Variable("x");
        private static final Variable yVar = new Variable("y");
        private static final ArrayList<Term> lTerms = new ArrayList<Term>() {
                {
                        add(new PowerTerm(1, xVar, 4));
                        add(new PowerTerm(1));
                }
        };
        private static final ArrayList<Term> rTerms = new ArrayList<Term>() {
                {
                        add(new PowerTerm(0));
                }
        };

        /* BNumbers */
        private static final Infinitesimal smol = new Infinitesimal("x", true);
        // #endregion
}
