package ic.aiczone.katologfilm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import ic.aiczone.katologfilm.models.Films;
import ic.aiczone.katologfilm.utils.Tools;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("film");
        Films film = gson.fromJson(strObj, Films.class);

        ImageView ivCover = findViewById(R.id.ivCover);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185" + film.getLinkImage()).into(ivCover);

        TextView tvCode = findViewById(R.id.tvCode);
        tvCode.setText("[ Kode : " + Integer.toString(film.getId()) + " ]");

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Judul: " + film.getTitle());

        TextView tvDescription = findViewById(R.id.tvDescription);
        tvDescription.setText(film.getDescription());

        TextView tvTime = findViewById(R.id.tvTime);
        String waktu = Tools.getLongFormat(film.getTime());
        tvTime.setText("Waktu Release: " + waktu);
    }

    private void init() {
        getSupportActionBar().setTitle("Detail Film");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
