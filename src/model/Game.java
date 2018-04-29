package model;

import view.MainFrame;
import control.MineField;
import view.YouDiedFrame;
import javax.swing.*;

public class Game {

    private static boolean finished;
    private static Timer timer;
    private static int clicks;
    private static int minesLeft;
    private static int time;
    private static int closedTiles;
    private static int rank;
    private static String[]ranks = {"Private (E-1)","Private (E-2)","Private, First Class","Corporal","Sergeant",
            "Staff Sergeant","Platoon Sergeant","First Sergeant","Staff Sergeant Major","Sergeant Major of the Army",
            "Warrant Officer-1","Chief Warrant Officer-2","Chief Warrant Officer-3","Chief Warrant Officer-4",
            "Second Lieutenant","First Lieutenant","Captain","Major","Lieutenant Colonel","Colonel","Brigadier General",
            "Major General","Lieutenant General","General","General of the Army"};

    public static void incrementClicks(){
        MainFrame.getCurrentMainFrame().getClicks().setText(String.valueOf(++clicks));
        if (clicks==1) timer.start();
    }

    public static String getRank(){
        return ranks[rank];
    }

    public static void startNewGame(){

        MainFrame.getCurrentMainFrame().addNewField();
        MainFrame.getCurrentMainFrame().setDisplaysVisibility();

        finished = false;

        if (timer !=null) {
            timer.stop();
        }

        clicks = 0;
        MainFrame.getCurrentMainFrame().getClicks().setText(String.valueOf(clicks));

        minesLeft = MineField.getCurrentField().getMinesCount();
        MainFrame.getCurrentMainFrame().getMines().setText(String.valueOf(minesLeft));

        time = 0;
        MainFrame.getCurrentMainFrame().getTime().setText(String.valueOf(0));

        timer = new Timer(1000, e -> MainFrame.getCurrentMainFrame().getTime().setText(String.valueOf(++time)));

        closedTiles = MineField.getCurrentField().getFieldHeight()
                *MineField.getCurrentField().getFieldWidth()
                -MineField.getCurrentField().getMinesCount();
    }

    private static void winTheGame() {
        timer.stop();
        finished = true;
        new Digger().playSound("src/resources/victory.wav");
        if (rank<ranks.length-1) rank++;
        JOptionPane.showMessageDialog(MainFrame.getCurrentMainFrame(),
                new String[] {"Good job, soldier!",
                        "You coped within " + (time+1) + " seconds.",
                        "You digged " + clicks + " times.",
                        "Your new rank:",
                        ranks[rank]},
                "Congratulations!",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon("src/resources/completeGame.jpg"));
    }

    public static void incrementMinesLeft(){
        MainFrame.getCurrentMainFrame().getMines().setText(String.valueOf(++minesLeft));
    }

    public static void decrementMinesLeft(){
        MainFrame.getCurrentMainFrame().getMines().setText(String.valueOf(--minesLeft));
        if (minesLeft == 0 && closedTiles == 0){
            winTheGame();
        }
    }

    public static void decrementClosedTiles(){
        closedTiles--;
        if (closedTiles==0 && minesLeft == 0){
            winTheGame();
        }
    }

    public static void endGame() {
        finished = true;
        timer.stop();
        new YouDiedFrame();
        rank = 0;
    }

    public static Boolean IsFinished() {
        return finished;
    }

}