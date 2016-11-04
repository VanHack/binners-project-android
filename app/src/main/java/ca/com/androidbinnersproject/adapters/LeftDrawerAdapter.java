package ca.com.androidbinnersproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ca.com.androidbinnersproject.R;
import ca.com.androidbinnersproject.models.NavDrawerItem;

/**
 * Created by jonathan_campos on 12/03/2016.
 */
public class LeftDrawerAdapter extends RecyclerView.Adapter<LeftDrawerAdapter.NaviViewHolder> {

    private List<NavDrawerItem> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private Context mContext;

    public LeftDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.mContext = context;
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public NaviViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.drawer_row, parent, false);
        NaviViewHolder holder = new NaviViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(NaviViewHolder holder, int position) {
        NavDrawerItem item = mData.get(position);
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class NaviViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        public NaviViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.left_navigation_drawer_row_title);
        }
    }
}
