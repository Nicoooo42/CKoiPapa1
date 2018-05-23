package com.demotxt.droidsrce.homedashboard.Model;

import java.util.ArrayList;

/**
 * Created by Lamanna on 23/05/2018.
 */

public class Categorie {

    private Integer id;
    private String title;
    private String description;
    private String illustration;
    private Integer actif;
    private ArrayList<Item> Items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public Integer getActif() {
        return actif;
    }

    public void setActif(Integer actif) {
        this.actif = actif;
    }

    public ArrayList<Item> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Item> items) {
        Items = items;
    }



}
