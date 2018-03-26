package ic.aiczone.katologfilm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ic.aiczone.katologfilm.DetailActivity;
import ic.aiczone.katologfilm.R;
import ic.aiczone.katologfilm.models.Films;

/**
 * Created by aic on 23/03/18.
 */

public class CardViewFilmsAdapter extends RecyclerView.Adapter<CardViewFilmsAdapter.CardViewViewHolder> {
    private ArrayList<Films> lsFilms;
    private Context context;

    public CardViewFilmsAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<Films> getListFilms() {
        return lsFilms;
    }

    public void setListFilms(ArrayList<Films> listFilms) {
        this.lsFilms = listFilms;
        notifyDataSetChanged();
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_film, parent, false);
        return new CardViewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CardViewViewHolder holder, int position) {

        Films p = getListFilms().get(position);

        /* load image menggunakan glide */
        /*Glide.with(context)
                .load(p.getLinkImage())
                .override(350, 550)
                .into(holder.imgPhoto);*/
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185" + p.getLinkImage()).into(holder.imgPhoto);

        holder.tvName.setText(p.getTitle());
        String desc = p.getDescription();
        if (desc.length() > 100) {
            desc = desc.substring(0, 100) + "...";
        } else if (desc.length() < 1) {
            desc = "";
        }
        holder.tvRemarks.setText(desc);

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                //Toast.makeText(context, "Favorite " + getListFilms().get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent v = new Intent(context, DetailActivity.class);
                Gson gson = new Gson();
                v.putExtra("film", gson.toJson(getListFilms().get(position)));
                context.startActivity(v);
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share " + getListFilms().get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        if(getListFilms() == null ) return 0;
        Log.d("ItemCount", String.valueOf(getListFilms().size()));
        return getListFilms().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvRemarks;
        Button btnDetail, btnShare;

        CardViewViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvRemarks = itemView.findViewById(R.id.tv_item_remarks);
            btnDetail = itemView.findViewById(R.id.btn_set_detail);
            btnShare = itemView.findViewById(R.id.btn_set_share);
        }
    }
}
