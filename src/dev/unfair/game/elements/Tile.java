package dev.unfair.game.elements;

import java.util.ArrayList;
import java.util.List;

public class Tile {

    private int num, x, y;
    private boolean pre;

    private List<Integer> notes = new ArrayList<>();

    public Tile(int num, int x, int y, boolean pre) {
        this.num = num;
        this.x = x;
        this.y = y;
        this.pre = pre;
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + 50 && mouseY > y && mouseY < y + 50;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isPre() {
        return pre;
    }

    public List<Integer> getNotes() {
        return notes;
    }

}
