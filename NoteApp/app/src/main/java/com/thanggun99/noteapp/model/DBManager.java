package com.thanggun99.noteapp.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thanggun99.noteapp.App;
import com.thanggun99.noteapp.model.entity.Note;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DBManager {
    private static final String DATABASE_NAME = "database.sqlite";
    private static final String PATH = App.getContext().getApplicationInfo().dataDir + "/databases/";

    //private static final String DATABASE_PATH = App.getContext().getDatabasePath(DATABASE_NAME).getPath();

    private static final String NOTE_TABLE = "Note";
    private static final String GET_ALL_NOTE_SQL = "SELECT * FROM " + NOTE_TABLE + " ORDER BY id DESC";

    private SQLiteDatabase sqLiteDatabase;


    private ArrayList<Note> noteList;

    public DBManager() {
        coppyDatabase();
    }

    private void coppyDatabase() {
        try {

            File file = new File(PATH + DATABASE_NAME);

            if (file.exists()) {
                return;
            } else {
                File folder = new File(PATH);

                if (!folder.exists()) {
                    folder.mkdir();
                }

                InputStream input = App.getContext().getAssets().open(DATABASE_NAME);
                OutputStream output = new FileOutputStream(file);
                byte[] bytes = new byte[1024];

                int length;
                while ((length = input.read(bytes)) > 0) {
                    output.write(bytes, 0, length);
                }
                output.flush();
                input.close();
                output.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Note> getNoteList() {
        return this.noteList;
    }

    private void openDB() {
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            sqLiteDatabase = SQLiteDatabase.openDatabase(
                    PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    private void closeDB() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
            sqLiteDatabase.close();
        }
    }


    public boolean getNoteListFromDatabase() {
        openDB();
        Cursor cursor = sqLiteDatabase.rawQuery(GET_ALL_NOTE_SQL, null);
        if (cursor == null || cursor.getCount() == 0) {
            return false;
        }
        noteList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            noteList.add(new Note(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("color"))));
            cursor.moveToNext();
        }
        cursor.close();
        closeDB();
        return true;
    }


    public boolean deleteNote(Note note) {
        openDB();
        String[] id = {String.valueOf(note.getId())};
        int row = sqLiteDatabase.delete(NOTE_TABLE, "id = :id", id);
        closeDB();
        if (row > 0) {
            Log.d(TAG, "xoa on db");
            this.noteList.remove(note);
            return true;
        }

        return false;
    }

    public boolean addNote(Note note) {
        openDB();

        ContentValues contentValues = new ContentValues();
        contentValues.put("date", note.getDate());
        contentValues.put("content", note.getContent());
        contentValues.put("color", note.getColor());
        int row = (int) sqLiteDatabase.insert(NOTE_TABLE, null, contentValues);
        closeDB();

        if (row > 0) {
            note.setId(row);
            this.noteList.add(0, note);
            return true;
        }
        return false;
    }

    public Note getNoteAt(int position) {
        return noteList.get(position);
    }

    public Boolean updateNote(Note note) {
        openDB();

        ContentValues contentValues = new ContentValues();
        contentValues.put("date", note.getDate());
        contentValues.put("content", note.getContent());
        contentValues.put("color", note.getColor());

        String[] id = {String.valueOf(note.getId())};
        int row = sqLiteDatabase.update(NOTE_TABLE, contentValues, "id = :id", id);
        closeDB();

        if (row > 0) {
            return true;
        }
        return false;
    }
}
