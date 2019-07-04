package com.example.rijskviewer.beans;

public class Artist {
    private String author;
    private int artWorkNumber;

    public Artist(){
        super();
    }

    //Getters and setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getArtWorkNumber() {
        return artWorkNumber;
    }
    public String getArtWorkNumberString() {
        return Integer.toString(artWorkNumber);
    }

    public void setArtWorkNumber(int artWorkNumber) {
        this.artWorkNumber = artWorkNumber;
    }

    @Override
    public String toString() {
        return this.author + " " + this.artWorkNumber;
}
}
