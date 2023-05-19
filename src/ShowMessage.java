import bagel.Font;

/**
 * The ShowMessage class is responsible for displaying messages on the screen.
 * It provides abstraction for font details and allows for easy customization and extension.
 * The class is used to display messages in the game without directly referencing font details.
 * It also provides different constructors for flexibility in specifying font type and size.
 * The ShowMessage class is part of the game's specification and allows for easy changes and enhancements.
 * The ShowMessage class can be used to display messages with different fonts and sizes.
 * The ShowMessage class provides methods to show the message on the screen.
 *
 * <p>Example usage:</p>
 * <pre>
 * ShowMessage showMessage = new ShowMessage("Hello, world!", 100, 100);
 * showMessage.Show();
 * </pre>
 *
 * @see <a href="https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/bagel/Font.html">bagel.util.Font</a>
 * @author YongchunLi
 */
public class ShowMessage {
    private final static int SPECIFIC_FONTSIZE = 64;
    private final double X;
    private final double Y;
    private final String messageDetail;
    private final Font ft;
    private int fontSize = getSpecificFontsize();

    /**
     * Constructs a ShowMessage object with the specified message, X and Y coordinates.
     *
     * @param messageDetail the detail of the message to be displayed
     * @param X             the X coordinate of the message's left-bottom position
     * @param Y             the Y coordinate of the message's left-bottom position
     */
    public ShowMessage(String messageDetail, double X, double Y) {
        this.messageDetail = messageDetail;
        this.X = X;
        this.Y = Y;
        ft = new Font("res/FSO8BITR.TTF", fontSize);
    }

    /**
     * Constructs a ShowMessage object with the specified message, X and Y coordinates, and font size.
     *
     * @param messageDetail the detail of the message to be displayed
     * @param X             the X coordinate of the message's left-bottom position
     * @param Y             the Y coordinate of the message's left-bottom position
     * @param myfontSize    the font size of the message
     */
    public ShowMessage(String messageDetail, int X, int Y, int myfontSize) {
        this.X = X;
        this.Y = Y;
        this.fontSize = myfontSize;
        this.messageDetail = messageDetail;
        this.ft = new Font("res/FSO8BITR.TTF", myfontSize);
    }

    /**
     * Constructs a ShowMessage object with the specified message, X and Y coordinates, font type, and font size.
     *
     * @param messageDetail the detail of the message to be displayed
     * @param X             the X coordinate of the message's left-bottom position
     * @param Y             the Y coordinate of the message's left-bottom position
     * @param fonttype      the address of the customized font type
     * @param fontSize      the font size of the message
     */
    public ShowMessage(String messageDetail, int X, int Y, String fonttype, int fontSize) {
        this.X = X;
        this.Y = Y;
        this.messageDetail = messageDetail;
        try {
            this.ft = new Font(fonttype, fontSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the specific font size.
     *
     * @return the specific font size
     */
    public static int getSpecificFontsize() {
        return SPECIFIC_FONTSIZE;
    }

    /**
     * Shows the message on the screen using the specified font.
     */
    public void Show() {
        ft.drawString(messageDetail, X, Y);
    }

}
