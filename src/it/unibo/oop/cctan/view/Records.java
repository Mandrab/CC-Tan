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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.tuple.Triple;

import it.unibo.oop.cctan.interpackage_comunication.LoadedFilesSingleton;

/**
 * A class that handle the records of the game.
 * 
 * @author Sutera Lorenzo
 *
 */
public class Records {

    private final List<Triple<String, Integer, Date>> leaderBoard = new ArrayList<Triple<String, Integer, Date>>();
    private final String path;

    /**
     * The constructor of SizeObserverManager class.
     */
    public Records() {

        // path = new File(FILE_NAME).getAbsolutePath();
        path = LoadedFilesSingleton.getLoadedFiles().getScoresFile().get().getPath();

        // inserimento in records
        try (InputStream file2 = new FileInputStream(path); InputStream bstream2 = new BufferedInputStream(file2);) {
            if (bstream2.available() >= 1) {
                final ObjectInputStream ostream2 = new ObjectInputStream(bstream2);
                @SuppressWarnings("unchecked")
                final ArrayList<Triple<String, Integer, Date>> lr = (ArrayList<Triple<String, Integer, Date>>) ostream2
                        .readObject();
                leaderBoard.addAll(lr);
                ostream2.close();
            } else {
                leaderBoard.clear();
                System.out.println("the file was empty");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the list of Records.
     * 
     * @return a arrayList of a triplet composed of Name,Score,Date.
     */
    public List<Triple<String, Integer, Date>> getRecordList() {
        final List<Triple<String, Integer, Date>> list = new ArrayList<Triple<String, Integer, Date>>();
        list.addAll(leaderBoard);
        return list;
    }

    /**
     * A method that return the Best score of a player.
     * 
     * @param player
     *            that need to know his Best score.
     * @return the best score of the player.
     */
    public int getBestScore(final String player) {
        int best = 0;
        int i = 0;
        for (i = 0; i < leaderBoard.size(); i++) {
            if (leaderBoard.get(i).getLeft().equals(player) && leaderBoard.get(i).getMiddle() > best) {
                best = leaderBoard.get(i).getMiddle();
            }
        }
        return best;
    }

    /**
     * A method that return the average score of a player.
     * 
     * @param player
     *            that need to know his avarage score.
     * @return the average score of the player.
     */
    public double getAvgScore(final String player) {
        int sum = 0;
        int num = 0;
        int i = 0;
        double avg = 0;
        for (i = 0; i < leaderBoard.size(); i++) {
            if (leaderBoard.get(i).getLeft().equals(player)) {
                sum += leaderBoard.get(i).getMiddle();
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

    /**
     * A method that order the list by score first.
     */
    public void orderRecordList() {
        leaderBoard.sort(new Comparator<Triple<String, Integer, Date>>() {
            @Override
            public int compare(final Triple<String, Integer, Date> o1, final Triple<String, Integer, Date> o2) {
                return o2.getMiddle() - o1.getMiddle();
            }
        });
        sobstisuteScores();
    }

    /**
     * A method that allow to add a score of a player if it is not already in the
     * leaderboard.
     * 
     * @param p
     *            the triple that will be add if it is not a duplicate already in
     *            the record list.
     * @return true if the argument is not already in the leaderboard list.
     */
    public boolean addWithNoDuplicate(final Triple<String, Integer, Date> p) {
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
            final File file = new File(path);
            // final boolean fdel =
            file.delete();
            // if (file.delete()) {
            // //System.out.println(file.getName() + " is deleted!");
            // } else {
            // //System.out.println("Delete operation is failed. non esiste già");
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            final File file = new File(path);
            // final boolean fvar =
            file.createNewFile();
            // if (fvar) {
            // //System.out.println("File has been created successfully");
            // } else {
            // //System.out.println("File already present at the specified location");
            // }
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            e.printStackTrace();
        }

        try (OutputStream fileS = new FileOutputStream(path);
                OutputStream bstream = new BufferedOutputStream(fileS);
                ObjectOutputStream ostream = new ObjectOutputStream(bstream);) {
            ostream.writeObject(leaderBoard);
        } catch (FileNotFoundException e) {
            System.out.println("not existing file in res folder");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isDuplicate(final Triple<String, Integer, Date> p) {
        int i = 0;
        for (i = 0; i < leaderBoard.size(); i++) {
            if (leaderBoard.get(i).getLeft().equals(p.getLeft())
                    && leaderBoard.get(i).getMiddle().equals(p.getMiddle())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the list as a string.
     * 
     * @return A string composed of triple, each triplet is subdivided like this :
     *         [NICKNAME:SCORE:DATE].
     */
    public String toString() {
        String s = "";
        int i = 0;
        for (i = 0; i < leaderBoard.size(); i++) {
            s = s + "[" + leaderBoard.get(i).getLeft() + ":" + leaderBoard.get(i).getMiddle() + ":"
                    + new SimpleDateFormat("dd/M/yyyy", Locale.ITALIAN).format(leaderBoard.get(i).getRight()) + "]";
        }
        return s;
    }
}
