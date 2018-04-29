package control;

import model.Digger;
import model.Game;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MineField extends JPanel{

    private static MineField currentField ;
    private static int nextHeight = 9;
    private static int nextWidth = 9;
    private static int nextMinesCount = 10;

    private int height;
    private int width;
    private int minesCount;
    private MinedButton[][] mineField;

    public MineField(){
        this.height = nextHeight;
        this.width = nextWidth;
        this.minesCount = nextMinesCount;

        mineField = new MinedButton[height][width];
        setBounds((504 - width * 20)/2,
                  (382 - height * 20)/2,
                  width *20,
                  height *20);
        setLayout(new GridLayout(height, width));

        addButtons();
        plantMines();
        MarkNearbyTiles();
        setVisible(true);

        currentField = this;
    }

    private void markMine(int h, int w){
        if (mineField[h][w].getHoard()<10) {
            mineField[h][w].superIncrementHoard();
            mineField[h][w].setIcon(new ImageIcon(getClass().getResource("/flag.jpg")));
            Game.getCurrentGame().decrementMinesLeft();
        }
        else {
            mineField[h][w].superDecrement();
            mineField[h][w].setIcon(new ImageIcon(""));
            Game.getCurrentGame().incrementMinesLeft();
        }
    }

    private void addButtons(){
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                MinedButton minedButton = new MinedButton();
                int finalH = h;
                int finalW = w;
                minedButton.addMouseListener(new MouseInputAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isMiddleMouseButton(e) || e.getClickCount() == 2) {
                            if (!minedButton.isEnabled() && !Game.getCurrentGame().IsFinished()) {
                                Game.getCurrentGame().incrementClicks();
                                new Digger(finalH, finalW, true).start();
                            }
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            if (minedButton.isEnabled() && !Game.getCurrentGame().IsFinished()) {
                                markMine(finalH, finalW);
                            }
                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            if (minedButton.isEnabled() && !Game.getCurrentGame().IsFinished()) {
                                Game.getCurrentGame().incrementClicks();
                                new Digger(finalH, finalW).start();
                            }
                        }
                    }
                });
                mineField[h][w] = minedButton;
                add(minedButton);
            }
        }
    }

    private void plantMines() {


        //fill field with zeroes
        for (int h = 0; h < height; h++)
            for (int w = 0; w < width; w++)
                mineField[h][w].setHoard(0);

        //plant mines in the field
        for (int minesPlanted = 0; minesPlanted < minesCount; ) {
            int x = new Random().nextInt(height);
            int y = new Random().nextInt(width);
            if (mineField[x][y].getHoard() != 9) {
                mineField[x][y].setHoard(9);
                minesPlanted++;
            }
        }
    }

    private void MarkNearbyTiles() {
        for (int h = 0; h < mineField.length; h++) {
            for (int w = 0; w < mineField[h].length; w++) {
                if (mineField[h][w].getHoard() != 9) {
                    if (w != 0) {
                        if (mineField[h][w - 1].getHoard() == 9)
                            mineField[h][w].incrementHoard();
                        if (h != 0) if (mineField[h - 1][w - 1].getHoard() == 9)
                            mineField[h][w].incrementHoard();
                        if (h != mineField.length - 1) if (mineField[h + 1][w - 1].getHoard() == 9)
                            mineField[h][w].incrementHoard();
                    }
                    if (h != 0) if (mineField[h - 1][w].getHoard() == 9)
                        mineField[h][w].incrementHoard();
                    if (h != mineField.length - 1) if (mineField[h + 1][w].getHoard() == 9)
                        mineField[h][w].incrementHoard();
                    if (w != mineField[h].length - 1) {
                        if (mineField[h][w + 1].getHoard() == 9)
                            mineField[h][w].incrementHoard();
                        if (h != 0) if (mineField[h - 1][w + 1].getHoard() == 9)
                            mineField[h][w].incrementHoard();
                        if (h != mineField.length - 1) if (mineField[h + 1][w + 1].getHoard() == 9)
                            mineField[h][w].incrementHoard();
                    }
                }
            }
        }
    }

    public static MineField getCurrentField() {
        return currentField;
    }

    public MinedButton[][] getMineField() {
        return mineField;
    }

    public static void setNextHeight(int nextHeight) {
        MineField.nextHeight = nextHeight;
    }

    public static void setNextWidth(int nextWidth) {
        MineField.nextWidth = nextWidth;
    }

    public static void setNextMinesCount(int nextMinesCount) {
        MineField.nextMinesCount = nextMinesCount;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public int getFieldHeight() {
        return height;
    }

    public int getFieldWidth() {
        return width;
    }
}
