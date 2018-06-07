package al.edu.feut.financaime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import al.edu.feut.financaime.callback.Schedule;
import al.edu.feut.financaime.model.Database;

public class HostActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, Schedule {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        //google analitics
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName(HostActivity.class.getName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        //inflater home fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.host, new HomeFragment())
                .commit();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //Lambda Expression
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if(getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
        });
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                if (getSupportFragmentManager().findFragmentById(R.id.host) instanceof YouTubePlayerSupportFragment) {
                    navigation.setVisibility(View.GONE);
                }
                else if (getSupportFragmentManager().findFragmentById(R.id.host) instanceof GoogleMapFragment) {
                    navigation.setVisibility(View.GONE);
                }
                else if (getSupportFragmentManager().findFragmentById(R.id.host) instanceof StatisticsFragment) {
                    navigation.setVisibility(View.GONE);
                }
                else if (getSupportFragmentManager().findFragmentById(R.id.host) instanceof SettingsFragment) {
                    navigation.setVisibility(View.GONE);
                }
                else if (getSupportFragmentManager().findFragmentById(R.id.host) instanceof ExpenseCategoriesFragment) {
                    navigation.setVisibility(View.GONE);
                }
            } else {
                navigation.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.youtube:
                /*getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host, new YoutubeFragment())
                        .addToBackStack("YOUTUBE_FRAGMENT")
                        .commit();*/
                //YouTubePlayerSupportFragment youTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_view);
                YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
                YouTubePlayer.OnInitializedListener onInitializedListener = new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.loadVideo("a4NT5iBFuZs");
                        //youTubePlayer.setShowFullscreenButton(false);
                        //youTubePlayer.setFullscreen(false);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                };

                youTubePlayerFragment.initialize("AIzaSyDFcMlEuBkzdu-bqfdrdbl7-HInFn8i63I" ,onInitializedListener);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host, youTubePlayerFragment)
                        .addToBackStack("YOUTUBE_FRAGMENT")
                        .commit();
                break;
            case R.id.map:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host, new GoogleMapFragment())
                        .addToBackStack("GOOGLE_MAP__FRAGMENT")
                        .commit();
                break;
            case R.id.statistics:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host, new StatisticsFragment())
                        .addToBackStack("STATISTICS_FRAGMENT")
                        .commit();
                break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host, new SettingsFragment().OnSchedule(this))
                        .addToBackStack("SETTINGS_FRAGMENT")
                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.host, new HomeFragment())
                        .commit();
                return true;
            case R.id.budget:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.host, new BudgetFragment())
                        .commit();
                return true;
            case R.id.expense:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.host, new ExpenseFragment())
                        .commit();
                return true;
            case R.id.expense_log:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.host, new ExpenseLogFragment())
                        .commit();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void schedule(boolean schedule) {

    }

    //if (c.get(Calendar.DAY_OF_MONTH) == 1) {
    /*public void schedule() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        //Intent intent = new Intent(this, LocationReceiver.class);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }*/
   /* public void cancel() {
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }*/
}
