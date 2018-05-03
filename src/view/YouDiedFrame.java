package view;

import model.Game;
import javax.swing.*;
import java.awt.*;

public class YouDiedFrame extends JWindow {

    private float opacity = 1.5f;
    private Timer opacityTimer;
    private JLabel background;

    public YouDiedFrame(){

        setAlwaysOnTop(true);
        setBounds(  view.MainFrame.getCurrentMainFrame().getLocation().x+153,
                    view.MainFrame.getCurrentMainFrame().getLocation().y+115,
                    210,210);

        background = new JLabel(new ImageIcon(getClass().getResource("/youdied.jpg")));
        background.setBounds(0,0,210,210);
        add(background);

        initializeResurrectButton();
        initializeAndStartOpacityTimer();

        setVisible(true);
    }

    private void initializeAndStartOpacityTimer() {
        opacityTimer = new Timer(200, e ->{
                opacity -= 0.05;
                if (opacity < 1){
                    setOpacity(opacity);
                }
                if (opacity < 0.1) {
                    opacityTimer.stop();
                    dispose();
                }
        });
        opacityTimer.start();
    }

    private void initializeResurrectButton() {
        JButton resurrect = new JButton("Resurrect");
        resurrect.setBackground(new Color(25,25,25));
        resurrect.setBounds(55,175,100,25);
        resurrect.setForeground(Color.white);
        resurrect.addActionListener(e -> {
            Game.startNewGame();
            dispose();
        });
        background.add(resurrect);
    }
}
