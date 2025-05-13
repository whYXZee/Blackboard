package whyxzee.blackboard.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A package for displaying math functions in Swing.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/10/2025}, and
 * nothing has changed.
 */
public class BlackboardDisplay extends JPanel {

    //
    // UI Components
    //
    private final DisplayDaemon DAEMON;
    private final BlackboardLabel testLabel2 = new BlackboardLabel("line 2: electric boogaloo", .1);
    private final JPanel scriptPanel = new JPanel();

    /* Grid */
    private GridBagConstraints grid;
    private GridBagConstraints scriptGrid;

    /* Labels */
    private ArrayList<BlackboardLabel> scriptLabels;

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
        scriptLabels = new ArrayList<BlackboardLabel>();

        /* Adding Display */
        this.add(testLabel2, grid);
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
        /* Variables */
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        // testLabel.setFont(
        // new Font(Constants.DisplayConstants.FONT_NAME,
        // Constants.DisplayConstants.FONT_STYLE, height / 10));

        testLabel2.resize(dimension);

        /* Script */
        scriptPanel.setPreferredSize(new Dimension((int) (width / 1.25), height / 4));
        for (BlackboardLabel i : scriptLabels) {
            i.resize(height);
        }
    }

    private void updateScriptDisplay() {
        /* Set Up */
        scriptPanel.removeAll();
        scriptGrid.gridx = 0;

        /* Adding */
        for (BlackboardLabel i : scriptLabels) {
            scriptPanel.add(i);
            scriptGrid.gridx++;
        }
    }

    //
    // Get, Set, & Append Methods
    //
    public void appendScript(BlackboardLabel... labels) {
        for (BlackboardLabel i : labels) {
            scriptLabels.add(i);
        }
        updateScriptDisplay();
    }

    public void appendScript(String text, double resizeFactor) {
        appendScript(new BlackboardLabel(text, resizeFactor));
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
