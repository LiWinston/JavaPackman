import bagel.Font;

/**
 * Explaining the purpose of ShowMessage class. While it may seem redundant, it serves to hide font details and is part
 * of the game's specification. It allows for abstraction of changes, such as the use of Java's own font class/function
 * in future games, by adding new constructors and judgments. Overloading Show() can also be used to meet display
 * requirements, such as text dithering or rotation, without taking up unnecessary space in ShadowPac.java.
 *
 * @author @YongchunLi
 */
public class ShowMessage {
    private final static int SPECIFIC_FONTSIZE = 64;
    private final double X;
    private final double Y;
    private final String messageDetail;
    private final Font ft;
    private int fontSize = getSpecificFontsize();


    public ShowMessage(String messageDetail, double X, double Y) {
        this.messageDetail = messageDetail;
        this.X = X;
        this.Y = Y;
        ft = new Font("res/FSO8BITR.TTF", fontSize);
    }

    public ShowMessage(String messageDetail, int X, int Y, int myfontSize) {
        this.X = X;
        this.Y = Y;
        this.fontSize = myfontSize;
        this.messageDetail = messageDetail;
        this.ft = new Font("res/FSO8BITR.TTF", myfontSize);
    }

    /**
     * improve extensibility for future Project
     * for different possible customized fontTypes
     *
     * @param messageDetail Detail of message(String message)
     * @param X             Left-Bottom coordinate X
     * @param Y             Left-Bottom coordinate Y
     * @param fonttype      customized fontType addr
     * @param fontSize      as literal mean
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

    protected static int getSpecificFontsize() {
        return SPECIFIC_FONTSIZE;
    }


    public void Show() {
        ft.drawString(messageDetail, X, Y);
    }

}
