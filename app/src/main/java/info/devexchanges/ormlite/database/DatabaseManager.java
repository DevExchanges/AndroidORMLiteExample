package info.devexchanges.ormlite.database;

import android.content.Context;

import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;

import info.devexchanges.ormlite.model.Cat;
import info.devexchanges.ormlite.model.Kitten;

public class DatabaseManager {

    private static DatabaseManager instance;
    private DatabaseHelper helper;

    public static void init(Context ctx) {
        if (null == instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    public DatabaseHelper getHelper() {
        return helper;
    }

    /**
     * Get all customer in db
     *
     * @return
     */
    public ArrayList<Cat> getAllCats() {
        ArrayList<Cat> cats = null;
        try {
            cats = (ArrayList<Cat>) getHelper().getCatsDAO().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cats;
    }

    public void addCat(Cat cat) {
        try {
            getHelper().getCatsDAO().create(cat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshCat(Cat cat) {
        try {
            getHelper().getCatsDAO().refresh(cat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCat(Cat wishList) {
        try {
            getHelper().getCatsDAO().update(wishList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCat (int catId) {
        try {
            DeleteBuilder<Cat, Integer> deleteBuilder = getHelper().getCatsDAO().deleteBuilder();
            deleteBuilder.where().eq("id", catId);
            deleteBuilder.delete();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Kitten newKitten() {
        Kitten kitten = new Kitten();
        try {
            getHelper().getKittenDAO().create(kitten);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kitten;
    }

    public Kitten newKittenAppend(Kitten kitten) {
        try {
            getHelper().getKittenDAO().create(kitten);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kitten;
    }

    public void updateKitten(Kitten item) {
        try {
            getHelper().getKittenDAO().update(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Kitten> getAllKittens() {
        ArrayList<Kitten> kittenArrayList = null;
        try {
            kittenArrayList = (ArrayList<Kitten>) getHelper().getKittenDAO().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kittenArrayList;
    }

    public void deleteKitten (int kittenId) {
        try {
            DeleteBuilder<Kitten, Integer> deleteBuilder = getHelper().getKittenDAO().deleteBuilder();
            deleteBuilder.where().eq("id", kittenId);
            deleteBuilder.delete();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}