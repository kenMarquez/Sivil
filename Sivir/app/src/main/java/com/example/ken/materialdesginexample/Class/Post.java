package com.example.ken.materialdesginexample.Class;

/**
 * Created by Ken on 08/03/2015.
 */
public class Post {


    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getLatlon() {
        return latlon;
    }

    public String getIdUser() {
        return idUser;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", latlon='" + latlon + '\'' +
                ", idUser='" + idUser + '\'' +
                ", noComments=" + noComments +
                ", noMatches=" + noMatches +
                ", postId='" + postId + '\'' +
                '}';
    }

    public String getPostId() {
        return postId;
    }

    public int getNoComments() {
        return noComments;
    }

    public int getNoMatches() {
        return noMatches;
    }

    public Post(String title, String category, String description, String latlon, String idUser, int comments, int matches, String postId) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.latlon = latlon;
        this.idUser = idUser;
        this.noComments = comments;
        this.noMatches = matches;
        this.postId = postId;

    }


    private String title;
    private String category;
    private String description;
    private String latlon;
    private String idUser;
    private int noComments;
    private int noMatches;
    private String postId;

}
