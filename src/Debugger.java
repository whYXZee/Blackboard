import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import whyxzee.blackboard.Constants;
import whyxzee.blackboard.display.*;
import whyxzee.blackboard.math.number.BVal;
import whyxzee.blackboard.utils.*;

@SuppressWarnings("unused")
public class Debugger {
        private static final Loggy loggy = new Loggy(true);

        // #region main()
        public static void main(String[] args) throws Exception {

                BVal exp = new BVal(2);
                BVal a1 = new BVal(2);
                BVal lBound = new BVal(1);
                BVal uBound = new BVal(Double.MAX_VALUE);

                BVal sum = new BVal(0);

                try {
                        FileWriter io = new FileWriter("output.txt");
                        BVal output = a1.divide(exp.add(new BVal(-1)));
                        output = output.multiply(lBound.pow(exp.negate().add(new BVal(1))));

                        io.write("integral: " + output.toConsole());
                        io.write("\nmax val: " + Double.MAX_VALUE);
                        io.close();
                } catch (IOException e) {
                        System.out.println("io failed to initialize");
                }

                for (double i = lBound.getValue(); i < Double.MAX_VALUE; i++) {
                        BVal addend = a1.divide(new BVal(i).pow(exp));
                        sum = sum.add(addend);
                        System.out.println(i + ": " + sum.toConsole());
                }

                // /* Frame stuff */
                // JFrame frame = new JFrame();
                // BlackboardDisplay display = new BlackboardDisplay(frame);
                // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                // /* Math Debugging */
                // // display.appendScript(new BVal(15.2655),
                // // 0.05);

                // /* Displaying */
                // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                // frame.setSize((int) (screenSize.width / 1.5), (int) (screenSize.height /
                // 1.5));
                // frame.setLocationRelativeTo(null);
                // frame.setVisible(true);
                // frame.setResizable(true);
                // frame.setContentPane(display);
        }
        // #endregion
}
