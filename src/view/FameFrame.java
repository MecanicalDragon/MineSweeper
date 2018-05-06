package view;

import model.Game;
import model.HallOfFame;
import javax.swing.*;
import java.awt.*;

public class FameFrame extends JDialog {

    public FameFrame(JFrame owner){
        super(owner,"Hall of Fame", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(owner.getLocation().x+100,owner.getLocation().y+80);
        setSize(370,300);
        setResizable(false);

        addTabbedPane();

        JButton ok = new JButton("Go back and try to be like these heroes.");
        ok.addActionListener(e -> dispose());
        add(ok, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void addTab(JPanel field, int level) {

        GridBagLayout gbl = new GridBagLayout();
        field.setLayout(gbl);
        gbl.columnWidths = new int[]{30,100,50,150};
        gbl.rowHeights = new int[]{20,20,20,20,20,20,20,20,20,20};
        GridBagConstraints c =  new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        String[] names = HallOfFame.getInstance().getNames(level);
        int[] ranks = HallOfFame.getInstance().getRanks(level);
        int[] times = HallOfFame.getInstance().getTimes(level);

        for (int i = 0; i < 10; i++) {
            JLabel position = new JLabel(String.valueOf(i+1));
            gbl.setConstraints(position, c);
            field.add(position);
            c.gridx++;

            JLabel name = new JLabel(names[i]);
            name.setForeground(Color.BLUE);
            gbl.setConstraints(name, c);
            field.add(name);
            c.gridx++;

            JLabel time = new JLabel(String.valueOf(times[i]));
            time.setForeground(Color.RED);
            gbl.setConstraints(time, c);
            field.add(time);
            c.gridx++;

            JLabel rank = new JLabel(Game.getArmyRanks()[ranks[i]]);
            gbl.setConstraints(rank, c);
            field.add(rank);
            c.gridy++;
            c.gridx = 0;
        }

    }

    private void addTabbedPane() {
        JTabbedPane fields = new JTabbedPane();
        JPanel easy = new JPanel();
        addTab(easy, 1);
        JPanel medi = new JPanel();
        addTab(medi, 2);
        JPanel hard = new JPanel();
        addTab(hard, 3);
        fields.addTab("9x9 *10", easy);
        fields.addTab("16x16 *40", medi);
        fields.add("25x19 *99", hard);
        add(fields);

    }

}
