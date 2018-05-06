package view;

import model.Game;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class HeroFrame extends JDialog {

    private JTextField heroName;

    public HeroFrame(JFrame owner) {
        super(owner,"Mother of God!", true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setBounds(view.MainFrame.getCurrentMainFrame().getLocation().x + 110,
                view.MainFrame.getCurrentMainFrame().getLocation().y + 140,
                328,125);
        setResizable(false);
        setLayout(null);
        addJLabels();
        addJTextField();

        JButton ok = new JButton("It's a Honor!");
        ok.setBounds(100, 55, 110, 25);
        ok.addActionListener(e -> confirm());
        add(ok);
        setVisible(true);
    }

    private void addJTextField() {
        heroName = new JTextField(10);
        heroName.grabFocus();
        heroName.setBounds(100, 30, 111, 25);
        heroName.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null) return;
                if ((getLength() + str.length()) <= 16) super.insertString(offset, str, attr);
            }
        });
        heroName.addActionListener(e -> confirm());
        heroName.setToolTipText("Put your name to the leaderboard, son. You deserved it.");
        add(heroName);
    }

    private void addJLabels() {
        JLabel leftLaurel= new JLabel(new ImageIcon(getClass().getResource("/leftLaurel.png")));
        leftLaurel.setBounds(0,0,80,80);
        add(leftLaurel);

        JLabel rightLaurel= new JLabel(new ImageIcon(getClass().getResource("/rightLaurel.png")));
        rightLaurel.setBounds(230,0,80,80);
        add(rightLaurel);

        JLabel text = new JLabel("You have surpassed our champions!");
        text.setBounds(52,5,220,20);
        add(text);
    }

    private void confirm() {
        if(!heroName.getText().trim().isEmpty()) {
            Game.getCurrentGame().setNewHero(heroName.getText().trim());
            dispose();
        }
    }

}
