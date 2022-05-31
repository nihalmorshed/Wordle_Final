import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class player {
    String name;
    int attempts, time;

    public player(String name, int attempts, int time) {
        this.name = name;
        this.attempts = attempts;
        this.time = time;
    }
}

class nameCompare implements Comparator<player> {
    @Override
    public int compare(player s1, player s2) {
        return s1.name.compareTo (s2.name);
    }
}

class triesCompare implements Comparator<player> {
    @Override
    public int compare(player s1, player s2) {
        int dateComparison = Integer.compare (s1.attempts,s2.attempts);
        return dateComparison == 0 ? Integer.compare (s1.time,s2.time)  : dateComparison;
    }
}

class timeCompare implements Comparator<player> {
    @Override
    public int compare(player s1, player s2) {
        return s2.time - s1.time;
    }
}

public class Leaderboard extends JFrame {
    public void sortFile() {
        //Creating BufferedReader object to read the input text file

        BufferedReader reader = null;
        try {
            reader = new BufferedReader (new FileReader ("Score.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }

        //Creating ArrayList to hold Student objects

        ArrayList<player> playerRecords = new ArrayList<player> ();

        //Reading Player records one by one

        String currentLine = null;
        try {
            currentLine = reader.readLine ();
        } catch (IOException e) {
            e.printStackTrace ();
        }

        while (currentLine != null) {
            String[] playerDetail = currentLine.split ("/");

            String name = playerDetail[0];
            int attempts = Integer.valueOf (playerDetail[1]);
            int times = Integer.valueOf (playerDetail[2]);


            //Creating Student object for every student record and adding it to ArrayList

            playerRecords.add (new player (name, attempts, times));

            try {
                currentLine = reader.readLine ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }

        //Sorting ArrayList playerRecords based on marks



        Collections.sort (playerRecords, new triesCompare ());

        //Creating BufferedWriter object to write into output text file

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter (new FileWriter ("SortedScore.txt"));
        } catch (IOException e) {
            e.printStackTrace ();
        }

        //Writing every playerRecords into output text file

        for (player student : playerRecords) {
            try {
                writer.write (student.name);
                writer.write ("/" + student.attempts);
                writer.write ("/" + student.time);
                writer.newLine ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }

        //Closing the resources

        try {
            reader.close ();
            writer.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }

    }

    public Leaderboard() {
        setSize (Constants.LEADERBOARD_WIDTH, Constants.LEADERBOARD_HEIGHT);
        setTitle ("LeaderBoard");
        setLocationRelativeTo (null);
        setLayout (new FlowLayout ());
        setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        File scorefile = new File ("SortedScore.txt");
        JTable jTable1;
        try {
            sortFile ();
            BufferedReader br = new BufferedReader (new FileReader (scorefile));
            String[] columnsName = {"Name","Tries", "Time(seconds)"};
            jTable1 = new JTable ();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel ();
            model.setColumnIdentifiers (columnsName);

            Object[] tableLines = br.lines ().toArray ();

            for (int i = 0; i < tableLines.length; i++) {
                String line = tableLines[i].toString ().trim ();
                String[] dataRow = line.split ("/");
                model.addRow (dataRow);
            }
            jTable1.setPreferredScrollableViewportSize (new Dimension (350,80));
            jTable1.setFillsViewportHeight (true);
            JScrollPane jScrollPane = new JScrollPane (jTable1);
            add (jScrollPane);
            validate ();
        } catch (Exception ex) {
            ex.printStackTrace ();
        }
        setVisible (true);
    }
}


