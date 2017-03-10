package com.thanggun99.noteapp.interfaces;


import com.thanggun99.noteapp.model.entity.Note;

public interface MainAction {

    void getDatas();

    void addNote(Note note);

    void deleteNote();

    void updateNote(Note note);

}
