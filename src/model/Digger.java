package model;

import control.MineField;
import control.MinedButton;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.IOException;

public class Digger{

    private int h;
    private int w;
    private boolean ignoreNotZeroTile;
    private MinedButton[][] field;
    private MinedButton diggedButton;

    Digger(){}

    public Digger( int h, int w) {
        this.h = h;
        this.w = w;
        field = MineField.getCurrentField().getMineField();
        diggedButton = field[h][w];
    }

    public Digger( int h, int w, boolean b){
        this.h = h;
        this.w = w;
        this.ignoreNotZeroTile = b;
        field = MineField.getCurrentField().getMineField();
        diggedButton = field[h][w];
    }

    public void start() {

        if (diggedButton.getHoard()<10
                && (diggedButton.isEnabled() || ignoreNotZeroTile)) {
            if (ignoreNotZeroTile){
                playSound("/dig.wav");
            }
            String s = String.format("/%d.jpg", diggedButton.getHoard());
            diggedButton.setIcon(new ImageIcon(getClass().getResource(s)));
            diggedButton.setDisabledIcon(new ImageIcon(getClass().getResource(s)));
            diggedButton.setEnabled(false);

            if (diggedButton.getHoard() == 9 && !Game.getCurrentGame().IsFinished()) {
                diggedButton.setDisabledIcon(new ImageIcon(getClass().getResource("/mine.jpg")));
                Game.getCurrentGame().endGame();
                playSound("/explosion.wav");
                showAllMines();

            }

            else if ((diggedButton.getHoard() == 0||ignoreNotZeroTile) && !Game.getCurrentGame().IsFinished()) {
                if (w != 0
                        && field[h][w - 1].isEnabled())
                    new Digger(h,w-1).start();
                if (w != 0
                        && h != field.length - 1
                        && field[h + 1][w - 1].isEnabled())
                    new Digger(h+1,w-1).start();
                if (w != 0
                        && h != 0
                        && field[h - 1][w - 1].isEnabled())
                    new Digger(h-1,w-1).start();
                if (w != field[h].length - 1
                        && field[h][w + 1].isEnabled())
                    new Digger(h,w+1).start();
                if (w != field[h].length - 1
                        && h != 0
                        && field[h - 1][w + 1].isEnabled())
                    new Digger(h-1,w+1).start();
                if (w != field[h].length - 1
                        && h != field.length - 1
                        && field[h + 1][w + 1].isEnabled())
                    new Digger(h+1,w+1).start();
                if (h != 0
                        && field[h - 1][w].isEnabled())
                    new Digger(h-1,w).start();
                if (h != field.length - 1
                        && field[h + 1][w].isEnabled())
                    new Digger(h+1,w).start();
            }

            if (!ignoreNotZeroTile && !Game.getCurrentGame().IsFinished()) {
                Game.getCurrentGame().decrementClosedTiles();
            }
        }
    }

    void playSound(String s) {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(getClass().getResource((s)));
            Clip clip = AudioSystem.getClip();
            clip.open(sound);
            clip.setFramePosition(0);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private void showAllMines() {
        for (int h = 0; h < field.length; h++) {
            for (int w = 0; w < field[h].length; w++) {
                diggedButton = field[h][w];
                if (diggedButton.getHoard() == 9 && diggedButton.isEnabled()) {
                    new Digger(h, w).start();
                }
                if (diggedButton.getHoard() > 9 && diggedButton.getHoard() < 19) {
                    diggedButton.setIcon(new ImageIcon(getClass().getResource("/wrongFlag.jpg")));
                }
            }
        }
    }
}
