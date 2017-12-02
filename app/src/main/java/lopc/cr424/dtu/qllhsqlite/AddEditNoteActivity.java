package lopc.cr424.dtu.qllhsqlite;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import lopc.cr424.dtu.qllhsqlite.Whiskyyy.SinhVien;

import static android.os.ParcelFileDescriptor.MODE_CREATE;

public class AddEditNoteActivity extends AppCompatActivity {

        SinhVien sinhVien;
        private static final int MODE_CREATE = 1;
        private static final int MODE_EDIT = 2;

        private int mode;
        private EditText textTitle;
        private EditText textContent;

        private boolean needRefresh;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_edit_note);

            this.textTitle = (EditText)this.findViewById(R.id.text_note_title);
            this.textContent = (EditText)this.findViewById(R.id.text_note_content);

            Intent intent = this.getIntent();
            this.sinhVien = (SinhVien) intent.getSerializableExtra("note");
            if(sinhVien== null)  {
                this.mode = MODE_CREATE;
            } else  {
                this.mode = MODE_EDIT;
                this.textTitle.setText(sinhVien.getSinhVienTitle());
                this.textContent.setText(sinhVien.getSinhVienContent());
            }

        }


        // Người dùng Click vào nút Save.
        public void buttonSaveClicked(View view)  {
            MyDatabaseHelper db = new MyDatabaseHelper(this);

            String title = this.textTitle.getText().toString();
            String content = this.textContent.getText().toString();

            if(title.equals("") || content.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Please enter title & content", Toast.LENGTH_LONG).show();
                return;
            }

            if(mode==MODE_CREATE ) {
                this.sinhVien= new SinhVien(title,content);
                db.addSinhVien(sinhVien);
            } else  {
                this.sinhVien.setSinhVienTitle(title);
                this.sinhVien.setSinhVienContent(content);
                db.updateSinhVien(sinhVien);
            }

            this.needRefresh = true;
            // Trở lại MainActivity.
            this.onBackPressed();
        }

        // Khi người dùng Click vào button Cancel.
        public void buttonCancelClicked(View view)  {
            // Không làm gì, trở về MainActivity.
            this.onBackPressed();
        }

        // Khi Activity này hoàn thành,
        // có thể cần gửi phản hồi gì đó về cho Activity đã gọi nó.
        @Override
        public void finish() {

            // Chuẩn bị dữ liệu Intent.
            Intent data = new Intent();
            // Yêu cầu MainActivity refresh lại ListView hoặc không.
            data.putExtra("needRefresh", needRefresh);

            // Activity đã hoàn thành OK, trả về dữ liệu.
            this.setResult(Activity.RESULT_OK, data);
            super.finish();
        }

    }
