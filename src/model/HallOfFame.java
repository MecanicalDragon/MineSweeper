package model;

import view.MainFrame;
import javax.swing.*;
import java.io.*;

public class HallOfFame implements Serializable{

    private static File hallOfFame;
    private static HallOfFame instance;

    private String[] easyNames = new String[]{"Bison", "Kurtz", "Payne", "Price", "Morales",
            "Schrodinger", "Dornan", "Hartman", "Ackerman", "Gump"};
    private String[] mediNames = new String[]{"Bison", "Kurtz", "Payne", "Price", "Morales",
            "Schrodinger", "Dornan", "Hartman", "Ackerman", "Gump"};
    private String[] hardNames = new String[]{"Bison", "Kurtz", "Payne", "Price", "Morales",
            "Schrodinger", "Dornan", "Hartman", "Ackerman", "Gump"};

    private int[] easyRanks = new int[]{24,19,17,16,14,10,7,4,3,2};
    private int[] mediRanks = new int[]{24,19,17,16,14,10,7,4,3,2};
    private int[] hardRanks = new int[]{24,19,17,16,14,10,7,4,3,2};

    private int[] easyTimes = new int[]{8,9,10,11,12,14,16,18,20,25};
    private int[] mediTimes = new int[]{60,65,70,75,80,85,90,100,110,120};
    private int[] hardTimes = new int[]{250,300,350,400,450,500,550,600,650,700};

    private HallOfFame() {
    }

    private static void loadInstance() {
        hallOfFame = new File(hallOfFame + "\\HallOfFame.dat");
        if (!hallOfFame.exists()){
            instance = new HallOfFame();
            saveHallOfFame();
        }
        else{
            loadHallOfFame();
        }
    }

    private static void loadHallOfFame() {
        try(ObjectInputStream os = new ObjectInputStream(new FileInputStream(hallOfFame))){
            instance = (HallOfFame)os.readObject();
        } catch (ClassNotFoundException | IOException e) {
            instance = new HallOfFame();
            JOptionPane.showMessageDialog(MainFrame.getCurrentMainFrame(),
                    "Can't use that Hall Of Fame.\n "+
                            "We have built another one.");
        }
    }

    public static void saveHallOfFame() {
        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(hallOfFame))){
            os.writeObject(instance);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(MainFrame.getCurrentMainFrame(),
                    "Couldn't build or update Hall Of Fame on your computer.\n " +
                            "We don't know, why.\n Try again later.");
        }
    }

    private static void initDisk(){
        char disk = 'A';
        File location7 = new File(":\\Users\\Public\\Documents");
        File locationXP = new File(":\\Documents and Settings\\Public\\My Documents");
        File temp7;
        File tempXP;
        for (int i = 0; i < 26;  i++, disk++) {
            temp7 = new File(String.valueOf(disk).concat(location7.toString()));
            tempXP = new File(String.valueOf(disk).concat(locationXP.toString()));
            if (temp7.exists()) {
                hallOfFame = temp7;
                break;
            } else if (tempXP.exists()) {
                hallOfFame = tempXP;
                break;
            }
        }
        if  (hallOfFame == null) {
            hallOfFame = new File("C:");
        }
    }

    public static HallOfFame getInstance(){
        if (instance == null){
            initDisk();
            loadInstance();
        }
        return instance;
    }

    public String[] getNames(int level) {
        switch (level){
            case 2:
                return mediNames;
            case 3:
                return hardNames;
            default:
                return easyNames;
        }
    }

    public int[] getRanks(int level) {
        switch (level){
            case 2:
                return mediRanks;
            case 3:
                return hardRanks;
            default:
                return easyRanks;
        }
    }

    public int[] getTimes(int level) {
        switch (level){
            case 2:
                return mediTimes;
            case 3:
                return hardTimes;
            default:
                return easyTimes;
        }
    }

}
