package control;

import javax.swing.*;

public class MinedButton extends JButton {

    private int hoard;

    public void setHoard(int i){
        hoard = i;
    }

    public int getHoard() {
        return hoard;
    }

    public void incrementHoard() {
        hoard++;
    }

    public void superIncrementHoard() {
        hoard += 10;
    }

    public void superDecrement() {
        hoard -=10;
    }
}
