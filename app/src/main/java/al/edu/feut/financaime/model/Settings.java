package al.edu.feut.financaime.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.text.DecimalFormat;

public class Settings {
    private long id;
    private double budget;
    private double incomes;
    private boolean auto;

    //ignore
    private DecimalFormat format = new DecimalFormat("###.###");

    public Settings() {
    }

    public long getId() {
        return id;
    }

    public long setId(long id) {
        this.id = id;
        return id;
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

    public boolean isAuto() {
        return auto;
    }

    public Settings setAuto(boolean auto) {
        this.auto = auto;
        return this;
    }

    public static abstract class SettingTable implements BaseColumns {

        //emri i tabeles
        public static final String SETTINGS_TABLE = "settings";

        //emrat e kolonave te tabeles
        public static final String INCOMES = "_incomes";
        public static final String BUDGET = "_budget";
        public static final String AUTO = "_auto";


        public static final String CREATE_SETTINGS_TABLE = "CREATE TABLE " + SETTINGS_TABLE + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
                + INCOMES + " REAL, "
                + BUDGET + " REAL, "
                + AUTO + " INTEGER"
                + ")";

        public static ContentValues contentSettings(Settings settings) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(INCOMES, settings.getIncomes());
            contentValues.put(BUDGET, settings.getBudget());
            contentValues.put(AUTO, settings.isAuto());
            return contentValues;
        }
        public static ContentValues contentBudgetSettings(Settings settings) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(BUDGET, settings.getBudget());
            return contentValues;
        }
        public static ContentValues contentIncomesSettings(Settings settings) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(INCOMES, settings.getIncomes());
            return contentValues;
        }
        public static ContentValues contentAutoSettings(Settings settings) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(AUTO, settings.isAuto());
            return contentValues;
        }

        public static Settings settingsCursor(Cursor cursor) {
            Settings settings = new Settings();
            settings.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
            settings.setIncomes(cursor.getDouble(cursor.getColumnIndex(INCOMES)));
            settings.setBudget(cursor.getDouble(cursor.getColumnIndex(BUDGET)));
            settings.setAuto(cursor.getShort(cursor.getColumnIndex(AUTO)) > 0);
            return settings;
        }
    }
}
