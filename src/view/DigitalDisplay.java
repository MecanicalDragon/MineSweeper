package view;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DigitalDisplay extends JTextField {

    DigitalDisplay(int x, int y, int displayType){
        setBounds(x,y,60,35);
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setEditable(false);
        setBackground(Color.BLACK);
        setHorizontalAlignment(JTextField.RIGHT);

        try(InputStream is = new FileInputStream("src/resources/digital.ttf")){
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(Font.BOLD,32f);
            setFont(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        identifyReadingsType(displayType);

        setVisible(false);
    }

    private void identifyReadingsType(int displayType) {
        switch (displayType){
            case 1:
                setForeground(Color.CYAN);
                setToolTipText("time");
                break;
            case 2:
                setForeground(Color.RED);
                setToolTipText("mines");
                break;
            case 3:
                setForeground(Color.GREEN);
                setToolTipText("clicks");
                break;
        }
    }

}
