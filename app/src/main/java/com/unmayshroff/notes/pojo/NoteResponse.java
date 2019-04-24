package com.unmayshroff.notes.pojo;

import java.util.ArrayList;
import java.util.List;

public class NoteResponse {

    private List<Note> notes;

    public NoteResponse() {
        notes = new ArrayList<>();
    }

    public List<Note> getNotes() {
        return notes;
    }

}
