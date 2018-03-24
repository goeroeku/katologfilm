package ic.aiczone.katologfilm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ic.aiczone.katologfilm.adapter.CardViewFilmsAdapter;
import ic.aiczone.katologfilm.models.Films;
import ic.aiczone.katologfilm.utils.AsyncTaskUtilsFragment;

/**
 * Created by aic on 24/03/18.
 */

public class NowFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Films>> {

    private String ARG_PARCEL_LIST = "bundle_films";
    CardViewFilmsAdapter cardViewFilmsAdapter;

    public NowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalogue, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        RecyclerView rvCategory;

        rvCategory = view.findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        cardViewFilmsAdapter = new CardViewFilmsAdapter(getContext());
        rvCategory.setAdapter(cardViewFilmsAdapter);

        getLoaderManager().initLoader(0, new Bundle(), this);
    }

    @Override
    public Loader<ArrayList<Films>> onCreateLoader(int id, Bundle args) {
        String sFilm = "";
        if (args != null) {
            sFilm = args.getString(ARG_PARCEL_LIST);
        }

        return new AsyncTaskUtilsFragment(getContext(), sFilm, "/movie/now_playing");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Films>> loader, ArrayList<Films> data) {
        cardViewFilmsAdapter.setListFilms(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Films>> loader) {
        cardViewFilmsAdapter.setListFilms(null);
    }
}
