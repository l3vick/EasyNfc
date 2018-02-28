package com.easynfc.menu;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easynfc.MainActivity;
import com.easynfc.R;
import com.easynfc.data.Menu;
import com.easynfc.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pablorojas on 19/2/18.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private Activity context;
    private List<Menu> lst_menu;
    private static final int WRITE = 0;
    private static final int READ = 1;
    private static final int MYTAGS = 2;
    private MainActivity main;

    public MenuAdapter(Activity context, List<Menu> lst_menu) {
        this.context = context;
        this.lst_menu = lst_menu;
        this.main = (MainActivity) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new MenuAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txt_title.setText(lst_menu.get(position).getTitle());
        holder.txt_subtitle.setText(lst_menu.get(position).getSubtitle());
    }

    @Override
    public int getItemCount() {
        return lst_menu.size();
    }

    public void update(List<Menu> menus) {
        this.lst_menu = menus;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ll_menu_item)
        LinearLayout ll_menu_item;
        @BindView(R.id.txt_title)
        TextView txt_title;
        @BindView(R.id.txt_subtitle)
        TextView txt_subtitle;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
            txt_title.setTypeface(AppUtils.getAppTypeface(context));
            txt_subtitle.setTypeface(AppUtils.getAppTypeface(context));
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) ll_menu_item.getLayoutParams();
            params.height = AppUtils.getDisplayMetrics(context).heightPixels/ 3;
            ll_menu_item.setLayoutParams(params);
        }

        @Override
        public void onClick(View view) {
            if (getLayoutPosition() == WRITE){
                main.navigateToTagsMenu();
            }else if (getLayoutPosition() == READ){
                main.navigateToTagReader();
            }else if (getLayoutPosition() == MYTAGS){
                main.navigateToMyTags();
            }
        }

    }


}