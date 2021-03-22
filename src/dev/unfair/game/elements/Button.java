package dev.unfair.game.elements;

import dev.unfair.game.Sudoku;
import processing.core.PConstants;

public class Button {

    private String text;
    private int x, y, width, height;
    private ButtonActionListener bal;

    private Sudoku su = Sudoku.INSTANCE;

    public Button(String text, int x, int y, int width, int height, ButtonActionListener bal) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bal = bal;
    }

    public void render(int mouseX, int mouseY) {
        su.stroke(255, 175);
        su.fill(0, 150);
        su.rect(x, y, width, height);

        su.fill(255, isHovered(mouseX, mouseY) ? 220 : 255);

        su.textSize(18);
        su.textAlign(PConstants.CENTER, PConstants.CENTER);
        su.text(text, x + (width / 2f), y + (height / 2f) - 2);
    }

    public void onMouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY) && mouseButton == 37) bal.onClick();
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public interface ButtonActionListener {
        void onClick();
    }

}
