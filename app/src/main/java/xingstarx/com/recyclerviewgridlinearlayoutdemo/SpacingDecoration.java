package xingstarx.com.recyclerviewgridlinearlayoutdemo;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by xiongxingxing on 16/10/8.
 */

public class SpacingDecoration extends RecyclerView.ItemDecoration {
    public static final String TAG = "SpacingDecoration";

    private int mHorizontalSpacing = 5;
    private int mVerticalSpacing = 5;
    private boolean isSetMargin = true;

    public SpacingDecoration(int hSpacing, int vSpacing, boolean setMargin) {
        isSetMargin = setMargin;
        mHorizontalSpacing = hSpacing;
        mVerticalSpacing = vSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        boolean isSetMarginLeftAndRight = this.isSetMargin;
        int bottomOffset = mVerticalSpacing;
        int leftOffset = 0;
        int rightOffset = 0;

        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) parent.getLayoutManager();
            GridLayoutManager.LayoutParams gridLp = (GridLayoutManager.LayoutParams) lp;

            Log.e(TAG, "gridLp.getSpanSize() == " + gridLp.getSpanSize() + ", lm.getSpanCount() == " + lm.getSpanCount() +
            "gridLp.getSpanIndex() == " + gridLp.getSpanIndex());
            if (gridLp.getSpanSize() == lm.getSpanCount()) {
                if (isSetMarginLeftAndRight) {
                    leftOffset = mHorizontalSpacing;
                    rightOffset = mHorizontalSpacing;
                }
            } else {
                if (gridLp.getSpanIndex() > 0) {
                    leftOffset = mHorizontalSpacing;
                } else if (gridLp.getSpanIndex() == 0 && isSetMarginLeftAndRight) {
                    leftOffset = mHorizontalSpacing;
                }
                if (gridLp.getSpanIndex() == lm.getSpanCount() - gridLp.getSpanSize() && isSetMarginLeftAndRight) {
                    rightOffset = mHorizontalSpacing;
                }
            }
        }
//        outRect.set(leftOffset, 0, rightOffset, bottomOffset);
//        outRect.set(mHorizontalSpacing, mVerticalSpacing, 0, 0);


        GridLayoutManager.LayoutParams layoutParams
                = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        int position = layoutParams.getViewAdapterPosition();
        if (position == RecyclerView.NO_POSITION) {
            outRect.set(0, 0, 0, 0);
            return;
        }

        // add edge margin only if item edge is not the grid edge
        int itemSpanIndex = layoutParams.getSpanIndex();
        // is left grid edge?
        outRect.left = itemSpanIndex == 0 ? 0 : mHorizontalSpacing;
        // is top grid edge?
        outRect.top = itemSpanIndex == position ? 0 : mVerticalSpacing;
        outRect.right = 0;
        outRect.bottom = 0;

    }
}