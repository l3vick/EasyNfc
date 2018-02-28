package com.easynfc.tagsmenu;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easynfc.MainActivity;
import com.easynfc.R;
import com.easynfc.data.TagMenu;
import com.easynfc.util.AppConstants;
import com.easynfc.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
            txtType.setTypeface(AppUtils.getAppTypeface(context));
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) rlTagMenuItem.getLayoutParams();
            params.width = AppUtils.getDisplayMetrics(context).widthPixels/ 2;
            params.height = params.width;
            rlTagMenuItem.setLayoutParams(params);
        }

        @Override
        public void onClick(View view) {
            if (getLayoutPosition() == AppConstants.SIMPLE_TEXT_TAG){
                main.navigateToWriter(AppConstants.SIMPLE_TEXT_TAG);
            }else if (getLayoutPosition() == AppConstants.URL_TAG){
                main.navigateToWriter(AppConstants.URL_TAG);
            }else if (getLayoutPosition() == AppConstants.SMS_TAG){
                main.navigateToWriter(AppConstants.SMS_TAG);
            }else if (getLayoutPosition() == AppConstants.PHONE_TAG){
                main.navigateToWriter(AppConstants.PHONE_TAG);
            }else if (getLayoutPosition() == AppConstants.APP_LAUNCHER_TAG){
                main.navigateToWriter(AppConstants.APP_LAUNCHER_TAG);
            }else if (getLayoutPosition() == AppConstants.LOCATION_TAG){
                main.navigateToWriter(AppConstants.LOCATION_TAG);
            }else if (getLayoutPosition() == AppConstants.WIFI_TAG){
                main.navigateToWriter(AppConstants.WIFI_TAG);
            }else if (getLayoutPosition() == AppConstants.EMAIL_TAG){
                main.navigateToWriter(AppConstants.EMAIL_TAG);
            }else if (getLayoutPosition() == AppConstants.NDEF_FORMAT_TAG){
                main.navigateToWriter(AppConstants.NDEF_FORMAT_TAG);
            }else if (getLayoutPosition() == AppConstants.LOCK_TAG){
                main.navigateToWriter(AppConstants.LOCK_TAG);
            }
        }

    }


}