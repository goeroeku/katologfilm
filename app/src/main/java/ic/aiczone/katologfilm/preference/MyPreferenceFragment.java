package ic.aiczone.katologfilm.preference;

import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.ButterKnife;
import ic.aiczone.katologfilm.R;
import ic.aiczone.katologfilm.services.SchedulerTask;
import ic.aiczone.katologfilm.utils.AlarmReceiver;

/**
 * Created by aic on 07/04/18.
 * Email goeroeku@gmail.com.
 */

public class MyPreferenceFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    @BindString(R.string.key_reminder_daily)
    String reminder_daily;

    @BindString(R.string.key_reminder_upcoming)
    String reminder_upcoming;

    @BindString(R.string.key_setting_locale)
    String setting_locale;

    private AlarmReceiver alarmReceiver = new AlarmReceiver();

    private int jobId = 10;

    private Context mContext;

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        ButterKnife.bind(this, getActivity());

        findPreference(reminder_daily).setOnPreferenceChangeListener(this);
        findPreference(reminder_upcoming).setOnPreferenceChangeListener(this);
        findPreference(setting_locale).setOnPreferenceClickListener(this);

        //schedulerTask = new SchedulerTask(getActivity());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key = preference.getKey();
        boolean isOn = (boolean) o;

        if (key.equals(reminder_daily)) {
            if (isOn) {
                alarmReceiver.setRepeatingAlarm(getActivity(), alarmReceiver.TYPE_REPEATING,
                        "12:05", getString(R.string.lb_daily_reminder));
            } else {
                alarmReceiver.cancelAlarm(getActivity(), alarmReceiver.TYPE_REPEATING);
            }

            Toast.makeText(mContext, getString(R.string.lb_daily_reminder)
                    + " " + (isOn ? getString(R.string.lb_activated) : getString(R.string.lb_deactivated)), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (key.equals(reminder_upcoming)) {
            if (isOn) {
                startJob();
            } else cancelJob();

            Toast.makeText(mContext, getString(R.string.lb_upcoming_reminder)
                    + " " + (isOn ? getString(R.string.lb_activated) : getString(R.string.lb_deactivated)), Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();

        if (key.equals(setting_locale)) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            return true;
        }

        return false;
    }

    private void startJob() {
        ComponentName mServiceComponent = new ComponentName(mContext, SchedulerTask.class);

        JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);
        /*builder.setPeriodic(AlarmManager.INTERVAL_DAY);*/
        builder.setPeriodic(2000);

        JobScheduler jobScheduler = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }

    private void cancelJob() {
        JobScheduler tm = (JobScheduler) mContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancel(jobId);
    }
}
