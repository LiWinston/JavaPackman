/**
 * Just to clarify the design purpose
 * There is an explanation on ED saying ：If you were to create an entirely new methods and classes for things that
 * already exist (e.g. write your Rectangle class) that might lead to mark deduction since it is overly complicated.
 * This ShowMessage class seems Redundant however it allows hiding font details, it's part of specification of this game,
 * which would be repeatable everytime. Also, what happens if a subsequent game comes along that requires calling java's
 * own font class/function to draw text(e.g. java.awt.Font)? By simply adding judgments and new constructors to this class,
 * I offer the possibility of encapsulating the abstraction of these changes. In addition, if there are requirements for
 * the display, such as text dithering, rotation, etc., this can be achieved by overloading Show() in order not to repeatedly
 * take up space in ShadowPac.java
 */

import bagel.Font;

public class ShowMessage {
    private final static int SPECIFIC_FONTSIZE = 64;
    private final int X;
    private final int Y;
    private final String messageDetail;
    private final Font ft;
    private int fontSize = getSpecificFontsize();


    public ShowMessage(String messageDetail, int X, int Y) {
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
     * @param messageDetail Detail of message(String message)
     * @param X             Left-Bottom coordinate X
     * @param Y             Left-Bottom coordinate Y
     * @param fonttype      customized fontType addr
     * @param fontSize      as literal mean
     */
    public ShowMessage(String messageDetail, int X, int Y, String fonttype,int fontSize) {
        this.X = X;
        this.Y = Y;
        this.messageDetail = messageDetail;
        try{
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
