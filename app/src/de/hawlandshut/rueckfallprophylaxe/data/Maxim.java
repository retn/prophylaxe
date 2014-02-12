package de.hawlandshut.rueckfallprophylaxe.data;

public class Maxim {
    private int id;
    private String text;

    public Maxim(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getID() {
         return this.id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String txt) {
        this.text = txt;
    }
}
