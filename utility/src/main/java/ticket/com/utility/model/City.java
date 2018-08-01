package ticket.com.utility.model;

public class City {

    private String name;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return ((City)obj).name.equals(this.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
