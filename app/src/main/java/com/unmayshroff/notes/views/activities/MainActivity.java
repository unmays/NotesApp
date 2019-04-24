package com.unmayshroff.notes.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.unmayshroff.notes.R;
import com.unmayshroff.notes.pojo.Note;
import com.unmayshroff.notes.presenters.NotesPresenter;
import com.unmayshroff.notes.views.fragments.EditNoteFragment;
import com.unmayshroff.notes.views.fragments.NotesListFragment;

import base.constant.AppConstants;
import base.view.BaseActivity;

public class MainActivity extends BaseActivity implements NotesListFragment.NotesListListener {

    private NotesPresenter notesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesPresenter = NotesPresenter.getInstance();
        notesPresenter.attachView(this);
        goToNotesList();
        /*goToEditNote(null);*/
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    public void goToNotesList() {
        addFragment(NotesListFragment.getInstance(), NotesListFragment.class.getSimpleName(), false);
    }

    @Override
    public void onTakeNoteClick() {
        goToEditNote(null);
    }

    @Override
    public void onEditNoteClick(Note note) {
        goToEditNote(note);
    }

    public void goToEditNote(@Nullable Note note) {
        EditNoteFragment editNoteFragment = EditNoteFragment.getInstance();
        Bundle bundle = new Bundle();
        if (note != null) {
            bundle.putParcelable(AppConstants.KEY_NOTE, note);
        }
        editNoteFragment.setArguments(bundle);
        addFragment(editNoteFragment, EditNoteFragment.class.getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notesPresenter.detachView();
    }
}
