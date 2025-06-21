package whyxzee.blackboard.display;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import whyxzee.blackboard.Constants;

/**
 * A package for the label of symbols, equations, etc.
 * 
 * <p>
 * The functionality of this class has been checked on <b>5/10/2025</b>, and
 * nothing has changed since.
 */
public class BLabel extends JLabel {
    /* UI */
    private String text;
    private double resizeFactor;

    // #region Constructors
    public BLabel(String text, double resizeFactor) {
        /* Initialize label */
        super(text);

        /* Set data */
        this.text = text;
        this.resizeFactor = resizeFactor;
    }

    /**
     * A constructor for a BLabel which does not require a String input.
     * 
     * @param obj          performs {@code .toString()} on the object.
     * @param resizeFactor what the multiplier is on the font height
     */
    public BLabel(Object obj, double resizeFactor) {
        /* Initialize label */
        super(obj.toString());

        /* Set data */
        this.text = obj.toString();
        this.resizeFactor = resizeFactor;
    }
    // #endregion

    // #region Resizing
    @Override
    public final void resize(Dimension dimension) {
        super.setFont(new Font(Constants.Display.FONT_NAME, Constants.Display.FONT_STYLE,
                (int) (dimension.getHeight() * resizeFactor)));
    }

    public final void resize(int height) {
        super.setFont(new Font(Constants.Display.FONT_NAME, Constants.Display.FONT_STYLE,
                (int) (height * resizeFactor)));
    }
    // #endregion

    // #region Get / Set
    public final String getText() {
        return text;
    }

    public final void setText(String text) {
        this.text = text;
        super.setText(text);
    }

    public final double getResizeFactor() {
        return resizeFactor;
    }

    public final void setResizeFactor(double resizeFactor) {
        this.resizeFactor = resizeFactor;
    }
    // #endregion
}
