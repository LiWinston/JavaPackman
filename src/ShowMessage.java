import bagel.Font;

public class ShowMessage {
    final static int SPECIFIC_FONTSIZE = 64;
    private final int X;
    private final int Y;
    private final String messageDetail;
    private final Font ft;


    public ShowMessage(String messageDetail, int X, int Y) {
        this.messageDetail = messageDetail;
        this.X = X;
        this.Y = Y;
        ft = new Font("res/FSO8BITR.TTF", SPECIFIC_FONTSIZE);
    }

    public ShowMessage(String messageDetail, int X, int Y, int fontSize) {
        this.X = X;
        this.Y = Y;
        this.messageDetail = messageDetail;
        this.ft = new Font("res/FSO8BITR.TTF", fontSize);
    }

    /**
     * improve extensibility for future Project
     * for different possible customized fontTypes
     * @param messageDetail Detail of message(String message)
     * @param X             Left-Bottom coordinate X
     * @param Y             Left-Bottom coordinate X
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


    public void Show() {
        ft.drawString(messageDetail, X, Y);
    }

}
