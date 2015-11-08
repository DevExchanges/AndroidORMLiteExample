package info.devexchanges.ormlite.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
import java.util.List;

public class Cat {
    @DatabaseField(generatedId=true)
    private int id;

    @DatabaseField (columnName = "name")
    private String name;

    @ForeignCollectionField
    private ForeignCollection<Kitten> kittens;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKittens(ForeignCollection<Kitten> items) {
        this.kittens = items;
    }

    public List<Kitten> getKittens() {
        ArrayList<Kitten> itemList = new ArrayList<>();
        for (Kitten item : kittens) {
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
