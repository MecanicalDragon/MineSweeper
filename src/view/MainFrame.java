package view;

import control.MineField;
import model.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame{

    private static MainFrame currentMainFrame;

    private view.DigitalDisplay time;
    private view.DigitalDisplay mines;
    private view.DigitalDisplay clicks;
    private JPanel field;
    private JLabel background;

    public MainFrame(){
        Image image = Toolkit.getDefaultToolkit().createImage
                (getClass().getResource("/complete.png"));
        setIconImage( image );
        setTitle("MineSweeper");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(780,420);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        addClosingMessage();
        addGUIElements();

        currentMainFrame = this;
        setVisible(true);

    }

    private void addClosingMessage() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(
                        MainFrame.currentMainFrame,
                        "Perfect service, "+ Game.getRank()+ ".\n " +
                                "Good luck in a Civvy Street.\n" +
                                "@MecanicalDragon",
                        "Resign",
                        JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon(getClass().getResource("/retired.png")));
            }
        });
    }

    private void addGUIElements() {
        background = new JLabel(new ImageIcon(getClass().getResource("/field.jpg")));
        background.setBounds(0,0,800,400);
        add(background);

        JButton newGame = new JButton("Start New Game");
        newGame.addActionListener(e -> new Game());
        newGame.setBounds(525,70,200,28);

        JButton changeDifficulty = new JButton("Change Difficulty");
        changeDifficulty.addActionListener(e -> new view.LevelFrame(this));
        changeDifficulty.setBounds(525,120,200,28);

        background.add(newGame);
        background.add(changeDifficulty);
        background.add(time = new view.DigitalDisplay(524,15, 1));
        background.add(mines = new view.DigitalDisplay(594,15, 2));
        background.add(clicks = new view.DigitalDisplay(664,15, 3));
    }

    public void addNewField(){
        if (field != null){
            field.setVisible(false);
        }
        background.add(field = new MineField());
        SwingUtilities.updateComponentTreeUI(field);

    }

    public void setDisplaysVisibility() {
        if (!time.isVisible()) {
            time.setVisible(true);
        }
        if (!mines.isVisible()) {
            mines.setVisible(true);
        }
        if (!clicks.isVisible()) {
            clicks.setVisible(true);
        }
    }

    public static MainFrame getCurrentMainFrame() {
        return currentMainFrame;
    }

    public view.DigitalDisplay getTime() {
        return time;
    }

    public view.DigitalDisplay getMines() {
        return mines;
    }

    public view.DigitalDisplay getClicks() {
        return clicks;
    }

}