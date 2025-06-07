import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import whyxzee.blackboard.utils.*;
import whyxzee.blackboard.display.*;
import whyxzee.blackboard.equations.*;
import whyxzee.blackboard.numbers.NumberAbstract;
import whyxzee.blackboard.numbers.RealNumber;
import whyxzee.blackboard.numbers.uncountable.Infinity;
import whyxzee.blackboard.settheory.*;
import whyxzee.blackboard.settheory.arithmetic.UnionBounds;
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
        NaturalSet natSet = new NaturalSet();
        IntegerSet intSet = new IntegerSet();
        RationalSet ratSet = new RationalSet();
        DefinedList list = new DefinedList("A", new ArrayList<NumberAbstract>() {
            {
                add(new RealNumber(0));
                add(new RealNumber(6));
                add(new RealNumber(5));
            }
        });
        SetBuilder builder = new SetBuilder("B", "x", new ArrayList<PredicateAbstract>() {
            {
                add(new ElementOf("x", intSet));
                add(new RangePredicate("x", new RealNumber(-2), false, false, new Infinity()));
            }
        });
        IntervalSet intInf = new IntervalSet("C");
        IntervalSet intOne = new IntervalSet("A", new RealNumber(-50), false, false, new RealNumber(0));
        IntervalSet intTwo = new IntervalSet("B", new RealNumber(0), true, true, new RealNumber(70));
        IntervalSet intTest = new IntervalSet("Test", new RealNumber(0), true, false, new RealNumber(-70));

        display.appendScript(
                new BlackboardLabel(
                        intTest.toString(),
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
