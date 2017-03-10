package com.thanggun99.noteapp.model;

import android.os.AsyncTask;

import com.thanggun99.noteapp.interfaces.MainAction;
import com.thanggun99.noteapp.model.entity.Note;

public class MainInteractor implements MainAction {

    private DBManager dbManager;
    private OnTaskFinishListener onTaskFinishListener;
    private Note currentNote;
    private int currentPosition;

    public MainInteractor(DBManager dbManager, OnTaskFinishListener onTaskFinishListener) {

        this.dbManager = dbManager;
        this.onTaskFinishListener = onTaskFinishListener;
    }

    @Override
    public void getDatas() {
        class GetDatasTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onTaskFinishListener.onStartTask();

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onTaskFinishListener.onFinishGetDatas();

                } else {
                    onTaskFinishListener.onGetDatasFail();
                }
                onTaskFinishListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay();

                return dbManager.getNoteListFromDatabase();
            }
        }
        new GetDatasTask().execute();

    }

    @Override
    public void deleteNote() {
        class DeleteNoteTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onTaskFinishListener.onStartTask();

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onTaskFinishListener.onFinishDeleteNote();

                } else {

                    onTaskFinishListener.onDeleteNoteFail();
                }
                onTaskFinishListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay();
                return dbManager.deleteNote(currentNote);
            }
        }
        new DeleteNoteTask().execute();
    }

    @Override
    public void updateNote(final Note note) {
        class UpdateNoteTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onTaskFinishListener.onStartTask();

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    currentNote.setColor(note.getColor());
                    currentNote.setContent(note.getContent());
                    currentNote.setDate(note.getDate());

                    onTaskFinishListener.onFinishUpdateNote();
                } else {

                    onTaskFinishListener.onUpdateNoteFail();
                }
                onTaskFinishListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay();

                note.setId(currentNote.getId());
                return dbManager.updateNote(note);
            }
        }
        new UpdateNoteTask().execute();
    }


    private void delay() {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentNote(int position) {
        this.currentNote = dbManager.getNoteAt(position);
    }

    @Override
    public void addNote(final Note note) {
        class AddNoteTask extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected void onPreExecute() {
                onTaskFinishListener.onStartTask();

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    onTaskFinishListener.onFinishAddNote();
                    currentNote = note;
                } else {

                    onTaskFinishListener.onAddNoteFail();
                }
                onTaskFinishListener.onFinishTask();
                super.onPostExecute(aBoolean);
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                delay();
                return dbManager.addNote(note);
            }
        }
        new AddNoteTask().execute();
    }

    public Note getNoteAt(int position) {
        return dbManager.getNoteAt(position);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }


    public interface OnTaskFinishListener {
        void onFinishGetDatas();

        void onGetDatasFail();

        void onFinishDeleteNote();

        void onDeleteNoteFail();

        void onFinishTask();

        void onStartTask();

        void onFinishAddNote();

        void onAddNoteFail();

        void onFinishUpdateNote();

        void onUpdateNoteFail();
    }
}

