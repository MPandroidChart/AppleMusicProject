package com.example.applemusicdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.applemusicdemo.R;
import com.example.applemusicdemo.activities.AlbumListActivity;
import com.example.applemusicdemo.models.AlbumModel;
import com.example.applemusicdemo.models.MusicResourceModel;

import java.util.List;

public class MusicGridAdapter extends RecyclerView.Adapter<MusicGridAdapter.Viewholder> {
    private Context mContext;
    private List<AlbumModel>malbumlist;
    public MusicGridAdapter(Context context,List<AlbumModel>albumlist) {
        mContext=context;
        this.malbumlist=albumlist;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new Viewholder(LayoutInflater.from(mContext).inflate(R.layout.item_grid_music,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewHolder, int i) {
        final AlbumModel model=malbumlist.get(i);
        Glide.with(mContext).load(model.getPoster()).into(viewHolder.rv_grid_img);
        viewHolder.music_play_num.setText(model.getPlayNum());
        viewHolder.music_play_name.setText(model.getName());
        viewHolder.ItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext, AlbumListActivity.class);
                i.putExtra(AlbumListActivity.ALBUM_ID,model.getAlbumId());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {

        return malbumlist.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        ImageView rv_grid_img;
        View ItemView;
        TextView music_play_num,music_play_name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            rv_grid_img=itemView.findViewById(R.id.rv_grid_img);
            music_play_name=itemView.findViewById(R.id.music_play_name);
            music_play_num=itemView.findViewById(R.id.music_play_num);
            this.ItemView=itemView;

        }
    }
}
