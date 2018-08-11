package it.unibo.oop.cctan.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.commons.lang3.tuple.Triple;

public class LeaderBoardTable {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
    private static Records r;
    private static MenuWindow mainGui;

    public LeaderBoardTable(MenuWindow mg) {

            mainGui = mg;
            int i = 0;
            int s = 0;
            boolean show = true;

            //TODO
            String player = mg.getPlayerName();

            JFrame mainFrame = new JFrame("LeaderBoard");
            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            mainFrame.setMaximumSize(new Dimension(400, 400));
            mainFrame.setLayout(new BorderLayout());

            Container c = mainFrame.getContentPane();

            c.setLayout(new BorderLayout());

            r = new Records();
            r.orderRecordList();
            ArrayList<Triple<String, Integer, Date>> l = r.getRecordList();

            Vector<String> columnNames = new Vector<String>();
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();

            columnNames.add("PLAYER :");
            columnNames.add("SCORE :");
            columnNames.add("DATE :");

            if (l.size() >= 10) {

                    s = 10;
            } else if (l.size() == 0) {
                    show = false;
                    JOptionPane.showMessageDialog(new JFrame(), "No records are saved", "Notice", JOptionPane.ERROR_MESSAGE);
            } else {
                    s = l.size();
            }
            for (i = 0; i < s; i++) {
                    Vector<Object> vector = new Vector<Object>();
                    vector.add(l.get(i).getLeft());
                    vector.add(l.get(i).getMiddle());
                    vector.add(sdf.format(l.get(i).getRight()));
                    data.add(vector);
            }
            JTable tab = new JTable(data, columnNames) {
                    private static final long serialVersionUID = -7738867615106016804L;

                    // allow the table to not be editable
                    public boolean isCellEditable(int row, int column) {
                            return false;
                    }
            };
            tab.setGridColor(new Color(155));
            tab.setBackground(new Color(200, 250, 220));
            tab.getTableHeader().setBackground(new Color(150, 200, 180));

            c.add(tab.getTableHeader(), BorderLayout.PAGE_START);
            c.add(tab, BorderLayout.CENTER);

            JLabel stat = new JLabel(
                            "Your BEST score :" + r.getBestScore(player) + "|Your AVERAGE score :" + r.getAvgScore(player));
            c.add(stat, BorderLayout.PAGE_END);

            mainFrame.pack();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2,
                            dim.height / 2 - mainFrame.getSize().height / 2);
            mainFrame.setVisible(show);

            mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                            mainGui.removeLeaderBoard();
                            e.getWindow().dispose();
                            System.out.println("JFrame Closed!");
                    }
            });
    }
}

