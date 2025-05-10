import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import whyxzee.blackboard.display.MathDisplay;

public class Debugger {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        MathDisplay display = new MathDisplay(frame);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.5));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setContentPane(display);
    }
}
