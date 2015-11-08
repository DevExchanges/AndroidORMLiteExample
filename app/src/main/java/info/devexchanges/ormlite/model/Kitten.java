package info.devexchanges.ormlite.model;

import com.j256.ormlite.field.DatabaseField;

public class Kitten {

    @DatabaseField(generatedId=true)
    private int id;

    @DatabaseField (columnName = "name")
    private String name;

    @DatabaseField(foreign=true, foreignAutoRefresh=true)
    private Cat cat;

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

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }
}
