package view;

import control.MineField;
import model.Game;
import javax.swing.*;
import java.awt.*;

class LevelFrame extends JDialog {

    private boolean ownRules;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField minesCountField;
    private JPanel ownRulesPanel;

    LevelFrame(JFrame owner){
        super(owner,"", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(owner.getLocation().x+325,owner.getLocation().y+40,180,200);
        setResizable(false);
        setLayout(new GridLayout(7,1));
        addGUIElements();
    }

    private void addGUIElements() {
        JLabel choose = new JLabel("      Choose Your destiny:");
        add(choose);
        addDifficultyRadioButtons();
        ownRulesPanel = addOwnRulesPanel();
        JButton ok = new JButton("Accept consequences");
        ok.addActionListener(e -> confirmChanges());
        add(ok);
        JLabel back = new JLabel(new ImageIcon(getClass().getResource("/keepout.jpg")));
        add(back);
        back.add(ownRulesPanel);
        setVisible(true);
    }

    private JPanel addOwnRulesPanel() {
        JPanel ownRulesPanel = new JPanel();
        ownRulesPanel.setBounds(-7,0,180,23);
        ownRulesPanel.setLayout(new GridLayout(1,3));
        widthField = new KeepOutField();
        widthField.setToolTipText("Width. Min: 2, max: 25");
        ownRulesPanel.add(widthField);
        heightField = new KeepOutField();
        heightField.setToolTipText("Height. Min: 2, max: 19");
        ownRulesPanel.add(heightField);
        minesCountField = new KeepOutField();
        minesCountField.setToolTipText("Mines amount. Min: 3, max: 470");
        minesCountField.addActionListener(e -> {
            ownRules = true;
            confirmChanges();
        });
        ownRulesPanel.add(minesCountField);
        ownRulesPanel.setVisible(false);
        return ownRulesPanel;
    }

    private void addDifficultyRadioButtons() {
        ButtonGroup levels = new ButtonGroup();
        JRadioButton easy = new JRadioButton("I'm too young to die.");
        easy.setToolTipText("9x9 field, 10 minesCountField");
        JRadioButton medium = new JRadioButton("I'm not afraid to die.");
        medium.setToolTipText("16x16 field, 40 minesCountField");
        JRadioButton hard = new JRadioButton("It's a good day to die.");
        hard.setToolTipText("25x19 field, 99 minesCountField");
        JRadioButton own = new JRadioButton("I live my own rules.");
        own.setToolTipText("Set your parameters: widthField, heightField and minesCountField amount");
        add(easy);
        add(medium);
        add(hard);
        add(own);
        levels.add(easy);
        levels.add(medium);
        levels.add(hard);
        levels.add(own);

        easy.addActionListener(e -> {
            MineField.setNextMinesCount(10);
            MineField.setNextHeight(9);
            MineField.setNextWidth(9);
            ownRules = false;
            ownRulesPanel.setVisible(false);
        });
        medium.addActionListener(e -> {
            MineField.setNextMinesCount(40);
            MineField.setNextHeight(16);
            MineField.setNextWidth(16);
            ownRules = false;
            ownRulesPanel.setVisible(false);
        });
        hard.addActionListener(e -> {
            MineField.setNextMinesCount(99);
            MineField.setNextHeight(19);
            MineField.setNextWidth(25);
            ownRules = false;
            ownRulesPanel.setVisible(false);
        });
        own.addActionListener(e -> {
            ownRules = true;
            ownRulesPanel.setVisible(true);
        });
    }

    private void acceptOwnRules(String mines, String height, String width) {
            int m = Integer.parseInt(mines);
            int h = Integer.parseInt(height);
            int w = Integer.parseInt(width);
            if (m<3) m=3;
            if (m>470) m=470;
            if (h<2) h = 2;
            if (h>19) h = 19;
            if (w<2) w=2;
            if (w>25) w=25;
            MineField.setNextMinesCount(m);
            MineField.setNextHeight(h);
            MineField.setNextWidth(w);
            new Game();
            dispose();
    }

    private void confirmChanges(){
        if (ownRules
                && !minesCountField.getText().isEmpty()
                && !heightField.getText().isEmpty()
                && !widthField.getText().isEmpty())
            acceptOwnRules(minesCountField.getText(), heightField.getText(), widthField.getText());
        else  {
            new Game();
            dispose();
        }

    }
}