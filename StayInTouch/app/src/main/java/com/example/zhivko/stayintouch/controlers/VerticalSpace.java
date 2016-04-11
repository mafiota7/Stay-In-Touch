package com.example.zhivko.stayintouch.controlers;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Panche on 4/8/2016.
 */
public class VerticalSpace extends RecyclerView.ItemDecoration {
    private int space;

    public VerticalSpace(int space) {
        this.space = space;
    }

    /*
    adding space between every element in recyclerview to look like cards
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        if(parent.getChildLayoutPosition(view) == 0)
            outRect.top = space;
    }
}
