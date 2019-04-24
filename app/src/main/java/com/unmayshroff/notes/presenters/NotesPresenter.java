package com.unmayshroff.notes.presenters;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.unmayshroff.notes.models.NotesModel;
import com.unmayshroff.notes.pojo.Note;
import com.unmayshroff.notes.pojo.NoteResponse;

import base.constant.ResponseCodes;
import base.controller.ResponseError;
import base.controller.ResponseListener;
import base.presenter.BasePresenter;

public class NotesPresenter extends BasePresenter {

    private NotesModel model;

    public NotesPresenter() {
        model = NotesModel.getInstance();
    }

    public static NotesPresenter getInstance() {
        return new NotesPresenter();
    }

    public void getNotes() {
        model.getNotes(new ResponseListener<NoteResponse>() {
            @Override
            public void onSuccess(NoteResponse responseBody) {
                if (isViewAttached()) {
                    getView().onApiResponse(ResponseCodes.GET_NODES, responseBody);
                }
            }

            @Override
            public void onError(@NonNull ResponseError error) {
                if (isViewAttached()) {
                    getView().onApiError(ResponseCodes.GET_NODES, error);
                }
            }
        });
    }

    public void updateNote(Note note) {
        /*Note note = new Note();
        note.setTitle(DateTimeUtil.getCurrentLocalTime());
        note.setContent("The quick brown fox jumped over the lazy dog.");*/
        if (!TextUtils.isEmpty(note.getTitle()) || !TextUtils.isEmpty(note.getContent())) {
            model.updateNote(note);
        }
    }

    public void deleteNote(Note note) {
        model.deleteNode(note);
    }

}
