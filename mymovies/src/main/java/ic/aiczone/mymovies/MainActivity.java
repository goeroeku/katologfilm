package ic.aiczone.mymovies;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import ic.aiczone.mymovies.adapter.CardViewFilmsAdapter;

import static ic.aiczone.mymovies.entity.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindString(R.string.lb_form_favorite)
    String mTitle;

    @BindView(R.id.rv_category)
    RecyclerView rvCategory;

    private final int LOAD_NOTES_ID = 110;

    private CardViewFilmsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        initData();
    }

    private void init() {
        getSupportActionBar().setTitle(mTitle);
    }

    private void initData() {
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        adapter = new CardViewFilmsAdapter(getBaseContext());
        rvCategory.setAdapter(adapter);

        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.setListFilms(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.setListFilms(null);
    }
}
