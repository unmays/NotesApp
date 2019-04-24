package com.unmayshroff.notes.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unmayshroff.notes.pojo.Note;
import com.unmayshroff.notes.pojo.NoteResponse;

import java.util.Iterator;

import base.controller.ResponseError;
import base.controller.ResponseListener;

public class NotesModel {

    private static NotesModel instance;
    FirebaseDatabase database;
    DatabaseReference notesRef;

    private NotesModel() {
        database = FirebaseDatabase.getInstance();
        notesRef = database.getReference("notes");
    }

    public static NotesModel getInstance() {
        if (instance == null) {
            instance = new NotesModel();
        }
        return instance;
    }

    public void getNotes(ResponseListener<NoteResponse> responseListener) {
        notesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("NotesModel", "*****onDataChange*****");
                Log.d("NotesModel", "dataSnapshot.getChildrenCount(): " + dataSnapshot.getChildrenCount());
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                NoteResponse noteResponse = new NoteResponse();
                while (iterator.hasNext()) {
                    DataSnapshot nextDataSnapshot = iterator.next();
                    Log.d("NotesModel", "nextDataSnapshot.getValue(): " + nextDataSnapshot.getValue());
                    Note note = nextDataSnapshot.getValue(Note.class);
                    Log.d("NotesModel", "nextDataSnapshot.getKey(): " + nextDataSnapshot.getKey());
                    note.setId(nextDataSnapshot.getKey());
                    noteResponse.getNotes().add(note);
                }
                responseListener.onSuccess(noteResponse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("NotesModel", "*****onCancelled*****");
                Log.d("NotesModel", "databaseError.getMessage()" + databaseError.getMessage());
                ResponseError error = new ResponseError();
                error.setHasException(true);
                responseListener.onError(error);
            }
        });
    }

    public void updateNote(Note note) {
        if (TextUtils.isEmpty(note.getId())) {
            notesRef.push().setValue(note, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.d("NotesModel", "*****updateNote*****");
                        Log.d("NotesModel", "databaseReference.getKey(): " + databaseReference.getKey());
                    }
                }
            });
        } else {
            notesRef.child(note.getId()).setValue(note);
        }
    }

    public void deleteNode(Note note) {
        notesRef.child(note.getId()).removeValue();
    }

}
