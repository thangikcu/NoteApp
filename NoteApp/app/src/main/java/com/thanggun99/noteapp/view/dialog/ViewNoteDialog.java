package com.thanggun99.noteapp.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thanggun99.noteapp.R;
import com.thanggun99.noteapp.model.entity.Note;

public class ViewNoteDialog extends BaseDialog {

    private LinearLayout lnItem;
    private ImageButton btnClose;
    public ViewNoteDialog(Context context) {
        super(context);
        setContentView(R.layout.view_note_dialog);

        lnItem = (LinearLayout) findViewById(R.id.ln_item);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvContent.setMovementMethod(new ScrollingMovementMethod());

        btnClose = (ImageButton) findViewById(R.id.btn_close);

        btnClose.setOnClickListener(this);
    }

    public void setContent(Note note) {
        tvTitle.setText(note.getDate());
        tvContent.setText(note.getContent());
        lnItem.setBackgroundColor(Color.parseColor(note.getColor()));

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
