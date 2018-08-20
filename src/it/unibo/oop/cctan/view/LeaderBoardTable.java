package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.commons.lang3.tuple.Triple;

/**
 * Class that comunicate whit scores file and instance the component used to show the leaderboard table  to the user.
 */
public class LeaderBoardTable {
    private final MenuWindow mainGui;
    private static final int BLUE_COLOR = 155;
    private static final int BACKGROUND_COLOR = 9890770;
    private static final int TABLEHEADER_COLOR = 6601120;

    /**
     * the constructor of the SettingsWindow class.
     * 
     *  @param mg
     *                  A reference to the MainMenu.
     */
    public LeaderBoardTable(final MenuWindow mg) {
            final Records r = Records.getInstance();
            mainGui = mg;
            int i = 0;
            int s = 0;
            boolean show = true;
            final String player = mg.getPlayerName();

            final JFrame mainFrame = new JFrame("LeaderBoard");
            mainFrame.setIconImage(mg.getIconImage());
            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            mainFrame.setLayout(new BorderLayout());

            final Container c = mainFrame.getContentPane();

            c.setLayout(new BorderLayout());

            r.orderRecordList();
            final List<Triple<String, Integer, Date>> l = r.getRecordList();



//            final Vector<Vector<Object>> data = new Vector<Vector<Object>>();

            final List<String> columnNames = new ArrayList<>();
            final List<Object[]> data = new ArrayList<>();
            columnNames.add("PLAYER :");
            columnNames.add("SCORE :");
            columnNames.add("DATE :");

            if (l.size() >= 10) {

                    s = 10;
            } else if (l.isEmpty()) {
                    show = false;
                    JOptionPane.showMessageDialog(new JFrame(), "No records are saved", "Notice", JOptionPane.ERROR_MESSAGE);
            } else {
                    s = l.size();
            }
            for (i = 0; i < s; i++) {
                   // final Vector<Object> vector = new Vector<Object>();
                   // vector.add(l.get(i).getLeft());
                   // vector.add(l.get(i).getMiddle());
                   // vector.add(new SimpleDateFormat("dd/M/yyyy", Locale.ITALIAN).format(l.get(i).getRight()));

                    final List<Object> vector = new ArrayList<>();
                    vector.add(l.get(i).getLeft());
                    vector.add(l.get(i).getMiddle());
                    vector.add(new SimpleDateFormat("dd/M/yyyy", Locale.ITALIAN).format(l.get(i).getRight()));
                    data.add(vector.toArray());
            }
           // data.stream().toArray(Object[][]::new);
            final JTable tab = new JTable(data.stream().toArray(Object[][]::new), columnNames.toArray());
            tab.setGridColor(new Color(BLUE_COLOR));
            tab.setBackground(new Color(BACKGROUND_COLOR));
            tab.getTableHeader().setBackground(new Color(TABLEHEADER_COLOR));


            c.add(tab.getTableHeader(), BorderLayout.PAGE_START);
            c.add(tab, BorderLayout.CENTER);

            final JLabel stat = new JLabel(
                            "Your BEST score :" + r.getBestScore(player) + "|Your AVERAGE score :" + r.getAvgScore(player));
            c.add(stat, BorderLayout.PAGE_END);

            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(show);

            mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(final java.awt.event.WindowEvent e) {
                            mainGui.removeLeaderBoard();
                            e.getWindow().dispose();
                    }
            });
    }
}

