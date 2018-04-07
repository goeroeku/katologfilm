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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ic.aiczone.katologfilm.DetailActivity;
import ic.aiczone.katologfilm.R;
import ic.aiczone.katologfilm.models.FilmModel;

import static ic.aiczone.katologfilm.BuildConfig.IMAGE_URL;

/**
 * Created by aic on 23/03/18.
 */

public class CardViewFilmsAdapter extends RecyclerView.Adapter<CardViewFilmsAdapter.CardViewViewHolder> {
    private ArrayList<FilmModel> lsFilms;
    private Context context;

    private String ARG_PARCEL_LIST = "bundle_films";

    public CardViewFilmsAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<FilmModel> getListFilms() {
        return lsFilms;
    }

    public void setListFilms(ArrayList<FilmModel> listFilms) {
        this.lsFilms = listFilms;
        notifyDataSetChanged();
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_film, parent, false);
        return new CardViewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CardViewViewHolder holder, int position) {

        final FilmModel film = getListFilms().get(position);

        /* load image menggunakan glide */
        /*Glide.with(context)
                .load(p.getLinkImage())
                .override(350, 550)
                .into(holder.imgPhoto);*/
        Picasso.with(context).load(IMAGE_URL + film.getPosterPath()).into(holder.imgPhoto);

        holder.tvName.setText(film.getTitle());
        String desc = film.getOverview();
        if (desc.length() > 100) {
            desc = desc.substring(0, 100) + "...";
        } else if (desc.length() < 1) {
            desc = "";
        }
        holder.tvOverview.setText(desc);

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                //Toast.makeText(context, "Favorite " + getListFilms().get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent v = new Intent(context, DetailActivity.class);
                /*Gson gson = new Gson();
                v.putExtra("film", gson.toJson(getListFilms().get(position)));*/
                v.putExtra(ARG_PARCEL_LIST, film);
                context.startActivity(v);
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                String mPesan = holder.btnShare.getContext().getResources().getString(R.string.app_name);
                mPesan += "\n" + film.getTitle();
                Intent v = new Intent();
                v.setType("text/plain");
                v.setAction(Intent.ACTION_SEND);
                v.putExtra(Intent.EXTRA_TEXT, mPesan);
                holder.btnShare.getContext().startActivity(Intent.createChooser(v, mPesan));
                holder.btnShare.getContext().getResources().getString(R.string.lb_button_send_to);
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (getListFilms() == null) return 0;
        Log.d("ItemCount", String.valueOf(getListFilms().size()));
        return getListFilms().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item_photo)
        ImageView imgPhoto;
        @BindView(R.id.tv_item_name)
        TextView tvName;
        @BindView(R.id.tv_overview)
        TextView tvOverview;
        @BindView(R.id.btn_set_detail)
        Button btnDetail;
        @BindView(R.id.btn_set_share)
        Button btnShare;

        CardViewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
