import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import whyxzee.blackboard.utils.*;
import whyxzee.blackboard.display.*;
import whyxzee.blackboard.equations.*;
import whyxzee.blackboard.numbers.ComplexNumber;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.numbers.RealNumber;
import whyxzee.blackboard.settheory.SetAbstract;
import whyxzee.blackboard.settheory.SetBuilder;
import whyxzee.blackboard.settheory.predicates.PredicateAbstract;
import whyxzee.blackboard.settheory.predicates.InequalityPredicate.InequalityType;
import whyxzee.blackboard.settheory.predicates.*;
import whyxzee.blackboard.settheory.sets.*;
import whyxzee.blackboard.terms.*;
import whyxzee.blackboard.terms.arithmetic.*;
import whyxzee.blackboard.terms.arithmetic.special.*;
import whyxzee.blackboard.terms.variables.*;

public class Debugger {
    public static void main(String[] args) throws Exception {
        /* Frame stuff */
        JFrame frame = new JFrame();
        BlackboardDisplay display = new BlackboardDisplay(frame);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        /* Math Debugging */
        ArrayList<PredicateAbstract> predicates = new ArrayList<PredicateAbstract>() {
            {
                // add(new SetInequality("x", InequalityType.LESS_THAN, 0));
                // add(new SetEvenOdd("x", true));
            }
        };
        SetAbstract[] domains = { new ComplexSet() };
        SetBuilder set = new SetBuilder("A", "x", domains, predicates);
        NumberAbstract number = new ComplexNumber(1, -5);
        display.appendScript(new BlackboardLabel(set.toString(), 0.05));

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
