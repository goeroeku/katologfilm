package ic.aiczone.mymovies;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import ic.aiczone.mymovies.models.FilmModel;

import static ic.aiczone.mymovies.BuildConfig.IMAGE_URL;
import static ic.aiczone.mymovies.entity.DatabaseContract.CONTENT_URI;

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

    private FilmModel film;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        init();
        loadData();
        showData();
    }

    private void init() {
        getSupportActionBar().setTitle(getString(R.string.lb_form_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                delFavorite();
            }
        });
    }

    private void loadData() {
        Uri uri = getIntent().getData();
        if (uri == null) return;
        Cursor cursor = getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) film = new FilmModel(cursor);
            cursor.close();
        }
    }

    private void showData() {
        if (film != null) {
            Picasso.with(this).load(IMAGE_URL + film.getPosterPath()).into(igPoster);
            tvTitle.setText(film.getTitle());
            tvRelease.setText(film.getRelease());
            tvOverview.setText(film.getOverview());
            tvPopularity.setText(film.getPopularity());
            bnFavorite.setBackground(getDrawable(R.drawable.ic_favorite_black));
        }
    }

    private void delFavorite() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + film.getId()),
                null,
                null
        );
        Toast.makeText(this, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
