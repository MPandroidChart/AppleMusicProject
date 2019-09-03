package com.example.applemusicdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.applemusicdemo.R;
import com.example.applemusicdemo.activities.AlbumListActivity;
import com.example.applemusicdemo.activities.PlayActivity;
import com.example.applemusicdemo.helps.RealmHelper;
import com.example.applemusicdemo.models.AlbumModel;
import com.example.applemusicdemo.models.MusicModel;
import com.example.applemusicdemo.utils.DataUtils;
import com.example.applemusicdemo.views.PlayMusicView;

import java.util.List;

import io.realm.Realm;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.Viewholder> {
    private Context mContext;
    private View mItemView;
    private RecyclerView rv;
    private boolean isCaculationRecyclerViewHight;
    private List<MusicModel>datalist;
    public MusicListAdapter(Context context,RecyclerView mRecyclerView,List<MusicModel>mdatalist) {

        mContext=context;
        rv=mRecyclerView;
        this.datalist=mdatalist;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mItemView=LayoutInflater.from(mContext).inflate(R.layout.rv_list_item,viewGroup,false);
        return new Viewholder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, int i) {
                setRecyclerViewHight();
        final MusicModel model=datalist.get(i);
        Glide.with(mContext).load(model.getPoster()).into(viewholder.rv_list_img);
        viewholder.music_name.setText(model.getName());
        viewholder.music_author.setText(model.getAuthor());


        viewholder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(mContext, PlayActivity.class);
                i.putExtra(PlayActivity.MUSIC_ID,model.getMusicId());
                mContext.startActivity(i);
            }
        });
//        if(realm!=null||!realm.isClosed()){
//            realm.close();
//        }
    }


    @Override
    public int getItemCount() {

        return datalist.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        ImageView rv_list_img;
        TextView music_name;
        TextView music_author;
        View mView;

        public Viewholder(@NonNull View itemView) {

            super(itemView);
            rv_list_img=itemView.findViewById(R.id.rv_list_img);
            music_author=itemView.findViewById(R.id.music_author);
            music_name=itemView.findViewById(R.id.music_name);
            this.mView=itemView;
        }

    }

    /**
     * ViewHight=ItemViewHight*ItemCount;
     */
    public void setRecyclerViewHight(){
        if(isCaculationRecyclerViewHight||rv==null)return;
        isCaculationRecyclerViewHight=true;
        RecyclerView.LayoutParams ItemLp= (RecyclerView.LayoutParams) mItemView.getLayoutParams();
        int ItemViewHight=ItemLp.height;
        int ItemCount=getItemCount();
        int recyclerViewHight=ItemViewHight*ItemCount;
        //设置RecyclerView的高度；
        LinearLayout.LayoutParams rvlp= (LinearLayout.LayoutParams) rv.getLayoutParams();
        rvlp.height=recyclerViewHight;
        rv.setLayoutParams(rvlp);
    }
}
