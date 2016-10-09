package xingstarx.com.recyclerviewgridlinearlayoutdemo;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int MIN_ITEM_WIDTH_DP = 280;
    RecyclerView mRecyclerView;
    DataAdapter mDataAdapter;
    GridLayoutManager mGridLayoutManager;
    private int mUserItemWith;
    private int mAppItemWidth;

    public static int dipToPX(@NonNull final Context ctx, float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, ctx.getResources().getDisplayMetrics());
    }

    public static final Point getScreenResolutionSize(Context ctx) {
        Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(android.R.id.list);
        mGridLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4, dipToPX(this, 8), false, 0));
//        mRecyclerView.addItemDecoration(new SpacingDecoration(dipToPX(this, 8), dipToPX(this, 8), false));
        mDataAdapter = new DataAdapter(generatorData());
        calculateItemWidth();
        mRecyclerView.setAdapter(mDataAdapter);

    }

    private void calculateItemWidth() {
        calculateItemWidth(getScreenResolutionSize(this).x);
    }

    private void calculateItemWidth(int width) {
        int numAppColums = 4;
        int appWidth = width - dipToPX(this, 24);
        mAppItemWidth = appWidth / numAppColums;
        if (mRecyclerView != null) {
            mGridLayoutManager.setSpanCount(numAppColums);
        }
    }

    private List<String> generatorData() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("And " + i);
        }
        return dataList;
    }

    class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

        private List<String> mDataList;

        public DataAdapter(List<String> mDataList) {
            this.mDataList = mDataList;
        }

        @Override
        public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_list_item, parent, false);
            view.setLayoutParams(new ViewGroup.LayoutParams(mAppItemWidth, mAppItemWidth + dipToPX(parent.getContext(), 12) + dipToPX(parent.getContext(), 2)));
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
            holder.textView.setText(mDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }
}
