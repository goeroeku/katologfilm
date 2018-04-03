package ic.aiczone.katologfilm;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ic.aiczone.katologfilm.database.FavoriteHelper;
import ic.aiczone.katologfilm.models.FilmModel;

import static android.provider.BaseColumns._ID;
import static ic.aiczone.katologfilm.BuildConfig.IMAGE_URL;
import static ic.aiczone.katologfilm.provider.DatabaseContract.CONTENT_URI;
import static ic.aiczone.katologfilm.provider.DatabaseContract.FavoriteColumns.OVERVIEW;
import static ic.aiczone.katologfilm.provider.DatabaseContract.FavoriteColumns.POPULARITY;
import static ic.aiczone.katologfilm.provider.DatabaseContract.FavoriteColumns.POSTER_PATH;
import static ic.aiczone.katologfilm.provider.DatabaseContract.FavoriteColumns.RELEASE;
import static ic.aiczone.katologfilm.provider.DatabaseContract.FavoriteColumns.TITLE;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRelease)
    TextView tvRelease;
    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @BindView(R.id.tvPopularity)
    TextView tvPopularity;
    @BindView(R.id.igPoster)
    ImageView igPoster;
    @BindView(R.id.bnFavorite)
    Button bnFavorite;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    private String ARG_PARCEL_LIST = "bundle_films";

    private FilmModel film;
    private FavoriteHelper favoriteHelper;
    private Boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        init();
        initData();
    }

    private void init() {
        getSupportActionBar().setTitle(getString(R.string.lb_form_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initData() {
        Bundle data = getIntent().getExtras();
        if (data != null) {
            film = data.getParcelable(ARG_PARCEL_LIST);
            loadDataSQLite();
            if (film != null) {
                Picasso.with(this).load(IMAGE_URL + film.getPosterPath()).into(igPoster);
                tvTitle.setText(film.getTitle());
                tvRelease.setText(film.getRelease());
                tvOverview.setText(film.getOverview());
                tvPopularity.setText(film.getPopularity());
            }
        }

        bnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                if (isFavorite) delFavorite();
                else addFavorite();

                isFavorite = !isFavorite;
                setFavorite();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDataSQLite() {
        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + film.getId()),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        setFavorite();
    }

    private void setFavorite() {
        if (isFavorite) bnFavorite.setBackground(getDrawable(R.drawable.ic_favorite_black));
        else bnFavorite.setBackground(getDrawable(R.drawable.ic_favorite_border_black));
    }

    private void addFavorite() {
        ContentValues cv = new ContentValues();
        cv.put(_ID, film.getId());
        cv.put(POSTER_PATH, film.getPosterPath());
        cv.put(TITLE, film.getTitle());
        cv.put(RELEASE, film.getRelease());
        cv.put(OVERVIEW, film.getOverview());
        cv.put(POPULARITY, film.getPopularity());

        getContentResolver().insert(CONTENT_URI, cv);
        Toast.makeText(this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
    }

    private void delFavorite() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + film.getId()),
                null,
                null
        );
        Toast.makeText(this, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
    }
}
