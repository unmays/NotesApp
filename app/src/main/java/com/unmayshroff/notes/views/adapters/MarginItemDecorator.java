package com.unmayshroff.notes.views.adapters;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MarginItemDecorator extends RecyclerView.ItemDecoration {

    private int margin;

    public MarginItemDecorator(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(margin, margin, margin, margin);
    }
}
