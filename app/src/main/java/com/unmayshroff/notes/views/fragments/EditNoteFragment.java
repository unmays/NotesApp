package com.unmayshroff.notes.views.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.unmayshroff.notes.R;
import com.unmayshroff.notes.pojo.Note;
import com.unmayshroff.notes.presenters.NotesPresenter;

import base.constant.AppConstants;
import base.listeners.FragmentBackPressedListener;
import base.view.BaseFragment;

public class EditNoteFragment extends BaseFragment implements TextWatcher, View.OnClickListener, FragmentBackPressedListener {

    private NotesPresenter notesPresenter;
    private Note note;
    private EditText etTitle;
    private EditText etNote;

    public static EditNoteFragment getInstance() {
        return new EditNoteFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_edit_note;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notesPresenter = NotesPresenter.getInstance();
        notesPresenter.attachView(this);
        initViews(view);
    }

    private void initViews(View view) {
        etTitle = view.findViewById(R.id.et_title);
        etNote = view.findViewById(R.id.et_note);
        etTitle.addTextChangedListener(this);
        etNote.addTextChangedListener(this);
        etTitle.setText(getNote().getTitle());
        etNote.setText(getNote().getContent());
        view.findViewById(R.id.btn_save).setOnClickListener(this);
    }

    private Note getNote() {
        if (note == null) {
            if (getArguments() != null && getArguments().containsKey(AppConstants.KEY_NOTE)) {
                note = getArguments().getParcelable(AppConstants.KEY_NOTE);
            } else {
                note = new Note();
            }
        }
        return note;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                saveNote();
                break;
        }

    }

    private void saveNote() {
        if (isTitleUpdated() || isNoteUpdated()) {
            getNote().setTitle(etTitle.getText().toString());
            getNote().setContent(etNote.getText().toString());
            notesPresenter.updateNote(getNote());
        }
    }

    private boolean isTitleUpdated() {
        return !etTitle.getText().toString().equals(getNote().getTitle());
    }

    private boolean isNoteUpdated() {
        return !etNote.getText().toString().equals(getNote().getContent());
    }

    @Override
    public boolean onFragmentBackPressed() {
        saveNote();
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notesPresenter.detachView();
    }
}
