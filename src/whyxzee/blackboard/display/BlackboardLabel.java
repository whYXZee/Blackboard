package whyxzee.blackboard.display;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import whyxzee.blackboard.Constants;

/**
 * A package for the label of symbols, equations, etc.
 * 
 * <p>
 * The functionality of this class has been checked on {@code 5/10/2025}, and
 * nothing has changed since.
 */
public class BlackboardLabel extends JLabel {
    /* UI */
    private String text;
    private double resizeFactor;

    public BlackboardLabel(String text, double resizeFactor) {
        /* Initialize label */
        super(text);

        /* Set data */
        this.text = text;
        this.resizeFactor = resizeFactor;
    }

    @Override
    public void resize(Dimension dimension) {
        super.setFont(new Font(Constants.DisplayConstants.FONT_NAME, Constants.DisplayConstants.FONT_STYLE,
                (int) (dimension.getHeight() * resizeFactor)));
    }

    public void resize(int height) {
        super.setFont(new Font(Constants.DisplayConstants.FONT_NAME, Constants.DisplayConstants.FONT_STYLE,
                (int) (height * resizeFactor)));
    }

    //
    // Get & Set Methods
    //
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        super.setText(text);
    }
}
