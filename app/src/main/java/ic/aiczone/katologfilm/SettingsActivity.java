package ic.aiczone.katologfilm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ic.aiczone.katologfilm.preference.MyPreferenceFragment;

/**
 * Created by aic on 06/04/18.
 * Email goeroeku@gmail.com.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyPreferenceFragment myPreference = new MyPreferenceFragment();
        myPreference.setContext(this);
        getFragmentManager().beginTransaction().replace(android.R.id.content, myPreference).commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
