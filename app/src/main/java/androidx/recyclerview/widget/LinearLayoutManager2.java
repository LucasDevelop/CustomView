package androidx.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.core.os.TraceCompat;

public class LinearLayoutManager2 extends LinearLayoutManager {
    public LinearLayoutManager2(Context context) {
        super(context);
    }

    public LinearLayoutManager2(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManager2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        return super.scrollVerticallyBy(dy, recycler, state);
    }


    @Override
    int scrollBy(int delta, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        Log.d("lucas","scrollBy:"+delta);
        return super.scrollBy(delta, recycler, state);
    }

//    @Override
//    int fill(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state, boolean stopOnFocusable) {
//        // max offset we should set is mFastScroll + available
//        final int start = layoutState.mAvailable;
//        if (layoutState.mScrollingOffset != LayoutState.SCROLLING_OFFSET_NaN) {
//            // TODO ugly bug fix. should not happen
//            if (layoutState.mAvailable < 0) {
//                layoutState.mScrollingOffset += layoutState.mAvailable;
//            }
//            recycleByLayoutState(recycler, layoutState);
//        }
//        int remainingSpace = layoutState.mAvailable + layoutState.mExtraFillSpace;
//        LayoutChunkResult layoutChunkResult = mLayoutChunkResult;
//        while ((layoutState.mInfinite || remainingSpace > 0) && layoutState.hasMore(state)) {
//            layoutChunkResult.resetInternal();
//            if (RecyclerView.VERBOSE_TRACING) {
//                TraceCompat.beginSection("LLM LayoutChunk");
//            }
//            layoutChunk(recycler, state, layoutState, layoutChunkResult);
//            if (RecyclerView.VERBOSE_TRACING) {
//                TraceCompat.endSection();
//            }
//            if (layoutChunkResult.mFinished) {
//                break;
//            }
//            layoutState.mOffset += layoutChunkResult.mConsumed * layoutState.mLayoutDirection;
//            /**
//             * Consume the available space if:
//             * * layoutChunk did not request to be ignored
//             * * OR we are laying out scrap children
//             * * OR we are not doing pre-layout
//             */
//            if (!layoutChunkResult.mIgnoreConsumed || layoutState.mScrapList != null
//                    || !state.isPreLayout()) {
//                layoutState.mAvailable -= layoutChunkResult.mConsumed;
//                // we keep a separate remaining space because mAvailable is important for recycling
//                remainingSpace -= layoutChunkResult.mConsumed;
//            }
//
//            if (layoutState.mScrollingOffset != LayoutState.SCROLLING_OFFSET_NaN) {
//                layoutState.mScrollingOffset += layoutChunkResult.mConsumed;
//                if (layoutState.mAvailable < 0) {
//                    layoutState.mScrollingOffset += layoutState.mAvailable;
//                }
//                recycleByLayoutState(recycler, layoutState);
//            }
//            if (stopOnFocusable && layoutChunkResult.mFocusable) {
//                break;
//            }
//        }
//        if (DEBUG) {
//            validateChildOrder();
//        }
//        return start - layoutState.mAvailable;
//    }

    @Override
    void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State state, LayoutState layoutState, LayoutChunkResult result) {
//        Log.d("lucas","layoutChunk");
        super.layoutChunk(recycler, state, layoutState, result);
    }
}
