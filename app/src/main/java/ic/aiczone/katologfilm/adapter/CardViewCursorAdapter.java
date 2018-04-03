package ic.aiczone.katologfilm.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ic.aiczone.katologfilm.DetailActivity;
import ic.aiczone.katologfilm.R;
import ic.aiczone.katologfilm.models.FilmModel;

import static ic.aiczone.katologfilm.BuildConfig.IMAGE_URL;
import static ic.aiczone.katologfilm.provider.DatabaseContract.CONTENT_URI;

/**
 * Created by aic on 23/03/18.
 */

public class CardViewCursorAdapter extends RecyclerView.Adapter<CardViewCursorAdapter.CardViewViewHolder> {
    private Cursor mFilms;
    private Context context;

    private String ARG_PARCEL_LIST = "bundle_films";

    public CardViewCursorAdapter(Context context) {
        this.context = context;
    }

    private FilmModel getItem(int position) {
        if (!mFilms.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new FilmModel(mFilms);
    }


    public void setListFilms(Cursor items) {
        mFilms = items;
        if (mFilms != null) notifyDataSetChanged();
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_film, parent, false);
        return new CardViewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CardViewViewHolder holder, int position) {

        final FilmModel film = getItem(position);

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
                Intent v = new Intent(context, DetailActivity.class);
                /*Gson gson = new Gson();
                v.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                v.putExtra("film", gson.toJson(getListFilms().get(position)));*/
                v.putExtra(ARG_PARCEL_LIST, film);
                /*v.setData(Uri.parse(CONTENT_URI + "/" + film.getId()));*/
                holder.btnDetail.getContext().startActivity(v);
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share " + film.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        if (mFilms == null) return 0;
        else return mFilms.getCount();
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
