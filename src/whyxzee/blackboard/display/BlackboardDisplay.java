package whyxzee.blackboard.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A package for displaying math functions in Swing.
 * 
 * <p>
 * The functionality of this class has been checked on <b>5/10/2025</b>, and
 * nothing has changed.
 */
public class BlackboardDisplay extends JPanel {
    ///
    /// UI Components
    ///
    private final DisplayDaemon DAEMON;
    private final JPanel scriptPanel = new JPanel();

    /* Grid */
    private GridBagConstraints grid;
    private GridBagConstraints scriptGrid;

    /* Labels */
    private ArrayList<BLabel> scriptLabels;

    // #region Constructors
    public BlackboardDisplay(JFrame frame) {
        /* Display layout */
        this.setLayout(new GridBagLayout());
        grid = new GridBagConstraints();
        grid.gridx = 0;
        grid.gridy = 0;
        grid.anchor = GridBagConstraints.CENTER;

        scriptPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scriptPanel.setLayout(new GridBagLayout());

        /* Internal Grids */
        scriptGrid = new GridBagConstraints();
        scriptGrid.gridx = 0;
        scriptGrid.gridy = 0;
        scriptGrid.anchor = GridBagConstraints.CENTER;

        /* Labels */
        scriptLabels = new ArrayList<BLabel>();

        /* Adding Display */
        this.add(scriptPanel, grid); // jscrollpane panel for debugging

        /* Daemon */
        DAEMON = new DisplayDaemon(this, frame);
        DAEMON.start();
    }
    // #endregion

    // #region Resizing
    public void resizeComponents(Dimension dimension) {
        /* Variables */
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        /* Script */
        scriptPanel.setPreferredSize(new Dimension((int) (width / 1.25), height / 4));
        for (BLabel i : scriptLabels) {
            i.resize(height);
        }
    }
    // #endregion

    // #region Script
    /**
     * Updates the contents of the script panel.
     */
    private void updateScriptDisplay() {
        /* Set Up */
        scriptPanel.removeAll();
        scriptGrid.gridx = 0;

        /* Adding */
        for (BLabel i : scriptLabels) {
            scriptPanel.add(i);
            scriptGrid.gridx++;
        }
    }

    /**
     * Appends multiple BLabels to the script panel.
     * 
     * @param labels
     */
    public void appendScript(BLabel... labels) {
        for (BLabel i : labels) {
            scriptLabels.add(i);
        }
        updateScriptDisplay();
    }

    /**
     * Appends a String to the script panel.
     * 
     * @param text
     * @param resizeFactor the multiplier of the font height
     */
    public void appendScript(String text, double resizeFactor) {
        appendScript(new BLabel(text, resizeFactor));
    }

    /**
     * Appends a general object to the script panel.
     * 
     * @param obj          performs {@code .toString()} on the object
     * @param resizeFactor the multiplier of the font height
     */
    public void appendScript(Object obj, double resizeFactor) {
        appendScript(new BLabel(obj, resizeFactor));
    }
    // #endregion
}

// #region Daemon
/**
 * The daemon thread is for resizing the components of the math display in
 * relation to the frame.
 * 
 * <p>
 * The functionality of the daemon was checked on <b>5/9/2025</b>, and nothing
 * has changed since then.
 */
class DisplayDaemon extends Thread {
    /* UI */
    private final BlackboardDisplay DISPLAY;
    private final JFrame FRAME;

    /* Boolean */
    private boolean shouldRun;

    public DisplayDaemon(BlackboardDisplay display, JFrame frame) {
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
            try {
                DISPLAY.resizeComponents(FRAME.getSize());
            } catch (ConcurrentModificationException e) {

            }
        }
    }

    ///
    /// Get & Set Methods
    ///
    public void setShouldRun(boolean shouldRun) {
        this.shouldRun = shouldRun;
    }
}
// #endregion
