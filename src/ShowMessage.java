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

    public void Show() {
        ft.drawString(messageDetail, X, Y);
    }

}
