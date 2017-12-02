package lopc.cr424.dtu.qllhsqlite.Whiskyyy;

import java.io.Serializable;

/**
 * Created by Admin on 11/29/2017.
 */

public class SinhVien implements Serializable {
    private int SinhVienId;
    private String SinhVienTitle;
    private String SinhVienContent;

    public SinhVien()  {

    }

    public SinhVien(  String SinhVienTitle, String SinhVienContent) {
        this.SinhVienTitle= SinhVienTitle;
        this.SinhVienContent= SinhVienContent;
    }

    public SinhVien(int SinhVienId, String SinhVienTitle, String SinhVienContent) {
        this.SinhVienId= SinhVienId;
        this.SinhVienTitle= SinhVienTitle;
        this.SinhVienContent= SinhVienContent;
    }

    public int getSinhVienId() {
        return SinhVienId;
    }

    public void setSinhVienId(int noteId) {
        this.SinhVienId = noteId;
    }
    public String getSinhVienTitle() {
        return SinhVienTitle;
    }

    public void setSinhVienTitle(String SinhVienTitle) {
        this.SinhVienTitle = SinhVienTitle;
    }


    public String getSinhVienContent() {
        return SinhVienContent;
    }

    public void setSinhVienContent(String SinhVienContent) {
        this.SinhVienContent = SinhVienContent;
    }


    @Override
    public String toString()  {
        return this.SinhVienTitle;
    }
}
