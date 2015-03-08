package com.example.ken.materialdesginexample.Class;

/**
 * Created by Ken on 08/03/2015.
 */
public class Comment {

    String descripcion;
    String name;
    int likes;
    int disLikes;

    public String getDescripcion() {
        return descripcion;
    }

    public String getUserName() {
        return name;
    }

    public int getLikes() {
        return likes;
    }

    public int getDisLikes() {
        return disLikes;
    }

    public Comment(String descripcion, String name, int likes, int disLikes) {

        this.descripcion = descripcion;
        this.name = name;
        this.likes = likes;
        this.disLikes = disLikes;
    }
}
