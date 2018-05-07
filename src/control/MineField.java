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

    private int difficultyLevel;
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

        identifyDifficultyLevel();
        addButtons();
        plantMines();
        setVisible(true);

        currentField = this;
    }

    private void identifyDifficultyLevel() {
        if (height == 9 && width ==9 && minesCount ==10) difficultyLevel = 1;
        else if (height == 16 && width ==16 && minesCount ==40) difficultyLevel = 2;
        else if (height == 19 && width ==25 && minesCount ==99) difficultyLevel = 3;
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

        for (int minesPlanted = 0; minesPlanted < minesCount; ) {
            int h = new Random().nextInt(height);
            int w = new Random().nextInt(width);
            if (mineField[h][w].getHoard() != 9) {
                mineField[h][w].setHoard(9);
                markAdjacentButtons(h,w);
                minesPlanted++;
            }
        }
    }

    private void markAdjacentButtons(int h, int w) {
                    if (w != 0) {
                        if (mineField[h][w - 1].getHoard() != 9)
                            mineField[h][w - 1].incrementHoard();
                        if (h != 0 && mineField[h - 1][w - 1].getHoard() != 9)
                            mineField[h - 1][w - 1].incrementHoard();
                        if (h != mineField.length - 1 && mineField[h + 1][w - 1].getHoard() != 9)
                            mineField[h + 1][w - 1].incrementHoard();
                    }
                    if (h != 0 && mineField[h - 1][w].getHoard() != 9)
                        mineField[h - 1][w].incrementHoard();
                    if (h != mineField.length - 1 && mineField[h + 1][w].getHoard() != 9)
                        mineField[h + 1][w].incrementHoard();

                    if (w != mineField[h].length - 1) {
                        if (mineField[h][w + 1].getHoard() != 9)
                            mineField[h][w + 1].incrementHoard();
                        if (h != 0 && mineField[h - 1][w + 1].getHoard() != 9)
                            mineField[h - 1][w + 1].incrementHoard();
                        if (h != mineField.length - 1 && mineField[h + 1][w + 1].getHoard() != 9)
                            mineField[h + 1][w + 1].incrementHoard();
                    }
    }

    public static MineField getCurrentField() {
        return currentField;
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

    public MinedButton[][] getMineField() {
        return mineField;
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

    public int getDifficultyLevel() {
        return difficultyLevel;
    }
}
