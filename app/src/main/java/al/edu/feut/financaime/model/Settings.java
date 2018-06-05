package al.edu.feut.financaime.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import java.text.DecimalFormat;

public class Settings {
    private long id;
    private double budget;
    private double incomes;

    //ignore
    private DecimalFormat format = new DecimalFormat("###.###");

    public Settings() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getIncomes() {
        return incomes;
    }

    public void setIncomes(double incomes) {
        this.incomes = incomes;
    }

    public String getIncomesStr() {
        return format.format(incomes);
    }

    public String getBudgetStr() {
        return format.format(budget);
    }

    public static abstract class SettingTable implements BaseColumns {

        //emri i tabeles
        public static final String SETTINGS_TABLE = "settings";

        //emrat e kolonave te tabeles
        public static final String INCOMES = "_incomes";
        public static final String BUGDET = "_budget";


        public static final String CREATE_SETTINGS_TABLE = "CREATE TABLE " + SETTINGS_TABLE + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + INCOMES + " REAL UNIQUE, "
                + BUGDET + " REAL UNIQUE"
                + ")";

        public static long bindItem(SQLiteStatement sqLiteStatement, Settings settings) {
            sqLiteStatement.bindDouble(1, settings.getBudget());
            sqLiteStatement.bindDouble(2, settings.getIncomes());
            return sqLiteStatement.executeInsert();
        }

        public static final String INSERT_OR_REPLACE = "INSERT OR REPLACE INTO " + SETTINGS_TABLE + "("
                + INCOMES + ", "
                + BUGDET + ")"
                + " VALUES(?1, ?2)";

        public static ContentValues contentSettings(Settings settings) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(INCOMES, settings.getIncomes());
            contentValues.put(BUGDET, settings.getBudget());
            return contentValues;
        }

        public static Settings settingsCursor(Cursor cursor) {
            Settings settings = new Settings();
            settings.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
            settings.setIncomes(cursor.getDouble(cursor.getColumnIndex(INCOMES)));
            settings.setBudget(cursor.getDouble(cursor.getColumnIndex(BUGDET)));
            return settings;
        }
    }
}
