package model;

import view.FameFrame;
import view.MainFrame;
import control.MineField;
import view.HeroFrame;
import view.YouDiedFrame;
import javax.swing.*;

public class Game {

    private static Game currentGame;
    private static int rank;
    private static Timer timer = new Timer(1000, e -> MainFrame.getCurrentMainFrame().getTime().setText(
            String.valueOf(++currentGame.time)));
    private static String[] armyRanks = {"Private (E-1)","Private (E-2)","Private, First Class","Corporal","Sergeant",
            "Staff Sergeant","Platoon Sergeant","First Sergeant","Staff Sergeant Major","Sergeant Major of the Army",
            "Warrant Officer-1","Chief Warrant Officer-2","Chief Warrant Officer-3","Chief Warrant Officer-4",
            "Second Lieutenant","First Lieutenant","Captain","Major","Lieutenant Colonel","Colonel","Brigadier General",
            "Major General","Lieutenant General","General","General of the Army"};
    private boolean finished;
    private boolean newRecord;
    private int clicks;
    private int minesLeft;
    private int time;
    private int closedTiles;

    private String newHero;
    private String[] names;
    private int[] ranks;
    private int[] times;

    private Game(){
        if (timer.isRunning()) {
            timer.stop();
        }
        MainFrame.getCurrentMainFrame().addNewField();
        MainFrame.getCurrentMainFrame().setDisplaysVisibility();
        MainFrame.getCurrentMainFrame().getClicks().setText(String.valueOf(clicks));
        minesLeft = MineField.getCurrentField().getMinesCount();
        MainFrame.getCurrentMainFrame().getMines().setText(String.valueOf(minesLeft));
        MainFrame.getCurrentMainFrame().getTime().setText(String.valueOf(0));
        closedTiles = MineField.getCurrentField().getFieldHeight()
                *MineField.getCurrentField().getFieldWidth()
                -MineField.getCurrentField().getMinesCount();
    }

    private void winTheGame() {
        timer.stop();
        finished = true;
        if (rank< armyRanks.length-1) rank++;
        checkDifficulty();
        if (!newRecord){
            showVictoryDialog();
        }
    }

    private void checkDifficulty() {
        if (MineField.getCurrentField().getDifficultyLevel() == 1) copyHallOfFameArrays(1);
        else if (MineField.getCurrentField().getDifficultyLevel() == 2) copyHallOfFameArrays(2);
        else if (MineField.getCurrentField().getDifficultyLevel() == 3) copyHallOfFameArrays(3);
    }

    private void copyHallOfFameArrays(int level) {
        names = HallOfFame.getInstance().getNames(level);
        ranks = HallOfFame.getInstance().getRanks(level);
        times = HallOfFame.getInstance().getTimes(level);
        updateLeaderBoard();
    }

    private void updateLeaderBoard() {

        for (int i = 0; i < 10; i++) {
            if (time<=times[i]){
                newRecord = true;
                for (int j = 9; j > i; j--) {
                    times[j] = times[j-1];
                    ranks[j] = ranks[j-1];
                    names[j] = names[j-1];
                }
                times[i] = time;
                ranks[i] = rank;
                showNewHeroDialog();
                names[i] = newHero;
                HallOfFame.saveHallOfFame();
                new FameFrame(MainFrame.getCurrentMainFrame());
                break;
            }

        }

    }

    private void showNewHeroDialog() {
        new Digger().playSound("/hero.wav");
        new HeroFrame(MainFrame.getCurrentMainFrame());
    }

    private void showVictoryDialog() {
        new Digger().playSound("/victory.wav");
        JOptionPane.showMessageDialog(MainFrame.getCurrentMainFrame(),
                new String[] {"Good job, soldier!",
                        "  ",
                        "You've cleared this field within " + (time+1) + " seconds.",
                        "You've dug " + clicks + " times.",
                        "Your new rank: " + armyRanks[rank]},
                "Congratulations!",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(getClass().getResource("/complete.png")));
    }

    public void incrementClicks(){
        MainFrame.getCurrentMainFrame().getClicks().setText(String.valueOf(++clicks));
        if (clicks==1) timer.start();
    }

    public void incrementMinesLeft(){
        MainFrame.getCurrentMainFrame().getMines().setText(String.valueOf(++minesLeft));
    }

    public void decrementMinesLeft(){
        MainFrame.getCurrentMainFrame().getMines().setText(String.valueOf(--minesLeft));
        if (minesLeft == 0 && closedTiles == 0){
            winTheGame();
        }
    }

    public void decrementClosedTiles(){
        closedTiles--;
        if (closedTiles==0 && minesLeft == 0){
            winTheGame();
        }
    }

    public void endGame() {
        finished = true;
        timer.stop();
        new YouDiedFrame();
    }

    public static void startNewGame(){
        currentGame = new Game();
    }

    public Boolean IsFinished() {
        return finished;
    }

    public void setNewHero(String newHero) {
        this.newHero = newHero;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static String[] getArmyRanks() {
        return armyRanks;
    }

    public static String getRank(){
        return armyRanks[rank];
    }

}
