package dev.unfair.game;

import dev.unfair.game.elements.Button;
import dev.unfair.game.elements.Tile;
import dev.unfair.game.utils.IoUtil;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sudoku extends PApplet {

    public static final Sudoku INSTANCE = new Sudoku();

    private final int SIZE = 550;
    public List<List<Tile>> tiles = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();

    private PImage bg;

    private Tile selectedTile;
    private int clicked;

    @Override
    public void settings() {
        size(SIZE, SIZE);
    }

    @Override
    public void setup() {
        // Get the file path of the program
        File path = new File(System.getProperty("user.dir"));

        // Randomly select a background image
        File[] pics = new File(path.getAbsolutePath() + "\\img").listFiles();

        if (pics == null) bg = null;
        else bg = loadImage(pics[new Random().nextInt(pics.length)].getAbsolutePath());

        // Randomly select a map
        File[] maps = new File(path.getAbsolutePath() + "\\maps").listFiles();
        if (maps == null) System.exit(-1);

        File selectedMap = maps[new Random().nextInt(maps.length)];
        List<String> lines = IoUtil.INSTANCE.fileToList(selectedMap);

        // Add the map "tiles" to a list
        int yInt = 50;
        for (String y : lines) {
            String[] t = y.split("");
            List<Tile> line = new ArrayList<>();

            int xInt = 50;
            for (String x : t) {
                boolean empty = x.equals("x");
                line.add(new Tile(empty ? 0 : Integer.parseInt(x), xInt, yInt, !empty));
                xInt += 50;
            }

            yInt += 50;
            tiles.add(line);
        }

        // Add the submit button to the scene
        buttons.add(new Button("Submit", 60, SIZE - 40, 130,
                30, () -> System.out.println(Logic.INSTANCE.checkPuzzle())));
    }

    @Override
    public void draw() {
        // Render either the background or a white canvas
        if (bg == null) background(255);
        else image(bg, 0, 0, SIZE, SIZE);

        // Draw The board
        fill(0);
        rect( 48, 48, SIZE - 96, SIZE - 96);

        for (List<Tile> tileLine : tiles) {
            for (Tile t : tileLine) {
                if (t == selectedTile) fill(252, 244, 242);
                else fill(255);

                stroke(178, 190, 195);
                rect(t.getX(), t.getY(), 50, 50);

                // Unfilled tiles have num values of 0
                if (t.getNum() != 0) {
                    if (t.isPre()) fill(0);
                    else fill(9, 132, 227);

                    textSize(25);
                    textAlign(CENTER, CENTER);
                    text(t.getNum(), t.getX() + 25, t.getY() + 25);
                } else {
                    fill(45, 52, 54);
                    textSize(12);
                    textAlign(LEFT, TOP);

                    int x = 3, y = 3, c = 1;
                    for (int i : t.getNotes()) {
                        text(i, t.getX() + x, t.getY() + y);

                        if (c == 3) {
                            y += 12;
                            x = 3;
                            c = 1;
                            continue;
                        }

                        x += 12;
                        c++;
                    }
                }
            }

        for (int i = 200; i <= 350; i += 150) {
            stroke(0);
            strokeWeight(2);
            line(i, 50, i, SIZE - 50);
            line(50, i, SIZE - 50, i);
        }

        // Render all buttons
            for (Button b : buttons)
                b.render(mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked() {
        // Call the buttons click methods
        for (Button b : buttons)
            b.onMouseClicked(mouseX, mouseY, mouseButton);

        // Choose between number mode and note mode
        Tile tile = getHoveredTile();
        if (tile == null || tile.isPre()) return;

        clicked = mouseButton == 37 ?
                0 : mouseButton == 39 ? 1 : -1;

        selectedTile = tile;
    }

    @Override
    public void keyPressed() {
        // Make sure the tile is the selected one
        if (selectedTile == null) return;

        // Only accept numbers between 1-9
        String nums = "123456789";
        int num;

        try {
            num = Integer.parseInt(Character.toString(key));
        } catch (NumberFormatException e) {
            return;
        }

        if (!nums.contains(Integer.toString(num))) return;

        if (clicked == 0)
            selectedTile.setNum(num);
        else
            if (clicked == 1) {
            if (!selectedTile.getNotes().contains(num))
                selectedTile.getNotes().add(num);
            else {
                int index = getIndexOfNote(selectedTile, num);
                if (index != -1) selectedTile.getNotes().remove(index);
            }
        }

            selectedTile = null;
    }

    // Get the array index of a number in notes for removal
    private int getIndexOfNote(Tile tile, int num) {
        int c = 0;
        for (int i : tile.getNotes())
            if (num == i) return c;
            else c++;

            return -1;
    }

    // Get the hovered tile of the user
    private Tile getHoveredTile() {
        for (List<Tile> y : tiles)
            for (Tile t : y)
                if (t.isHovered(mouseX, mouseY))
                    return t;
                return null;
    }

    public static void main(String[] args) {
        PApplet.runSketch(new String[]{"ProcessingTest"}, INSTANCE);
    }

}