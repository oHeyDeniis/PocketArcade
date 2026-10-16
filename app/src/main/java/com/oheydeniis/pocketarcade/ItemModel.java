package com.oheydeniis.pocketarcade;

public class ItemModel {
    private String word;
    private String category;

    public ItemModel(String word, String category) {
        this.word = word;
        this.category = category;
    }

    public String getWord() { return word; }
    public String getCategory() { return category; }
}