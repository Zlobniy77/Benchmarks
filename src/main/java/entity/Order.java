package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Zlobniy on 02.12.2016.
 */
public class Order {

    private String name;
    private List<Product> products = new ArrayList<>();

    public Order( Product... products ){
        Collections.addAll( this.products, products );
    }

    public Order( List<Product> products ){
        this.products.addAll( products );
    }

    public Order(){

    }

    public String getName(){
        return name;
    }

    public void setName( String name ){
        this.name = name;
    }

    public List<Product> getProducts(){
        return products;
    }

    public void setProducts( List<Product> products ){
        this.products = products;
    }
}

