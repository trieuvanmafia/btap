package lopc.cr424.dtu.qllhsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lopc.cr424.dtu.qllhsqlite.Whiskyyy.SinhVien;

/**
 * Created by Admin on 11/29/2017.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";


    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "SinhVien_Manager";


    // Tên bảng: Note.
    private static final String TABLE_NOTE = "SinhVien";

    private static final String COLUMN_NOTE_ID ="SinhVien_Id";
    private static final String COLUMN_NOTE_TITLE ="SinhVien_Title";
    private static final String COLUMN_NOTE_CONTENT = "SinhVien_Content";
    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Script to create table.
        String script = "CREATE TABLE " + TABLE_NOTE + "("
                                        + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY," + COLUMN_NOTE_TITLE + " TEXT,"
                                        + COLUMN_NOTE_CONTENT + " TEXT" + ")";
        // Execute script.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);


        // Recreate
        onCreate(db);
    }
    public void createDefaultNotesIfNeed()  {
        int count = this.getSinhVienCount();
        if(count ==0 ) {
            SinhVien note1 = new SinhVien("Random Sinh Vien",
                    "Hello Man");
            SinhVien note2 = new SinhVien("Sinh Vien",
                    "Hello Girl");
            this.addSinhVien(note1);
            this.addSinhVien(note2);
        }
    }


    public void addSinhVien(SinhVien sinhVien) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + sinhVien.getSinhVienTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, sinhVien.getSinhVienTitle());
        values.put(COLUMN_NOTE_CONTENT, sinhVien.getSinhVienContent());


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_NOTE, null, values);


        // Đóng kết nối database.
        db.close();
    }


    public SinhVien getSinhVien(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTE, new String[] { COLUMN_NOTE_ID,
                        COLUMN_NOTE_TITLE, COLUMN_NOTE_CONTENT }, COLUMN_NOTE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SinhVien sinhVien = new SinhVien(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return note
        return sinhVien;
    }


    public List<SinhVien> getAllNotes() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<SinhVien> noteList = new ArrayList<SinhVien>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                SinhVien sinhVien = new SinhVien();
                sinhVien.setSinhVienId(Integer.parseInt(cursor.getString(0)));
                sinhVien.setSinhVienTitle(cursor.getString(1));
                sinhVien.setSinhVienContent(cursor.getString(2));

                // Thêm vào danh sách.
                noteList.add(sinhVien);
            } while (cursor.moveToNext());
        }

        // return note list
        return noteList;
    }

    public int getSinhVienCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateSinhVien(SinhVien sinhVien) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... "  + sinhVien.getSinhVienTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, sinhVien.getSinhVienTitle());
        values.put(COLUMN_NOTE_CONTENT, sinhVien.getSinhVienContent());

        // updating row
        return db.update(TABLE_NOTE, values, COLUMN_NOTE_ID + " = ?", new String[]{String.valueOf(sinhVien.getSinhVienId())});
    }

    public void deleteSinhVien(SinhVien sinhVien) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + sinhVien.getSinhVienTitle() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_NOTE_ID + " = ?", new String[] { String.valueOf(sinhVien.getSinhVienId()) });
        db.close();
    }
}
