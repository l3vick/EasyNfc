package com.easynfc.mytags;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easynfc.R;
import com.easynfc.data.MyTag;
import com.easynfc.util.AppUtils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pablorojas on 14/3/18.
 */

public class MyTagsAdapter extends RecyclerView.Adapter<MyTagsAdapter.MyViewHolder> {

    private Activity context;
    private List<MyTag> lstMyTags;
    private MyTagsContract.OnMyTagClickListener onMyTagClickListener;


    public MyTagsAdapter(Activity context, List<MyTag> lstMyTags, MyTagsContract.OnMyTagClickListener onMyTagClickListener) {
        this.context = context;
        this.lstMyTags = lstMyTags;
        this.onMyTagClickListener = onMyTagClickListener;
    }

    @Override
    public MyTagsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_tag_item, parent, false);
        return new MyTagsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyTagsAdapter.MyViewHolder holder, int position) {
        holder.myTag = lstMyTags.get(position);
        holder.txtTitle.setText(lstMyTags.get(position).getContent());
        long timestamp = lstMyTags.get(position).getTimestamp();
        Date date = new Date(timestamp);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        holder.txtDate.setText(formatter.format(date));
        holder.imgType.setImageResource(0);
        holder.imgType.setImageResource(AppUtils.getResourceByType(lstMyTags.get(position).getType()));
    }

    @Override
    public int getItemCount() {
        return lstMyTags.size();
    }

    public void updateAdapter(List<MyTag> tags) {
        this.lstMyTags = tags;
        notifyDataSetChanged();
    }

    public void updateRemove(List<MyTag> lstMyTags, int position) {
        this.lstMyTags = lstMyTags;
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.btn_delete)
        ImageButton btnDelete;
        @BindView(R.id.img_type)
        ImageView imgType;
        MyTag myTag;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_delete) {
                onMyTagClickListener.OnDelete(myTag);
            }else{
                onMyTagClickListener.OnItemSelected(myTag);
            }
        }

    }


}