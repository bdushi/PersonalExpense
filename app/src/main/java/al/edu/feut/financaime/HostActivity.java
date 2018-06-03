package al.edu.feut.financaime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import al.edu.feut.financaime.model.Database;

public class HostActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.host, new HomeFragment())
                .commit();
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        new Database(this);
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
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SettingsFragment())
                        .addToBackStack("SETTINGS_FRAGMENT")
                        .commit();
                break;
            case R.id.map:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SettingsFragment())
                        .addToBackStack("SETTINGS_FRAGMENT")
                        .commit();
                break;
            case R.id.statistics:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SettingsFragment())
                        .addToBackStack("SETTINGS_FRAGMENT")
                        .commit();
                break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new SettingsFragment())
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
                        .addToBackStack("USER_DETAILS_FRAGMENT")
                        .replace(R.id.host, new BudgetFragment())
                        .commit();
                return true;
            case R.id.expense:
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("EXPENSE_FRAGMENT")
                        .replace(R.id.host, new ExpenseFragment())
                        .commit();
                return true;
            case R.id.expense_log:
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("EXPENSE_LOG_FRAGMENT")
                        .replace(R.id.host, new ExpenseLogFragment())
                        .commit();
                return true;
            default:
                return false;
        }
    }
}
