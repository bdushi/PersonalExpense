package al.bruno.financaime;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HostActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        //google analitics
        FirebaseAnalytics.getInstance(this);
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
                else
                if (getSupportFragmentManager().findFragmentById(R.id.host) instanceof GoogleMapFragment) {
                    navigation.setVisibility(View.GONE);
                }
                else if (getSupportFragmentManager().findFragmentById(R.id.host) instanceof StatisticsFragment) {
                    navigation.setVisibility(View.GONE);
                }
                else if (getSupportFragmentManager().findFragmentById(R.id.host) instanceof SettingsFragment) {
                    navigation.setVisibility(View.GONE);
                }
                else if (getSupportFragmentManager().findFragmentById(R.id.host) instanceof CategoriesFragment) {
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
        switch (item.getItemId()) {
           case R.id.youtube:
                /*getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host, new YoutubeFragment())
                        .addToBackStack("YOUTUBE_FRAGMENT")
                        .commit();*/
                //YouTubePlayerSupportFragment youTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_view);
                /*YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
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
                };*/

                /*youTubePlayerFragment.initialize(getString(R.string.google_maps_key) ,onInitializedListener);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host, youTubePlayerFragment)
                        .addToBackStack("YOUTUBE_FRAGMENT")
                        .commit();*/
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
                        .replace(R.id.host, new SettingsFragment())
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
                        .replace(R.id.host, new DetailsFragment())
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
}
