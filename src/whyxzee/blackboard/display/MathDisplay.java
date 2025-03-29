package whyxzee.blackboard.display;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class which contains a way to display math in JSwing.
 */
public class MathDisplay extends JPanel {
    //
    // Display
    //
    private Dimension scriptSize = new Dimension(10, 10);
    private Dimension superscriptSize = new Dimension(10, 10);
    private Dimension subscriptSize = new Dimension(10, 10);
    private Font scriptFont = new Font("Noto Sans", Font.PLAIN, 14);
    private Font superscriptFont = new Font("Noto Sans", Font.PLAIN, 14);
    private Font subscriptFont = new Font("Noto Sans", Font.PLAIN, 14);
    private JPanel scriptPanel = new JPanel();
    private JPanel superscriptPanel = new JPanel();
    private JPanel subscriptPanel = new JPanel();

    // Text
    private JLabel script = new JLabel();
    private JLabel superscript = new JLabel();
    private JLabel subscript = new JLabel();

    public MathDisplay() {

    }

    /**
     * Resizes the size of the MathDisplay.
     * 
     * @param width  the width of the frame
     * @param height the height of the frame
     */
    public void resize(int width, int height) {
        // Panels
        scriptSize = new Dimension(width, height);
        superscriptSize = new Dimension(width, height);
        subscriptSize = new Dimension(width, height);

        scriptPanel.setSize(scriptSize);
        superscriptPanel.setSize(superscriptSize);
        subscriptPanel.setSize(subscriptSize);

        // Font
        scriptFont = new Font("Noto Sans", Font.PLAIN, 14);
        superscriptFont = new Font("Noto Sans", Font.PLAIN, 14);
        subscriptFont = new Font("Noto Sans", Font.PLAIN, 14);

        script.setFont(scriptFont);
        superscript.setFont(superscriptFont);
        subscript.setFont(subscriptFont);
    }

    //
    // Get, Set, and Update Methods
    //

    public void updateScript(String text) {
        script.setText(text);
    }

    public void updateSubscript(String text) {
        subscript.setText(text);
    }

    public void updateSuperscript(String text) {
        superscript.setText(text);
    }

}
