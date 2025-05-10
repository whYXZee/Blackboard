package whyxzee.blackboard.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import whyxzee.blackboard.Constants;

/**
 * A package for displaying math functions in Swing.
 * 
 * The functionality of this class has been checked on {@code 5/9/2025}, and
 * nothing has changed since.
 */
public class MathDisplay extends JPanel {

    //
    // UI Components
    //
    private final DisplayDaemon DAEMON;
    private final JLabel testLabel = new JLabel("line 1");
    private final JPanel scriptPanel = new JPanel();

    /* Grid */
    private GridBagConstraints grid;

    public MathDisplay(JFrame frame) {
        /* Display layout */
        this.setLayout(new GridBagLayout());
        grid = new GridBagConstraints();
        grid.gridx = 0;
        grid.gridy = 0;
        grid.anchor = GridBagConstraints.CENTER;

        scriptPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scriptPanel.setLayout(new GridBagLayout());

        /* Adding Display */
        this.add(testLabel, grid);
        grid.gridy++;
        this.add(new JLabel("line 2"), grid);
        grid.gridy++;
        this.add(scriptPanel, grid);

        /* Daemon */
        DAEMON = new DisplayDaemon(this, frame);
        DAEMON.start();
    }

    //
    // UI Methods
    //
    public void resizeComponents(Dimension dimension) {
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        testLabel.setFont(
                new Font(Constants.DisplayConstants.FONT_NAME, Constants.DisplayConstants.FONT_STYLE, height / 10));
        scriptPanel.setPreferredSize(new Dimension((int) (width / 1.25), height / 4));
    }
}

/**
 * The daemon thread is for resizing the components of the math display in
 * relation to the frame.
 * 
 * <p>
 * The functionality of the daemon was checked on {@code 5/9/2025}, and nothing
 * has changed since then.
 */
class DisplayDaemon extends Thread {
    /* UI */
    private final MathDisplay DISPLAY;
    private final JFrame FRAME;

    /* Boolean */
    private boolean shouldRun;

    public DisplayDaemon(MathDisplay display, JFrame frame) {
        /* Variable Declarations */
        super("Display Daemon");
        DISPLAY = display;
        FRAME = frame;
        shouldRun = true;

        /* Thread */
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (shouldRun) {
            DISPLAY.resizeComponents(FRAME.getSize());
        }
    }

    //
    // Get & Set Methods
    //
    public void setShouldRun(boolean shouldRun) {
        this.shouldRun = shouldRun;
    }
}
