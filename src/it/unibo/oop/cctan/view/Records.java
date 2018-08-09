package it.unibo.oop.cctan.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.Date;

public class Records {

    private ArrayList<Triple<String, Integer, Date>> leaderBoard = new ArrayList<Triple<String, Integer, Date>>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    DecimalFormat df = new DecimalFormat("0,00");

    static final private String FILE_NAME = "./res//Scores";
    String path = new File(FILE_NAME).getAbsolutePath();

    public Records() {

        try {
            File file = new File(path);
            boolean fvar = file.createNewFile();
            if (fvar) {
                System.out.println("File has been created successfully");
            } else {
                System.out.println("File already present at the specified location");
            }
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            e.printStackTrace();
        }
        // inserimento in records
        try (final InputStream file2 = new FileInputStream(path);
                final InputStream bstream2 = new BufferedInputStream(file2);) {
            if (bstream2.available() >= 1) {
                final ObjectInputStream ostream2 = new ObjectInputStream(bstream2);
                @SuppressWarnings("unchecked")
                ArrayList<Triple<String, Integer, Date>> lr = (ArrayList<Triple<String, Integer, Date>>) ostream2
                        .readObject();
                leaderBoard.addAll(lr);
                System.out.println(this.toString());
            } else {
                leaderBoard.clear();
                System.out.println("il file era vuoto");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Triple<String, Integer, Date>> getRecordList() {
        ArrayList<Triple<String, Integer, Date>> list = new ArrayList<Triple<String, Integer, Date>>();
        list.addAll(leaderBoard);
        return list;
    }

    public int getBestScore(String player) {
        int best = 0;
        int i = 0;
        for (i = 0; i < leaderBoard.size(); i++) {
            if (leaderBoard.get(i).getElement0().equals(player) && leaderBoard.get(i).getElement1() > best) {
                best = leaderBoard.get(i).getElement1();
            }
        }
        return best;
    }

    public double getAvgScore(String player) {
        int sum = 0;
        int num = 0;
        int i = 0;
        double avg = 0;
        for (i = 0; i < leaderBoard.size(); i++) {
            if (leaderBoard.get(i).getElement0().equals(player)) {
                sum += leaderBoard.get(i).getElement1();
                num++;
            }
        }
        if (num == 0) {
            avg = 0;
        } else {
            avg = (double) sum / (double) num;
            avg = Double.valueOf(String.format("%1.2f", avg).replace(",", "."));
        }
        return avg;
    }

    public void orderRecordList() {
        leaderBoard.sort(new Comparator<Triple<String, Integer, Date>>() {
            @Override
            public int compare(Triple<String, Integer, Date> o1, Triple<String, Integer, Date> o2) {
                return o2.getElement1() - o1.getElement1();
            }
        });
        sobstisuteScores();
    }

    /**
     * A method that allow to add a score of a player if it is not already in the
     * leaderboard
     * 
     * @param p
     * @return true if the argument is not already in the leaderboard list
     */
    public boolean addWithNoDuplicate(Triple<String, Integer, Date> p) {
        if (isDuplicate(p)) {
            return false;
        } else {
            leaderBoard.add(p);
            sobstisuteScores();
            return true;
        }
    }

    private void sobstisuteScores() {
        try {
            File file = new File(path);
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed. non esiste già");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = new File(path);
            boolean fvar = file.createNewFile();
            if (fvar) {
                System.out.println("File has been created successfully");
            } else {
                System.out.println("File already present at the specified location");
            }
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            e.printStackTrace();
        }

        try (
                // ClassLoader classLoader = getClass().getClassLoader();
                // File file = new File(classLoader.getResource("Scores"));
                // new FileOutputStream(arg0)

                final OutputStream fileS = new FileOutputStream(path);
                final OutputStream bstream = new BufferedOutputStream(fileS);
                final ObjectOutputStream ostream = new ObjectOutputStream(bstream);) {
            // ostream . writeObject ( new java . util . Date () ); // Classe serializ .
            ostream.writeObject(leaderBoard);
        } catch (FileNotFoundException e) {
            System.out.println("aggiungere file Scores in res");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isDuplicate(Triple<String, Integer, Date> p) {
        int i = 0;
        for (i = 0; i < leaderBoard.size(); i++) {
            if (leaderBoard.get(i).getElement0().equals(p.getElement0())
                    && leaderBoard.get(i).getElement1().equals(p.getElement1())) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String s = "";
        int i = 0;
        for (i = 0; i < leaderBoard.size(); i++) {
            s = s + "[" + String.valueOf(leaderBoard.get(i).getElement0()) + ":"
                    + String.valueOf(leaderBoard.get(i).getElement1()) + ":"
                    + sdf.format(leaderBoard.get(i).getElement2()) + "]";
        }
        return s;
    }
}
