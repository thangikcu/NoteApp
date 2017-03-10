package com.thanggun99.noteapp.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.thanggun99.noteapp.R;
import com.thanggun99.noteapp.adapter.AdapterNote;
import com.thanggun99.noteapp.model.DBManager;
import com.thanggun99.noteapp.model.entity.Note;
import com.thanggun99.noteapp.presenter.MainPresenter;
import com.thanggun99.noteapp.util.Util;
import com.thanggun99.noteapp.view.dialog.ConfirmDialog;
import com.thanggun99.noteapp.view.dialog.ViewNoteDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainPresenter.MainView {

    private static final int ACTIVITY_CREATE_NOTE = 1996;
    private static final int ACTIVITY_UPDATE_NOTE = 0605;
    public static final String NOTE_UPDATE = "NOTE_UPDATE";

    private RecyclerView listView;
    private AdapterNote adapterNote;
    private DBManager dbManager;
    private ConfirmDialog confirmDialog;
    private ViewNoteDialog viewNoteDialog;
    private ImageView btnAdd;
    private MainPresenter mainPresenter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
        initComponets();
        setEvents();
        mainPresenter.getDatas();
    }

    private void initComponets() {
        dbManager = new DBManager();
        mainPresenter = new MainPresenter(this, dbManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Util.getStringByid(R.string.doi_teo));

        viewNoteDialog = new ViewNoteDialog(this);
        confirmDialog = new ConfirmDialog(this, mainPresenter);
        adapterNote = new AdapterNote(mainPresenter);

    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        if (confirmDialog != null) {
            confirmDialog.cancel();
        }
        if (viewNoteDialog != null) {
            viewNoteDialog.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void showMessageFail() {
        showToast(Util.getStringByid(R.string.tai_du_lieu_that_bai));
    }

    private void setEvents() {
        btnAdd.setOnClickListener(this);

        //thêm space vào sau list note cuối cùng
        listView.addItemDecoration(new BottomOffsetDecoration(960));

    }

    private void findViews() {
        btnAdd = (ImageView) findViewById(R.id.btn_add);
        listView = (RecyclerView) findViewById(R.id.list_view_note);
    }

    @Override
    public void showViewNoteDialog(Note note) {
        viewNoteDialog.setContent(note);
        viewNoteDialog.show();
    }

    @Override
    public void showContent() {
        adapterNote.setDatas(dbManager.getNoteList());
        listView.setAdapter(adapterNote);
        listView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void showMessageDeleteSucess() {
        showToast(Util.getStringByid(R.string.xoa_thanh_cong));
    }

    @Override
    public void showMessageDeleteFail() {
        showToast(Util.getStringByid(R.string.xoa_that_bai));
    }

    @Override
    public void showConfirmDialog() {
        confirmDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {
            mainPresenter.onClickAddNote();
        }
    }

    @Override
    public void showCreateNoteActivity() {
        startActivityForResult(new Intent(this, CreateNoteActivity.class), ACTIVITY_CREATE_NOTE);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_CREATE_NOTE) {
            if (resultCode == CreateNoteActivity.RESULT_OK) {

                Note note = (Note) data.getSerializableExtra(CreateNoteActivity.NOTE_DATA);

                mainPresenter.addNote(note);

            }
        }
        if (requestCode == ACTIVITY_UPDATE_NOTE) {
            if (resultCode == CreateNoteActivity.RESULT_OK) {
                Note note = (Note) data.getSerializableExtra(CreateNoteActivity.NOTE_DATA);

                mainPresenter.updateNote(note);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showMessageUpdateSuccess() {
        showToast(Util.getStringByid(R.string.cap_nhat_thanh_cong));
    }

    @Override
    public void showMessageUpdateFail() {
        showToast(Util.getStringByid(R.string.cap_nhat_that_bai));
    }

    @Override
    public void notifyDataUpdate(int position) {
        adapterNote.notifyItemChanged(position);
    }

    @Override
    public void notifyDataInsert() {
        adapterNote.notifyDataSetChanged();
    }

    @Override
    public void showUpdateNoteActivity(Note note) {
        Intent intent = new Intent(this, CreateNoteActivity.class);
        intent.putExtra(NOTE_UPDATE, note);

        startActivityForResult(intent, ACTIVITY_UPDATE_NOTE);
    }

    @Override
    public void notifyDataDelete(int position) {
        adapterNote.notifyItemRemoved(position);
        adapterNote.notifyItemChanged(adapterNote.getItemCount() - 1);
    }

    @Override
    public void showMessageAddSuccess() {
        listView.smoothScrollToPosition(0);
        showToast(Util.getStringByid(R.string.them_moi_thanh_cong));
    }

    @Override
    public void showMessageAddFail() {
        showToast(Util.getStringByid(R.string.them_moi_that_bai));
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    static class BottomOffsetDecoration extends RecyclerView.ItemDecoration {
        private int mBottomOffset;

        public BottomOffsetDecoration(int bottomOffset) {
            mBottomOffset = bottomOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int dataSize = state.getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (dataSize > 0 && position == dataSize - 1) {
                outRect.set(0, 0, 0, mBottomOffset);
            } else {
                outRect.set(0, 0, 0, 0);
            }

        }
    }
}
