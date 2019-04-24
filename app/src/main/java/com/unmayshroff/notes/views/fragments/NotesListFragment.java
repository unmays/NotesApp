package com.unmayshroff.notes.views.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.unmayshroff.notes.R;
import com.unmayshroff.notes.pojo.Note;
import com.unmayshroff.notes.pojo.NoteResponse;
import com.unmayshroff.notes.presenters.NotesPresenter;
import com.unmayshroff.notes.views.adapters.MarginItemDecorator;
import com.unmayshroff.notes.views.adapters.NotesAdapter;

import base.constant.ResponseCodes;
import base.controller.ResponseError;
import base.view.BaseFragment;

public class NotesListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, TextWatcher, View.OnLongClickListener {

    private NotesListListener notesListListener;
    private NotesPresenter notesPresenter;
    private EditText etSearch;
    private RecyclerView rvNotes;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NotesAdapter adapter;

    public static Fragment getInstance() {
        return new NotesListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NotesListListener) {
            notesListListener = (NotesListListener) context;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notes_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notesPresenter = NotesPresenter.getInstance();
        notesPresenter.attachView(this);
        initViews(view);
        onRefresh();
    }

    private void initViews(View view) {
        rvNotes = view.findViewById(R.id.rv_notes);
        etSearch = view.findViewById(R.id.et_search);
        etSearch.addTextChangedListener(this);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        view.findViewById(R.id.tv_take_note).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_take_note:
                if (notesListListener != null) {
                    notesListListener.onTakeNoteClick();
                }
                break;
            case R.id.layout_item_note:
                Note note = (Note) v.getTag();
                notesListListener.onEditNoteClick(note);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.layout_item_note:
                Note note = (Note) v.getTag();
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.delete_note_message)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notesPresenter.deleteNote(note);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(true)
                        .show();
                break;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (adapter != null) {
            adapter.getFilter().filter(s);
        }
    }

    @Override
    public void onRefresh() {
        notesPresenter.getNotes();
    }

    @Override
    public void onApiResponse(int reqCode, Object response) {
        switch (reqCode) {
            case ResponseCodes.GET_NODES:
                NoteResponse noteResponse = (NoteResponse) response;
                initRecyclerView(noteResponse);
                swipeRefreshLayout.setRefreshing(false);
                break;
        }
    }

    @Override
    public void onApiError(int reqCode, ResponseError error) {
        switch (reqCode) {
            case ResponseCodes.GET_NODES:
                swipeRefreshLayout.setRefreshing(false);
                break;
        }
    }

    private void initRecyclerView(NoteResponse noteResponse) {
        rvNotes.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvNotes.addItemDecoration(new MarginItemDecorator(8));
        adapter = new NotesAdapter(noteResponse.getNotes(), this, this);
        rvNotes.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notesPresenter.detachView();
    }

    public interface NotesListListener {
        void onTakeNoteClick();

        void onEditNoteClick(Note note);
    }

}
