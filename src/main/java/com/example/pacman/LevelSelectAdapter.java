package com.example.pacman;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LevelSelectAdapter extends RecyclerView.Adapter<LevelSelectAdapter.ViewHolder> {

    private List<Bitmap> levelBitmaps;
    private List<String> names;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    LevelSelectAdapter(Context context, List<Bitmap> levelBitmaps, List<String> names) {
        this.inflater = LayoutInflater.from(context);
        this.levelBitmaps = levelBitmaps;
        this.names = names;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.level_select_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bitmap bmp = levelBitmaps.get(position);
        String name = names.get(position);
        holder.img.setImageBitmap(bmp);
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.select_level_image);
            name = itemView.findViewById(R.id.select_level_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null){
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public String getItemName(int id) {
        return names.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}