package binus.ac.id.a2401955151_uasmobileprogramming;

public class Data {

    private int id;
    private String name, genus, family, order;
    private Nutritions nutritions;

    public Data(int id, String name, String genus, String family, String order, Nutritions nutritions) {
        this.id = id;
        this.name = name;
        this.genus = genus;
        this.family = family;
        this.order = order;
        this.nutritions = nutritions;
    }

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

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Nutritions getNutritions() {
        return nutritions;
    }

    public void setNutritions(Nutritions nutritions) {
        this.nutritions = nutritions;
    }
}
