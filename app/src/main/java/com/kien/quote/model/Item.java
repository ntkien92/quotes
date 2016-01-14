package com.kien.quote.model;

/**
 * Created by kien on 03/back/2016.
 */
public class Item {
    private  int id;
    private String image;
    private int type_quote;

    public Item(){

    }

    public Item(int id, String image, int type_quote) {
        this.id = id;
        this.image = image;
        this.type_quote = type_quote;
    }

    public Item(String image, int type_quote) {
        this.image = image;
        this.type_quote = type_quote;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType_quote() {
        return type_quote;
    }

    public void setType_quote(int type_quote) {
        this.type_quote = type_quote;
    }
}
