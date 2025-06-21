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
                ComplexNum[] nums = {
                                new ComplexNum(1, 5),
                                new ComplexNum(-5, 3),

                };

                display.appendScript(
                                ComplexNum.divide(new ComplexNum(0, Value.infinity(true, 0)), nums),
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
        private static final Variable<String> xVar = new Variable<String>("x");
        private static final Variable<String> yVar = new Variable<String>("y");
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
        private static final Value smol = Value.infinitesimal("dx");
        private static final Value aleph = Value.aleph(true, 0);
        // #endregion
}
