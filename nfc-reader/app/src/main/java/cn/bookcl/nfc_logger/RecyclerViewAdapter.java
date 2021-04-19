package cn.bookcl.nfc_logger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bookcl.nfc_logger.tagdb.TagDatabaseStruct;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    //private ArrayList<String> data;
    public List<TagDatabaseStruct> mTagDatabaseStructList; // = new ArrayList<TagDatabaseStruct>();

    public RecyclerViewAdapter(final Context context, List<TagDatabaseStruct> data) {
        this.context = context;
        this.mTagDatabaseStructList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.tag_line_info, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
       // viewHolder.tv_id.setText(mTagDatabaseStructList.get(position).getVar_id() + "");
        viewHolder.tv_pyload.setText((CharSequence) mTagDatabaseStructList.get(position).getVar_payload());
        viewHolder.tv_date.setText((CharSequence) mTagDatabaseStructList.get(position).getVar_date().toString());
        viewHolder.tv_count.setText(mTagDatabaseStructList.get(position).getVar_count() + "");
    }

    @Override
    public int getItemCount() {
        return mTagDatabaseStructList.size();
    }

    public void addData(int pos, TagDatabaseStruct val) {
        mTagDatabaseStructList.add(val);
        notifyItemInserted(pos);
    }


    public void delData(int pos) {
        mTagDatabaseStructList.remove(pos);
        notifyItemRemoved(pos);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_id;
        private TextView tv_pyload;
        private TextView tv_date;
        private TextView tv_count;

        ViewHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tag_text_id);
            tv_pyload = itemView.findViewById(R.id.tag_text_payload);
            tv_date = itemView.findViewById(R.id.tag_text_date);
            tv_count = itemView.findViewById(R.id.tag_text_count);
        }
    }
}