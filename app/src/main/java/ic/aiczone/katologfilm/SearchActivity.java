package ic.aiczone.katologfilm;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import ic.aiczone.katologfilm.adapter.FilmAdapter;
import ic.aiczone.katologfilm.models.Films;
import ic.aiczone.katologfilm.utils.AsyncTaskUtils;

/**
 * Created by aic on 26/02/18.
 */

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Films>> {

    ListView listView;
    FilmAdapter adapter;
    EditText edCari;
    Button bnCari;

    static final String EXTRAS_FILM = "EXTRAS_FILM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

        adapter = new FilmAdapter(this);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new FilmAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Films obj, int position) {
                //Toast.makeText(getBaseContext(), Integer.toString(obj.getId()) + obj.getDescription(), Toast.LENGTH_SHORT).show();
                Intent v = new Intent(getBaseContext(), DetailActivity.class);
                Gson gson = new Gson();
                v.putExtra("film", gson.toJson(obj));
                startActivity(v);
            }
        });
        listView = findViewById(R.id.listView);

        listView.setAdapter(adapter);

        edCari = findViewById(R.id.edCari);
        bnCari = findViewById(R.id.bnCari);

        bnCari.setOnClickListener(myListener);

        String film = edCari.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM, film);

        //getLoaderManager().initLoader(0, bundle, this);
    }

    private void init() {
        getSupportActionBar().setTitle(getString(R.string.lb_form_search));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.act_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<Films>> onCreateLoader(int id, Bundle args) {

        String sFilm = "";
        if (args != null) {
            sFilm = args.getString(EXTRAS_FILM);
        }

        return new AsyncTaskUtils(getBaseContext(), sFilm, "");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Films>> loader, ArrayList<Films> films) {
        adapter.setData(films);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Films>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String film = edCari.getText().toString();

            if (TextUtils.isEmpty(film)) return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_FILM, film);
            getLoaderManager().restartLoader(0, bundle, SearchActivity.this);
        }
    };
}
