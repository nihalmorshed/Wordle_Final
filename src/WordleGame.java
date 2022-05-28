import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;

public class WordleGame extends JFrame implements ActionListener {
    private static Font normalFont = new Font ("Times New Roman", Font.BOLD, 25);
    private static Font textfieldfont = new Font ("Comic Sans MS", Font.PLAIN, 20);
    private static Font notificationfont = new Font ("Times New Roman", Font.BOLD, 15);
    private static Font notificationgreen = new Font ("Times New Roman", Font.BOLD, 13);
    private String highscore = "";

    public String getHighscore() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader ("HighScore.dat");
            bufferedReader = new BufferedReader (fileReader);
            return bufferedReader.readLine ();
        } catch (Exception e) {
            return "Nobody:7";
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
    }

    public void checkScore() {
        if (highscore.equals ("")) {
            return;
        }
        if (tries < Integer.parseInt (highscore.split (":")[1])) {
            String name = JOptionPane.showInputDialog ("You set a Highscore! Whats Your name?");
            highscore = name + ":" + (tries+1);
            File scoreFile = new File ("HighScore.dat");
            if (!scoreFile.exists ()) {
                try {
                    scoreFile.createNewFile ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            FileWriter writeFile = null;
            BufferedWriter writer = null;
            try {
                writeFile = new FileWriter (scoreFile);
                writer = new BufferedWriter (writeFile);
                writer.write (this.highscore);

            } catch (IOException e) {
                e.printStackTrace ();
            } finally {
                if (writer != null) {
                    try {
                        writer.close ();
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                }
            }
        }
    }

    class WordPanel extends JPanel {

        JLabel[] wordColumns = new JLabel[5];

        public WordPanel() {
            this.setLayout (new GridLayout (1, 5));
            Border blackBorder = BorderFactory.createLineBorder (Color.LIGHT_GRAY);
            for (int i = 0; i < 5; i++) {
                wordColumns[i] = new JLabel ();
                wordColumns[i].setHorizontalAlignment (JLabel.CENTER);
                wordColumns[i].setOpaque (true);
                wordColumns[i].setBorder (blackBorder);
                this.add (wordColumns[i]);
            }
        }

        public void clearWordPanel() {
            for (int i = 0; i < 5; i++) {
                wordColumns[i].setText ("");
            }
        }

        public void setPanelText(String charValue, int position, Color color) {
            this.wordColumns[position].setText (charValue);
            this.wordColumns[position].setBackground (color);
        }
    }

    class UserPanel extends JPanel {

        private JTextField userInput;
        private JButton okButton;


        public UserPanel() {
            this.setLayout (new GridLayout (1, 2));
            userInput = new JTextField ();
            userInput.setFont (textfieldfont);
            this.add (userInput);
            okButton = new JButton ("OK");
            this.add (okButton);
        }

        public JTextField getUserInput() {
            return userInput;
        }

        public JButton getOkButton() {
            return okButton;
        }

    }


    private JFrame gameFrame;
    private WordPanel[] wordPanelArray = new WordPanel[6];
    private UserPanel userPanel;
    private String wordleString;
    static long startTime;
    static int tries;
    private int count = 0;

    public WordleGame() {
        gameFrame = new JFrame (Constants.SCREEN_TITLE);
        gameFrame.setSize (Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gameFrame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setLayout (new GridLayout (7, 1));
        gameFrame.setLocationRelativeTo (null);

        JMenuBar menuBar = new JMenuBar ();
        JMenu GameMenu = new JMenu ("Game");
        JMenu HelpMenu = new JMenu ("Help");
        JMenuItem LeaderBoard = new JMenuItem ("LeaderBoard");
        JMenuItem exit = new JMenuItem ("Exit");
        JMenuItem howtoplay = new JMenuItem ("How-To-Play");
        menuBar.add (GameMenu);
        menuBar.add (HelpMenu);
        GameMenu.add (LeaderBoard);
        GameMenu.add (exit);
        HelpMenu.add (howtoplay);
        gameFrame.setJMenuBar (menuBar);
        gameFrame.setVisible (true);


        for (int i = 0; i < 6; i++) {
            wordPanelArray[i] = new WordPanel ();
            gameFrame.add (wordPanelArray[i]);
        }
        userPanel = new UserPanel ();
        userPanel.getOkButton ().addActionListener (this);
        gameFrame.add (userPanel);
        gameFrame.revalidate ();


        wordleString = getWordleString ();
        System.out.println ("Word for the day : " + wordleString);
        startTime = System.currentTimeMillis ();
        tries = 0;
        checkScore ();
        if (highscore.equals ("")) {
            highscore = getHighscore ();
        }
    }

    public static void main(String[] args) {
        new WordleGame ();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userWord = this.userPanel.getUserInput ().getText ();

        if (userWord.length () > 4) {
            if (isWordleWordEqualTo (userWord.trim ().toUpperCase ())) {
                clearAllPanels ();
                checkScore ();
                JOptionPane.showMessageDialog (null, "Current Highscore is " + highscore + ". You Win!! You Found The Answer in " + ((System.currentTimeMillis () - startTime) / 1000) + " seconds and " + (tries + 1) + " tries.", "Congrats", JOptionPane.INFORMATION_MESSAGE);
                gameFrame.dispose ();
                return;
            }
        }
        if (count > 4) {
            checkScore ();
            String option[]= {"Retry?"};
            JOptionPane.showOptionDialog (null, "The Answer Was: " + wordleString + ". Better luck next time!!", "Oops", JOptionPane.OK_OPTION,JOptionPane.INFORMATION_MESSAGE,null, option,0);
            gameFrame.setVisible (false);
            new WordleGame ();
            return;
        }
        count++;
        tries++;
        this.userPanel.userInput.setText ("");

    }

    private void clearAllPanels() {
        for (int i = 0; i <= count; i++) {
            wordPanelArray[i].clearWordPanel ();
        }
    }

    private boolean isWordleWordEqualTo(String userWord) {
        List<String> wordleWordsList = Arrays.asList (wordleString.split (""));
        String[] userWordsArray = userWord.split ("");
        List<Boolean> wordMatchesList = new ArrayList<> ();

        for (int i = 0; i < 5; i++) {
            if (wordleWordsList.contains (userWordsArray[i])) {
                if (wordleWordsList.get (i).equals (userWordsArray[i])) {
                    getActivePanel ().setPanelText (userWordsArray[i], i, Color.GREEN);
                    wordMatchesList.add (true);
                } else {
                    getActivePanel ().setPanelText (userWordsArray[i], i, Color.YELLOW);
                    wordMatchesList.add (false);
                }
            } else {
                getActivePanel ().setPanelText (userWordsArray[i], i, Color.GRAY);
                wordMatchesList.add (false);
            }
        }
        return !wordMatchesList.contains (false);
    }

    public WordPanel getActivePanel() {
        return this.wordPanelArray[count];
    }

    public String getWordleString() {
        Path path = Paths.get ("..\\\\Wordle_Final\\\\assets\\\\Words.txt");
        List<String> wordList = new ArrayList<> ();
        try {
            wordList = Files.readAllLines (path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        }
        Random random = new Random ();
        int position = random.nextInt (wordList.size ());
        return wordList.get (position).trim ().toUpperCase ();
    }

}
