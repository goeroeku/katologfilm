package ic.aiczone.katologfilm.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import ic.aiczone.katologfilm.DetailActivity;
import ic.aiczone.katologfilm.R;
import ic.aiczone.katologfilm.models.FilmModel;
import ic.aiczone.katologfilm.utils.Tools;

import static ic.aiczone.katologfilm.BuildConfig.API_KEY;
import static ic.aiczone.katologfilm.BuildConfig.API_URL;

/**
 * Created by aic on 06/04/18.
 * Email goeroeku@gmail.com.
 */

public class SchedulerTask extends JobService {

    public static final String TAG = SchedulerTask.class.getSimpleName();

    private String ARG_PARCEL_LIST = "bundle_films";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob() Executed");
        getCurrentMovie(params);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob() Executed");
        return true;
    }

    private void getCurrentMovie(final JobParameters job) {
        /*final ArrayList<FilmModel> filmItems = new ArrayList<>();*/

        Log.d(TAG, "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = API_URL + "/movie/now_playing/?api_key=" + API_KEY + "&language=en-US"; //di pindah ke build.gradle
        Log.e(TAG, "getCurrentWeather: " + url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);

                String title = getString(R.string.lb_alarm_daily_reminder);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject film = list.getJSONObject(i);
                        FilmModel oFilm = new FilmModel(film);
                        /*notifId = (int )(Math.random() * 50 + 1);*/
                        String message = getString(R.string.lb_col_title) + ": ";
                        int notifId = oFilm.getId();
                        if (Tools.currentDate(oFilm.getRelease())) {
                            message += oFilm.getTitle();
                            showNotification(getApplicationContext(), title,
                                    message + oFilm.getTitle(), notifId, oFilm);
                        }
                    }

                    jobFinished(job, false);
                } catch (Exception e) {
                    jobFinished(job, true);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                jobFinished(job, true);
            }
        });
    }

    private void showNotification(Context context, String title, String message, int notifId, FilmModel film) {
    /*private void showNotification(Context context, String title, String message, int notifId) {*/
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ARG_PARCEL_LIST, film);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_reply_black_24dp)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}
