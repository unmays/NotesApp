package com.unmayshroff.notes.views.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.unmayshroff.notes.R;
import com.unmayshroff.notes.pojo.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> implements Filterable {

    private List<Note> originalList;
    private List<Note> noteList;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    public NotesAdapter(List<Note> noteList, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
        this.noteList = noteList;
        this.originalList = noteList;
        this.onClickListener = onClickListener;
        this.onLongClickListener = onLongClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, null));
         }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int pos) {
        Note note = noteList.get(pos);
        noteViewHolder.tvTitle.setText(note.getTitle());
        noteViewHolder.tvContent.setText(note.getContent());
        noteViewHolder.itemView.setOnClickListener(onClickListener);
        noteViewHolder.itemView.setOnLongClickListener(onLongClickListener);
        noteViewHolder.itemView.setTag(note);
    }

    @Override
    public int getItemCount() {
        return noteList != null ? noteList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (!TextUtils.isEmpty(constraint)) {
                    List<Note> filteredList = new ArrayList<>();
                    for (Note note : noteList) {
                        if ((!TextUtils.isEmpty(note.getContent()) && note.getContent().contains(constraint))
                                || (!TextUtils.isEmpty(note.getTitle()) && note.getTitle().contains(constraint))) {
                            filteredList.add(note);
                        }
                    }
                    filterResults.values = filteredList;
                } else {
                    filterResults.values = originalList;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                noteList = (List<Note>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvContent;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

}
