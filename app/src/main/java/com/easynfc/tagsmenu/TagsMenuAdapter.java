package com.easynfc.tagsmenu;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easynfc.MainActivity;
import com.easynfc.R;
import com.easynfc.data.model.TagMenu;
import com.easynfc.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.easynfc.util.AppConstants.TagTypes.AAR;
import static com.easynfc.util.AppConstants.TagTypes.EMAIL;
import static com.easynfc.util.AppConstants.TagTypes.LOCATION;
import static com.easynfc.util.AppConstants.TagTypes.LOCK;
import static com.easynfc.util.AppConstants.TagTypes.NDEF;
import static com.easynfc.util.AppConstants.TagTypes.PHONE;
import static com.easynfc.util.AppConstants.TagTypes.SMS;
import static com.easynfc.util.AppConstants.TagTypes.TEXT;
import static com.easynfc.util.AppConstants.TagTypes.URL;
import static com.easynfc.util.AppConstants.TagTypes.WIFI;

/**
 * Created by pablorojas on 21/2/18.
 */


public class TagsMenuAdapter extends RecyclerView.Adapter<TagsMenuAdapter.MyViewHolder> {

    private Activity context;
    private List<TagMenu> lst_tags_menu;
    private MainActivity main;

    public TagsMenuAdapter(Activity context, List<TagMenu> lst_tags_menu) {
        this.context = context;
        this.lst_tags_menu = lst_tags_menu;
        this.main = (MainActivity) context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_menu_item, parent, false);
        return new TagsMenuAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtType.setText(lst_tags_menu.get(position).getType());
        String uri = "drawable/" + lst_tags_menu.get(position).getImg();
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        holder.imgType.setImageResource(imageResource);
    }


    @Override
    public int getItemCount() {
        return lst_tags_menu.size();
    }

    public void update(List<TagMenu> tagsMenu) {
        this.lst_tags_menu = tagsMenu;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rl_tag_menu_item)
        RelativeLayout rlTagMenuItem;
        @BindView(R.id.txt_type)
        TextView txtType;
        @BindView(R.id.img_type)
        ImageView imgType;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) rlTagMenuItem.getLayoutParams();
            params.width = AppUtils.getDisplayMetrics(context).widthPixels/ 2;
            params.height = params.width;
            rlTagMenuItem.setLayoutParams(params);
        }

        @Override
        public void onClick(View view) {
            if (getLayoutPosition() == TEXT.ordinal()){
                main.navigateToWriter(TEXT, 0);
            }else if (getLayoutPosition() == URL.ordinal()){
                main.navigateToWriter(URL, 0);
            }else if (getLayoutPosition() == SMS.ordinal()){
                main.navigateToWriter(SMS, 0);
            }else if (getLayoutPosition() == PHONE.ordinal()){
                main.navigateToWriter(PHONE, 0);
            }else if (getLayoutPosition() == AAR.ordinal()){
                main.navigateToWriter(AAR, 0);
            }else if (getLayoutPosition() == LOCATION.ordinal()){
                main.navigateToWriter(LOCATION, 0);
            }else if (getLayoutPosition() == WIFI.ordinal()){
                main.navigateToWriter(WIFI, 0);
            }else if (getLayoutPosition() == EMAIL.ordinal()){
                main.navigateToWriter(EMAIL, 0);
            }else if (getLayoutPosition() == NDEF.ordinal()){
                main.navigateToWriter(NDEF, 0);
            }else if (getLayoutPosition() == LOCK.ordinal()){
                main.navigateToWriter(LOCK, 0);
            }
        }

    }


}