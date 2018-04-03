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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ic.aiczone.katologfilm.adapter.FilmAdapter;
import ic.aiczone.katologfilm.models.FilmModel;
import ic.aiczone.katologfilm.utils.AsyncTaskUtils;

/**
 * Created by aic on 26/02/18.
 */

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<FilmModel>> {

    @BindView(R.id.listView) ListView listView;
    @BindView(R.id.edCari) EditText edCari;
    @BindView(R.id.bnCari) Button bnCari;

    FilmAdapter adapter;

    static final String ARG_PARCEL_FIND = "EXTRAS_FILM";
    private String ARG_PARCEL_LIST = "bundle_films";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

        adapter = new FilmAdapter(this);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new FilmAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, FilmModel obj, int position) {
                Intent v = new Intent(getBaseContext(), DetailActivity.class);
                v.putExtra(ARG_PARCEL_LIST, obj);
                startActivity(v);
            }
        });

        listView.setAdapter(adapter);

        bnCari.setOnClickListener(myListener);

        String film = edCari.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARCEL_FIND, film);

        getLoaderManager().initLoader(0, bundle, this);
    }

    private void init() {
        getSupportActionBar().setTitle(getString(R.string.lb_form_search));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
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
    public Loader<ArrayList<FilmModel>> onCreateLoader(int id, Bundle args) {

        String sFilm = "";
        if (args != null) {
            sFilm = args.getString(ARG_PARCEL_FIND);
        }

        return new AsyncTaskUtils(getBaseContext(), sFilm, "");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilmModel>> loader, ArrayList<FilmModel> films) {
        adapter.setData(films);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmModel>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String film = edCari.getText().toString();

            if (TextUtils.isEmpty(film)) return;

            Bundle bundle = new Bundle();
            bundle.putString(ARG_PARCEL_FIND, film);
            getLoaderManager().restartLoader(0, bundle, SearchActivity.this);
        }
    };
}
