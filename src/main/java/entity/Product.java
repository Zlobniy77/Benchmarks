package entity;

/**
 * Created by Zlobniy on 02.12.2016.
 */
public class Product {

    private String name;
    private String value;

    public Product(){

    }

    public Product( String name ){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public String toString(){
        return this.name;
    }

}
