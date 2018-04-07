package ic.aiczone.mymovies.adapter;

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

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ic.aiczone.mymovies.DetailActivity;
import ic.aiczone.mymovies.R;
import ic.aiczone.mymovies.models.FilmModel;

import static ic.aiczone.mymovies.BuildConfig.IMAGE_URL;
import static ic.aiczone.mymovies.entity.DatabaseContract.CONTENT_URI;

/**
 * Created by aic on 23/03/18.
 */

public class CardViewFilmsAdapter extends RecyclerView.Adapter<CardViewFilmsAdapter.CardViewViewHolder> {
    private Cursor mFilms;
    private Context context;

    public CardViewFilmsAdapter(Context context) {
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
                v.setData(Uri.parse(CONTENT_URI + "/" + film.getId()));
                holder.btnDetail.getContext().startActivity(v);
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
