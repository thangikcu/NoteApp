package com.thanggun99.noteapp.presenter;


import com.thanggun99.noteapp.interfaces.MainAction;
import com.thanggun99.noteapp.model.DBManager;
import com.thanggun99.noteapp.model.MainInteractor;
import com.thanggun99.noteapp.model.entity.Note;

public class MainPresenter implements MainAction, MainInteractor.OnTaskFinishListener {
    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenter(MainView mainView, DBManager dbManager) {

        mainInteractor = new MainInteractor(dbManager, this);
        this.mainView = mainView;
    }


    //Task
    @Override
    public void onStartTask() {
        mainView.showProgress();
    }

    @Override
    public void onFinishTask() {
        mainView.hideProgress();
    }


    //get data note
    @Override
    public void getDatas() {
        mainInteractor.getDatas();

    }

    @Override
    public void onFinishGetDatas() {
        mainView.showContent();
    }

    @Override
    public void onGetDatasFail() {
        mainView.showMessageFail();
    }


    //delete note
    public void onClickDeleteAt(int position) {
        mainInteractor.setCurrentPosition(position);
        mainInteractor.setCurrentNote(position);
        mainView.showConfirmDialog();
    }

    @Override
    public void deleteNote() {
        mainInteractor.deleteNote();
    }

    @Override
    public void onFinishDeleteNote() {
        mainView.notifyDataDelete(mainInteractor.getCurrentPosition());
        mainView.showMessageDeleteSucess();
    }

    @Override
    public void onDeleteNoteFail() {
        mainView.showMessageDeleteFail();

    }


    //add note
    public void onClickAddNote() {
        mainView.showCreateNoteActivity();
    }

    @Override
    public void addNote(Note note) {
        mainInteractor.addNote(note);
    }

    @Override
    public void onFinishAddNote() {
        mainView.notifyDataInsert();
        mainView.showMessageAddSuccess();
    }

    @Override
    public void onAddNoteFail() {
        mainView.showMessageAddFail();
    }


    //show note
    public void onClickNote(int position) {
        mainView.showViewNoteDialog(mainInteractor.getNoteAt(position));
    }


    //update note
    public void onClickEditAt(int position) {
        mainInteractor.setCurrentPosition(position);
        mainInteractor.setCurrentNote(position);
        mainView.showUpdateNoteActivity(mainInteractor.getNoteAt(position));
    }

    @Override
    public void updateNote(Note note) {
        mainInteractor.updateNote(note);
    }

    @Override
    public void onFinishUpdateNote() {
        mainView.notifyDataUpdate(mainInteractor.getCurrentPosition());
        mainView.showMessageUpdateSuccess();
    }

    @Override
    public void onUpdateNoteFail() {
        mainView.showMessageUpdateFail();
    }


    public interface MainView {
        void showProgress();

        void hideProgress();

        void showContent();

        void showMessageFail();

        void showMessageDeleteSucess();

        void showMessageDeleteFail();

        void showConfirmDialog();

        void showCreateNoteActivity();

        void showMessageAddSuccess();

        void showMessageAddFail();

        void notifyDataInsert();

        void showViewNoteDialog(Note note);

        void notifyDataDelete(int position);

        void showUpdateNoteActivity(Note note);

        void showMessageUpdateSuccess();

        void showMessageUpdateFail();

        void notifyDataUpdate(int position);
    }

}
