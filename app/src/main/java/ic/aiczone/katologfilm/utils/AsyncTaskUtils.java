package ic.aiczone.katologfilm.utils;


import android.content.AsyncTaskLoader;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import ic.aiczone.katologfilm.models.FilmModel;

import static ic.aiczone.katologfilm.BuildConfig.API_KEY;
import static ic.aiczone.katologfilm.BuildConfig.API_URL;

/**
 * Created by aic on 26/02/18.
 */

public class AsyncTaskUtils extends AsyncTaskLoader<ArrayList<FilmModel>> {
    private ArrayList<FilmModel> mData;
    private boolean mHasResult = false;

    private String sFilms;
    private String sFeature = "/search/movie";

    public AsyncTaskUtils(final Context context, String sFilms, String sFeature) {
        super(context);

        onContentChanged();
        this.sFilms = sFilms;
        if (!sFeature.equals(""))
            this.sFeature = sFeature;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<FilmModel> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    //private static final String API_KEY = "dac2bd4943970f70319dd37e04836f27"; //di pindah ke build.gradle

    @Override
    public ArrayList<FilmModel> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<FilmModel> filmItems = new ArrayList<>();
        //String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + sFilms;
        String sFilter = "";
        if (!sFilms.equals(""))
            sFilter = "&query=" + sFilms;

        String url = API_URL + sFeature + "?api_key=" + API_KEY + "&language=en-US" + sFilter; //di pindah ke build.gradle

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject film = list.getJSONObject(i);
                        FilmModel oFilm = new FilmModel(film);
                        filmItems.add(oFilm);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return filmItems;
    }

    protected void onReleaseResources(ArrayList<FilmModel> data) {

    }
}
