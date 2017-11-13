package cs2340.gatech.edu.m4.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import cs2340.gatech.edu.m4.activity.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cs2340.gatech.edu.m4.R;

import java.util.List;

/**
 * Created by bravado on 10/24/17.
 */

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {
    private List<DataItem> list;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View contextView;
        TextView DataId;
        TextView DataContent;

        public ViewHolder(View view){
            super(view);
            contextView = view;
            DataId = view.findViewById(R.id.datalist_id);
            DataContent = view.findViewById(R.id.datalist_content);
        }
    }

    public DataItemAdapter(List<DataItem> list){
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_data_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DataItem item = list.get(position);
        holder.DataId.setText(String.valueOf(item.getId()));
        holder.DataContent.setText(String.valueOf(item.getCreatedDate()));
        holder.contextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DataDetailActivity.class);
                intent.putExtra(DataDetailActivity.ARG_ITEM_ID, item.getId());
                intent.putExtra(DataDetailActivity.ACTIVITY, "MainActivity");
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
