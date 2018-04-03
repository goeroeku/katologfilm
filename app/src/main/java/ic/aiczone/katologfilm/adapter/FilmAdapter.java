package ic.aiczone.katologfilm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ic.aiczone.katologfilm.R;
import ic.aiczone.katologfilm.models.FilmModel;

import static ic.aiczone.katologfilm.BuildConfig.IMAGE_URL;

/**
 * Created by Emeth on 10/31/2016.
 */

public class FilmAdapter extends BaseAdapter {

    private ArrayList<FilmModel> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    private OnItemClickListener mOnItemClickListener;

    public FilmAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, FilmModel obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setData(ArrayList<FilmModel> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(final FilmModel item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;

        return mData.size();
    }

    @Override
    public FilmModel getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.film_items, null);
            holder.tvTitle = convertView.findViewById(R.id.tvTitle);
            holder.tvDescription = convertView.findViewById(R.id.tvDescription);
            holder.tvTime = convertView.findViewById(R.id.tvTime);
            holder.ivCover = convertView.findViewById(R.id.ivCover);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.ivCover.setImageResource(R.drawable.no_img);
        String desc = mData.get(position).getOverview();
        if (desc.length() < 1) {
            holder.tvDescription.setVisibility(View.GONE);
        } else {
            holder.tvDescription.setVisibility(View.VISIBLE);
        }

        if (desc.length() > 100) {
            desc = desc.substring(0, 100) + "...";
        } else if (desc.length() < 1) {
            desc = "";
        }

        holder.tvDescription.setText(desc);
        holder.tvTime.setText(mData.get(position).getRelease());
        Picasso.with(context).load(IMAGE_URL + mData.get(position).getPosterPath()).into(holder.ivCover);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, mData.get(position), position);
                }
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvTime;
        ImageView ivCover;
    }

}





