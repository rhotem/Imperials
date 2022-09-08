package me.justacat.imperials;

public enum RegisteredItems {

    ImperialOfSound(new ImperialOfSound());

    private final CustomItem item;

    RegisteredItems(CustomItem item) {

        this.item = item;

    }


    public CustomItem getItem() {return item;}



}
