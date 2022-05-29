import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Leaderboard extends JFrame {
    public void sortFile(){

    }
    public Leaderboard(){
        setSize (400,400);
        setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        File scorefile= new File ("Score.txt");
        JTable jTable1;
        try {
            BufferedReader br = new BufferedReader(new FileReader (scorefile));
            String[] columnsName = {"Tries", "Time(seconds)"};
            jTable1 = new JTable ();
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel ();
            model.setColumnIdentifiers(columnsName);

            Object[] tableLines = br.lines().toArray();

            for(int i = 0; i < tableLines.length; i++)
            {
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split("/");
                model.addRow(dataRow);
            }
            JScrollPane jScrollPane = new JScrollPane (jTable1);
            add (jScrollPane);
            validate ();
        } catch (Exception ex) {
            ex.printStackTrace ();
        }
        setVisible(true);
    }
}
