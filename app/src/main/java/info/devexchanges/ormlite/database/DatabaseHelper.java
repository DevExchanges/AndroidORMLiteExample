package info.devexchanges.ormlite.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import info.devexchanges.ormlite.model.Cat;
import info.devexchanges.ormlite.model.Kitten;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "cat.db";

    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<Cat, Integer> catDAO = null;
    private Dao<Kitten, Integer> kittenDAO = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Cat.class);
            TableUtils.createTable(connectionSource, Kitten.class);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            List<String> allSql = new ArrayList<>();
            for (String sql : allSql) {
                db.execSQL(sql);
            }
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        }

    }

    public Dao<Cat, Integer> getCatsDAO() {
        if (catDAO == null) {
            try {
                catDAO = getDao(Cat.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return catDAO;
    }

    public Dao<Kitten, Integer> getKittenDAO() {
        if (kittenDAO == null) {
            try {
                kittenDAO = getDao(Kitten.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return kittenDAO;
    }
}
