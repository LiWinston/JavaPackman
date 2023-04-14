import bagel.*;
public class ShowMessage {
    final private String messageDetail;
    private Font ft;
    final int SPECIFIC_FONTSIZE = 24;

    public ShowMessage(String messageDetail) {
        this.messageDetail = messageDetail;
        ft = new Font("res/FSO8BITR.TTF",SPECIFIC_FONTSIZE);
    }

    public ShowMessage(String messageDetail, int fontSize) {
        this.messageDetail = messageDetail;
        this.ft = new Font("res/FSO8BITR.TTF",fontSize);
    }
    //    public void setFt(Font ft) {
//        this.ft = ft;
//    }

    public void Show(int x, int y){
        ft.drawString(messageDetail, x, y);
    }
    public void Show(int x, int y, DrawOptions drop){
        ft.drawString(messageDetail, x, y, drop);
    }

}
