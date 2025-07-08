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
import whyxzee.blackboard.math.pure.equations.*;
import whyxzee.blackboard.math.pure.equations.terms.*;
import whyxzee.blackboard.math.pure.equations.variables.Variable;
import whyxzee.blackboard.math.pure.numbers.*;
import whyxzee.blackboard.math.utils.pure.TrigUtils;
import whyxzee.blackboard.math.utils.pure.TrigUtils.TrigType;
import whyxzee.blackboard.utils.*;

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
                TermArray terms = new TermArray(
                                new PowerTerm(1, xVar),
                                new PowerTerm(-15));

                display.appendScript(
                                AlgebraSolver.performOp("x", lTerms, rTerms),
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
        private static final Variable<String> zVar = new Variable<String>("z");

        /* BNumbers */
        private static final BNum smol = BNum.infinitesimal("dx");
        private static final BNum aleph = BNum.aleph(true, 0);
        // #endregion
}
