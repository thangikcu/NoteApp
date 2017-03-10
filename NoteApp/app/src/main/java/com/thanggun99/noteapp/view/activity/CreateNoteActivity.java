package com.thanggun99.noteapp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.thanggun99.noteapp.R;
import com.thanggun99.noteapp.model.entity.Note;
import com.thanggun99.noteapp.util.Util;

import java.util.HashMap;

public class CreateNoteActivity extends Activity implements View.OnClickListener {
    public static final int RESULT_OK = 1;
    public static final int RESULT_NULL = 0;
    public static final String NOTE_DATA = "NOTE_DATA";

    private EditText edtContenNote;
    private DatePicker datePicker;
    private View[] colorPick;
    private Button btnCreateNote;
    private HashMap<Integer, String> hashMapStringHexColor;
    private int[] ids;
    private Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_note_activity);
        note = (Note) getIntent().getSerializableExtra(MainActivity.NOTE_UPDATE);

        findViews();
        initComponents();
        setEvents();
    }

    private void initComponents() {

        ids = new int[]{R.id.note_1, R.id.note_2, R.id.note_3, R.id.note_4, R.id.note_5};

        hashMapStringHexColor = new HashMap<>();
        hashMapStringHexColor.put(ids[0], Util.getStringByid(R.color.colorPick1));
        hashMapStringHexColor.put(ids[1], Util.getStringByid(R.color.colorPick2));
        hashMapStringHexColor.put(ids[2], Util.getStringByid(R.color.colorPick3));
        hashMapStringHexColor.put(ids[3], Util.getStringByid(R.color.colorPick4));
        hashMapStringHexColor.put(ids[4], Util.getStringByid(R.color.colorPick5));

        colorPick = new View[hashMapStringHexColor.size()];
        int i = 0;
        for (View view : colorPick) {
            view = findViewById(getIDColorPick(i));
            i++;
            view.setOnClickListener(this);
        }
    }

    private void setEvents() {
        if (note != null) {
            btnCreateNote.setText(Util.getStringByid(R.string.cap_nhat));
            edtContenNote.setText(note.getContent());
            edtContenNote.setBackgroundColor(Color.parseColor(note.getColor()));

            String[] date = note.getDate().split("/");

            datePicker.init(Integer.parseInt(date[2]), (Integer.parseInt(date[1]) - 1), Integer.parseInt(date[0]), null);
        }

        btnCreateNote.setOnClickListener(this);
        datePicker.requestFocus();
        edtContenNote.setMovementMethod(new ScrollingMovementMethod());
    }

    private int getIDColorPick(int i) {
        return ids[i];
    }

    private void findViews() {
        edtContenNote = (EditText) findViewById(R.id.edt_conten_note);
        datePicker = (DatePicker) findViewById(R.id.date_picker);
        btnCreateNote = (Button) findViewById(R.id.btn_create_note);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_create_note) {
            if (TextUtils.isEmpty(edtContenNote.getText())) {
                edtContenNote.setError(Util.getStringByid(R.string.nhap_ghi_nho));
                edtContenNote.requestFocus();
                return;
            }

            Note note = new Note();
            note.setContent(edtContenNote.getText().toString().trim());
            note.setDate(datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear());
            note.setColor(Util.convertColorToHex(edtContenNote.getBackground()));

            Intent intent = new Intent();

            intent.putExtra(NOTE_DATA, note);

            setResult(RESULT_OK, intent);
            finish();
        } else {
            if (!v.isSelected()) {
                setColorView(v.getId());
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_NULL);
        super.onBackPressed();
    }

    private void setColorView(int id) {
        edtContenNote.setBackgroundColor(Color.parseColor(hashMapStringHexColor.get(id)));

    }
}
