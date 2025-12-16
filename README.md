# WORDLE - Java Desktop Implementation

A fully-featured desktop clone of the popular word puzzle game Wordle, built entirely in Java using the Swing framework. This project demonstrates GUI programming, file I/O operations, event-driven architecture, and game state management.

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Complete File Structure](#complete-file-structure)
3. [Architecture Overview](#architecture-overview)
4. [Detailed Class Documentation](#detailed-class-documentation)
   - [WordleGame.java](#wordlegamejava---main-game-class)
   - [Leaderboard.java](#leaderboardjava---score-management)
   - [Constants.java](#constantsjava---configuration)
5. [GUI Components & Layout](#gui-components--layout)
6. [Game Mechanics](#game-mechanics)
7. [Data Storage & File Formats](#data-storage--file-formats)
8. [Color Feedback System](#color-feedback-system)
9. [Font Specifications](#font-specifications)
10. [Event Handling](#event-handling)
11. [Word Dictionary](#word-dictionary)
12. [Build & Deployment](#build--deployment)
13. [Modification Guide](#modification-guide)
14. [Technical Specifications](#technical-specifications)

---

## Project Overview

### What Was Built
- A complete Wordle game clone with graphical user interface
- Persistent high score and leaderboard system
- Timer-based performance tracking
- Replay functionality
- Menu-driven navigation with help documentation

### Technologies Used
- **Language**: Java 16 (compatible with JDK 8+)
- **GUI Framework**: Java Swing (javax.swing.*)
- **Graphics**: Java AWT (java.awt.*)
- **Data Storage**: Plain text files and binary files
- **Build Tool**: IntelliJ IDEA (can be compiled manually)

### Core Features Implemented
1. Random 5-letter word selection from a 500-word dictionary
2. 6 attempts to guess the correct word
3. Color-coded visual feedback (Green/Yellow/Gray)
4. Real-time game timer
5. Persistent score tracking across sessions
6. Sortable leaderboard with multiple criteria
7. High score system with player name recording
8. "How to Play" help dialog
9. Replay option after game completion

---

## Complete File Structure

```
Wordle_Final/
│
├── src/                              # SOURCE CODE DIRECTORY
│   ├── WordleGame.java               # Main game class (315 lines)
│   ├── Leaderboard.java              # Leaderboard system (160 lines)
│   └── Constants.java                # Game configuration (16 lines)
│
├── assets/                           # GAME RESOURCES
│   └── Words.txt                     # Dictionary: 500 five-letter words
│
├── out/                              # COMPILED OUTPUT
│   ├── production/Wordle_Final/      # Compiled .class files
│   │   ├── WordleGame.class
│   │   ├── WordleGame$1.class        # Anonymous ActionListener (Leaderboard menu)
│   │   ├── WordleGame$2.class        # Anonymous ActionListener (Exit menu)
│   │   ├── WordleGame$3.class        # Anonymous ActionListener (How-To-Play menu)
│   │   ├── WordleGame$UserPanel.class
│   │   ├── WordleGame$WordPanel.class
│   │   ├── Leaderboard.class
│   │   ├── Constants.class
│   │   ├── player.class              # Player data class
│   │   ├── nameCompare.class         # Name comparator
│   │   ├── triesCompare.class        # Attempts comparator
│   │   ├── timeCompare.class         # Time comparator
│   │   └── META-INF/MANIFEST.MF
│   └── artifacts/Wordle_Final_jar/   # JAR package contents
│
├── .idea/                            # INTELLIJ IDEA CONFIGURATION
│   ├── modules.xml
│   ├── misc.xml
│   ├── vcs.xml
│   ├── workspace.xml
│   └── artifacts/Wordle_Final_jar.xml
│
├── Score.txt                         # Player scores (append-only log)
├── SortedScore.txt                   # Sorted leaderboard data
├── HighScore.dat                     # Binary high score storage
├── Wordle_Final.jar                  # Executable JAR (16 KB)
├── Game.exe                          # Windows executable (82 KB)
├── icon.ico                          # Application icon (29 KB)
├── Wordle_Final.iml                  # IntelliJ module file
├── .gitattributes                    # Git configuration
└── README.md                         # This documentation file
```

---

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           WordleGame.java                                │
│                         (Main Application)                               │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ┌──────────────────┐    ┌──────────────────┐    ┌──────────────────┐   │
│  │    JFrame        │    │   JMenuBar       │    │   Constants      │   │
│  │  (gameFrame)     │    │                  │    │   (Config)       │   │
│  │  800x600 pixels  │    │  ├─ Game Menu    │    │                  │   │
│  │                  │    │  │  ├─LeaderBoard│    │  SCREEN_WIDTH    │   │
│  │  GridLayout      │    │  │  └─Exit       │    │  SCREEN_HEIGHT   │   │
│  │  (7 rows, 1 col) │    │  └─ Help Menu    │    │  SCREEN_TITLE    │   │
│  │                  │    │     └─How-To-Play│    │  HOWTOPLAY       │   │
│  └──────────────────┘    └──────────────────┘    └──────────────────┘   │
│                                                                          │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │                    WordPanel[6] Array                             │   │
│  │  ┌────────────────────────────────────────────────────────────┐  │   │
│  │  │ Row 0: WordPanel  ┌─────┬─────┬─────┬─────┬─────┐          │  │   │
│  │  │                   │ [0] │ [1] │ [2] │ [3] │ [4] │ JLabel   │  │   │
│  │  │                   └─────┴─────┴─────┴─────┴─────┘          │  │   │
│  │  ├────────────────────────────────────────────────────────────┤  │   │
│  │  │ Row 1: WordPanel  (Same structure as above)                │  │   │
│  │  ├────────────────────────────────────────────────────────────┤  │   │
│  │  │ Row 2: WordPanel  (Same structure as above)                │  │   │
│  │  ├────────────────────────────────────────────────────────────┤  │   │
│  │  │ Row 3: WordPanel  (Same structure as above)                │  │   │
│  │  ├────────────────────────────────────────────────────────────┤  │   │
│  │  │ Row 4: WordPanel  (Same structure as above)                │  │   │
│  │  ├────────────────────────────────────────────────────────────┤  │   │
│  │  │ Row 5: WordPanel  (Same structure as above)                │  │   │
│  │  └────────────────────────────────────────────────────────────┘  │   │
│  └──────────────────────────────────────────────────────────────────┘   │
│                                                                          │
│  ┌──────────────────────────────────────────────────────────────────┐   │
│  │ Row 6: UserPanel                                                  │   │
│  │  ┌─────────────────────────────────┬────────────────────────┐    │   │
│  │  │         JTextField              │       JButton          │    │   │
│  │  │        (userInput)              │        "OK"            │    │   │
│  │  │   Comic Sans MS, 20pt           │   ActionListener       │    │   │
│  │  └─────────────────────────────────┴────────────────────────┘    │   │
│  └──────────────────────────────────────────────────────────────────┘   │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    │ Opens on demand
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                          Leaderboard.java                                │
│                        (Score Display Window)                            │
├─────────────────────────────────────────────────────────────────────────┤
│  JFrame (400x250)                                                        │
│  ├── JScrollPane                                                         │
│  │   └── JTable                                                          │
│  │       ├── Column: "Name"                                              │
│  │       ├── Column: "Tries"                                             │
│  │       └── Column: "Time(seconds)"                                     │
│  │                                                                       │
│  └── Data Source: SortedScore.txt (regenerated on each open)            │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Detailed Class Documentation

### WordleGame.java - Main Game Class

**Location**: `src/WordleGame.java`
**Lines of Code**: 315
**Inheritance**: `extends JFrame implements ActionListener`

#### Package Imports

```java
import java.awt.*;                    // Graphics and layout management
import java.awt.event.ActionEvent;    // Event handling
import java.awt.event.ActionListener; // Button click listener interface
import java.io.*;                     // File I/O operations
import java.nio.file.Files;           // Modern file operations
import java.nio.file.Path;            // File path handling
import java.nio.file.Paths;           // Path creation
import java.util.ArrayList;           // Dynamic arrays
import java.util.Arrays;              // Array utilities
import java.util.List;                // List interface
import java.util.Random;              // Random number generation
import javax.swing.*;                 // GUI components
import javax.swing.border.Border;     // Border styling
```

#### Class-Level Variables (Fields)

| Variable | Type | Access | Purpose |
|----------|------|--------|---------|
| `normalFont` | `Font` | `private static` | Times New Roman, Bold, 25pt - Currently unused but available |
| `textfieldfont` | `Font` | `private static` | Comic Sans MS, Plain, 20pt - Input field font |
| `notificationfont` | `Font` | `private static` | Times New Roman, Bold, 15pt - Currently unused |
| `notificationgreen` | `Font` | `private static` | Times New Roman, Bold, 13pt - Currently unused |
| `highscore` | `String` | `private` | Stores "Name:Tries" format high score |
| `gameFrame` | `JFrame` | `private` | Main game window reference |
| `wordPanelArray` | `WordPanel[6]` | `private` | Array of 6 word display panels (one per attempt) |
| `userPanel` | `UserPanel` | `private` | Input field and OK button container |
| `wordleString` | `String` | `private` | The target word to guess (uppercase) |
| `startTime` | `long` | `static` | Game start timestamp (milliseconds) |
| `time` | `long` | `static` | Elapsed time in seconds |
| `name` | `String` | `static` | Player name after winning |
| `tries` | `int` | `static` | Number of attempts made |
| `count` | `int` | `private` | Current row index (0-5) |

#### Inner Class: WordPanel

**Purpose**: Displays a single row of 5 letter tiles

```java
class WordPanel extends JPanel {
    JLabel[] wordColumns = new JLabel[5];  // 5 letter positions

    // Constructor: Creates 5 JLabels in a horizontal grid
    public WordPanel() {
        this.setLayout(new GridLayout(1, 5));           // 1 row, 5 columns
        Border blackBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        for (int i = 0; i < 5; i++) {
            wordColumns[i] = new JLabel();
            wordColumns[i].setHorizontalAlignment(JLabel.CENTER);  // Center text
            wordColumns[i].setOpaque(true);                        // Enable background color
            wordColumns[i].setBorder(blackBorder);                 // Light gray border
            this.add(wordColumns[i]);
        }
    }

    // Clears all 5 labels (used when starting new game)
    public void clearWordPanel() {
        for (int i = 0; i < 5; i++) {
            wordColumns[i].setText("");
        }
    }

    // Sets a single letter with background color
    public void setPanelText(String charValue, int position, Color color) {
        this.wordColumns[position].setText(charValue);
        this.wordColumns[position].setBackground(color);
    }
}
```

**Key Design Decisions**:
- `setOpaque(true)` is required for `setBackground()` to work on JLabels
- `JLabel.CENTER` ensures letters are horizontally centered
- Light gray border provides subtle visual separation between tiles

#### Inner Class: UserPanel

**Purpose**: Contains the text input field and submit button

```java
class UserPanel extends JPanel {
    private JTextField userInput;  // Where player types guesses
    private JButton okButton;      // Submit button

    public UserPanel() {
        this.setLayout(new GridLayout(1, 2));  // Side by side layout
        userInput = new JTextField();
        userInput.setFont(textfieldfont);      // Comic Sans MS, 20pt
        this.add(userInput);
        okButton = new JButton("OK");
        this.add(okButton);
    }

    public JTextField getUserInput() { return userInput; }
    public JButton getOkButton() { return okButton; }
}
```

#### Constructor: WordleGame()

**Execution Flow**:

1. **Create Main Window**
   ```java
   gameFrame = new JFrame(Constants.SCREEN_TITLE);  // "WORDLE"
   gameFrame.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);  // 800x600
   gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   gameFrame.setLayout(new GridLayout(7, 1));  // 7 rows: 6 word rows + 1 input row
   gameFrame.setLocationRelativeTo(null);  // Center on screen
   ```

2. **Create Menu Bar**
   ```java
   JMenuBar menuBar = new JMenuBar();

   // Game Menu
   JMenu GameMenu = new JMenu("Game");
   JMenuItem LeaderBoard = new JMenuItem("LeaderBoard");
   JMenuItem exit = new JMenuItem("Exit");

   // Help Menu
   JMenu HelpMenu = new JMenu("Help");
   JMenuItem howtoplay = new JMenuItem("How-To-Play");
   ```

3. **Attach Event Listeners to Menu Items**
   - LeaderBoard: Opens new `Leaderboard()` instance
   - Exit: Calls `System.exit(0)`
   - How-To-Play: Opens a new JFrame with game rules

4. **Create Word Panel Grid**
   ```java
   for (int i = 0; i < 6; i++) {
       wordPanelArray[i] = new WordPanel();
       gameFrame.add(wordPanelArray[i]);
   }
   ```

5. **Create User Input Panel**
   ```java
   userPanel = new UserPanel();
   userPanel.getOkButton().addActionListener(this);  // 'this' = WordleGame
   gameFrame.add(userPanel);
   ```

6. **Initialize Game State**
   ```java
   wordleString = getWordleString();  // Load random word
   System.out.println("Word for the day : " + wordleString);  // Debug output
   startTime = System.currentTimeMillis();
   tries = 0;
   ```

#### Method: getHighscore()

**Purpose**: Reads the current high score from file

```java
public String getHighscore() {
    // Attempts to read HighScore.dat
    // Returns "Name:Tries" format string
    // Default fallback: "Nobody:7" (impossible to beat with 6 max attempts)
}
```

**File Location**: `HighScore.dat` (project root)
**Format**: `Name:Tries` (e.g., "Mohammed:3")

#### Method: checkScore()

**Purpose**: Checks if current game beats the high score

```java
public void checkScore() {
    if (highscore.equals("")) return;  // No score loaded yet

    if (tries < Integer.parseInt(highscore.split(":")[1])) {
        // Player beat the high score!
        String name = JOptionPane.showInputDialog("You set a Highscore! Whats Your name?");
        highscore = name + ":" + (tries + 1);  // Save new high score
        // Write to HighScore.dat
    }
}
```

**Trigger Points**:
- On game win
- On game loss (6th attempt exceeded)
- Note: Uses `tries + 1` because `tries` is 0-indexed

#### Method: actionPerformed(ActionEvent e)

**Purpose**: Main game logic - handles OK button clicks

**Flow**:
```
User clicks OK
     │
     ▼
Get input text
     │
     ▼
Is length > 4? ──No──► Increment count, clear input, continue
     │
    Yes
     │
     ▼
Check if guess matches target (isWordleWordEqualTo)
     │
     ├── Match Found ──► WIN!
     │                   - Clear panels
     │                   - Check high score
     │                   - Calculate time
     │                   - Ask for name
     │                   - Save score
     │                   - Offer replay
     │
     └── No Match
           │
           ▼
     Is count > 4? ──Yes──► GAME OVER
     │                      - Show answer
     │                      - Offer retry
     │
    No
     │
     ▼
Increment count and tries
Clear input field
Continue playing
```

**Key Code Section**:
```java
if (userWord.length() > 4) {
    if (isWordleWordEqualTo(userWord.trim().toUpperCase())) {
        // WIN CONDITION
        clearAllPanels();
        checkScore();
        time = ((System.currentTimeMillis() - startTime) / 1000);
        name = JOptionPane.showInputDialog(
            "Current Highscore is " + highscore +
            " You win!! You Found The Answer in " + time +
            " seconds and " + (tries + 1) + " tries. Whats Your name?"
        );
        setScore();  // Save to Score.txt

        int answer = JOptionPane.showConfirmDialog(
            null, "Want to play again?", "Replay?", JOptionPane.YES_NO_OPTION
        );
        if (answer == JOptionPane.YES_OPTION) {
            gameFrame.setVisible(false);
            new WordleGame();  // Start new game
            return;
        } else {
            gameFrame.dispose();
            return;
        }
    }
}

if (count > 4) {
    // LOSE CONDITION (6th attempt failed)
    checkScore();
    String option[] = {"Retry?"};
    JOptionPane.showOptionDialog(
        null,
        "The Answer Was: " + wordleString + ". Better luck next time!!",
        "Oops",
        JOptionPane.OK_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null, option, 0
    );
    gameFrame.setVisible(false);
    new WordleGame();  // Auto-restart
    return;
}

count++;
tries++;
this.userPanel.userInput.setText("");  // Clear for next guess
```

#### Method: setScore()

**Purpose**: Appends game result to Score.txt

```java
public void setScore() {
    FileWriter fw = new FileWriter(new File("Score.txt"), true);  // true = append mode
    fw.write(name + "/" + (tries + 1) + "/" + time + "\n");
    fw.close();
}
```

**Output Format**: `PlayerName/Attempts/TimeInSeconds`

#### Method: isWordleWordEqualTo(String userWord)

**Purpose**: Core word comparison with color feedback

**Algorithm**:
```
For each position i (0 to 4):
    IF letter exists in target word:
        IF letter is at correct position:
            Set tile GREEN
            Mark as correct
        ELSE:
            Set tile YELLOW
            Mark as incorrect
    ELSE:
        Set tile GRAY
        Mark as incorrect

Return TRUE if all positions marked correct
```

**Implementation**:
```java
private boolean isWordleWordEqualTo(String userWord) {
    List<String> wordleWordsList = Arrays.asList(wordleString.split(""));
    String[] userWordsArray = userWord.split("");
    List<Boolean> wordMatchesList = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
        if (wordleWordsList.contains(userWordsArray[i])) {
            if (wordleWordsList.get(i).equals(userWordsArray[i])) {
                getActivePanel().setPanelText(userWordsArray[i], i, Color.GREEN);
                wordMatchesList.add(true);
            } else {
                getActivePanel().setPanelText(userWordsArray[i], i, Color.YELLOW);
                wordMatchesList.add(false);
            }
        } else {
            getActivePanel().setPanelText(userWordsArray[i], i, Color.GRAY);
            wordMatchesList.add(false);
        }
    }
    return !wordMatchesList.contains(false);
}
```

**Important Note**: This implementation has a known quirk - it doesn't handle duplicate letters perfectly (e.g., if target is "APPLE" and guess is "PAPER", the 'P' handling may show unexpected colors).

#### Method: getWordleString()

**Purpose**: Loads a random word from the dictionary

```java
public String getWordleString() {
    Path path = Paths.get("..\\\\Wordle_Final\\\\assets\\\\Words.txt");
    List<String> wordList = Files.readAllLines(path);
    Random random = new Random();
    int position = random.nextInt(wordList.size());  // 0 to 499
    return wordList.get(position).trim().toUpperCase();
}
```

**Path Note**: Uses relative path `..\\Wordle_Final\\assets\\Words.txt` - may need adjustment based on working directory.

---

### Leaderboard.java - Score Management

**Location**: `src/Leaderboard.java`
**Lines of Code**: 160

#### Helper Class: player

**Purpose**: Data container for a single player's game result

```java
class player {
    String name;      // Player's name
    int attempts;     // Number of guesses used
    int time;         // Time in seconds

    public player(String name, int attempts, int time) {
        this.name = name;
        this.attempts = attempts;
        this.time = time;
    }
}
```

#### Comparator Class: nameCompare

**Purpose**: Sorts players alphabetically by name

```java
class nameCompare implements Comparator<player> {
    @Override
    public int compare(player s1, player s2) {
        return s1.name.compareTo(s2.name);
    }
}
```

#### Comparator Class: triesCompare (PRIMARY SORT)

**Purpose**: Sorts by attempts ascending, then by time ascending (fewer attempts = better, faster = better)

```java
class triesCompare implements Comparator<player> {
    @Override
    public int compare(player s1, player s2) {
        int dateComparison = Integer.compare(s1.attempts, s2.attempts);
        return dateComparison == 0 ? Integer.compare(s1.time, s2.time) : dateComparison;
    }
}
```

**Sorting Logic**:
1. First sort by number of attempts (ascending: 1 < 2 < 3...)
2. If attempts are equal, sort by time (ascending: faster is better)

#### Comparator Class: timeCompare

**Purpose**: Sorts by time descending (slower times first - currently unused in default behavior)

```java
class timeCompare implements Comparator<player> {
    @Override
    public int compare(player s1, player s2) {
        return s2.time - s1.time;  // Descending order
    }
}
```

#### Method: sortFile()

**Purpose**: Reads Score.txt, sorts data, writes to SortedScore.txt

**Process**:
1. Open Score.txt for reading
2. Parse each line: split by "/" to get name, attempts, time
3. Create player objects and add to ArrayList
4. Sort ArrayList using `triesCompare` comparator
5. Write sorted results to SortedScore.txt

#### Constructor: Leaderboard()

**Purpose**: Creates and displays the leaderboard window

```java
public Leaderboard() {
    setSize(Constants.LEADERBOARD_WIDTH, Constants.LEADERBOARD_HEIGHT);  // 400x250
    setTitle("LeaderBoard");
    setLocationRelativeTo(null);  // Center on screen
    setLayout(new FlowLayout());
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close only this window

    sortFile();  // Regenerate sorted data

    // Create JTable with columns
    String[] columnsName = {"Name", "Tries", "Time(seconds)"};
    JTable jTable1 = new JTable();
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setColumnIdentifiers(columnsName);

    // Read and display sorted scores
    BufferedReader br = new BufferedReader(new FileReader("SortedScore.txt"));
    Object[] tableLines = br.lines().toArray();
    for (int i = 0; i < tableLines.length; i++) {
        String line = tableLines[i].toString().trim();
        String[] dataRow = line.split("/");
        model.addRow(dataRow);
    }

    // Configure table appearance
    jTable1.setPreferredScrollableViewportSize(new Dimension(350, 80));
    jTable1.setFillsViewportHeight(true);
    JScrollPane jScrollPane = new JScrollPane(jTable1);
    add(jScrollPane);

    setVisible(true);
}
```

---

### Constants.java - Configuration

**Location**: `src/Constants.java`
**Lines of Code**: 16

```java
public class Constants {
    // Window Dimensions
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final int LEADERBOARD_WIDTH = 400;
    public static final int LEADERBOARD_HEIGHT = 250;

    // Window Title
    public static final String SCREEN_TITLE = "WORDLE";

    // Help Text
    public static final String HOWTOPLAY =
        "Player will get 6 attempts to guess a 5-letter word.\n\n" +
        "After Each guess, the background color of the letter turns:\n" +
        "'Yellow': If the letter is correct but is placed wrong in the word.\n" +
        "'Green': If the letter is correctly placed in the word.\n" +
        "'Grey': If the letter is incorrect.\n\n" +
        "The Player have to guess the correct word within given attempts in order to win.";
}
```

**To Change Game Dimensions**: Modify `SCREEN_WIDTH` and `SCREEN_HEIGHT`
**To Change Game Title**: Modify `SCREEN_TITLE`
**To Update Help Text**: Modify `HOWTOPLAY` string

---

## GUI Components & Layout

### Main Window Layout

```
┌────────────────────────────────────────────────────────────┐
│ Menu Bar: [Game ▼] [Help ▼]                                │
├────────────────────────────────────────────────────────────┤
│                                                            │
│  ┌──────┬──────┬──────┬──────┬──────┐  ← Row 0 (Attempt 1) │
│  │      │      │      │      │      │                      │
│  └──────┴──────┴──────┴──────┴──────┘                      │
│                                                            │
│  ┌──────┬──────┬──────┬──────┬──────┐  ← Row 1 (Attempt 2) │
│  │      │      │      │      │      │                      │
│  └──────┴──────┴──────┴──────┴──────┘                      │
│                                                            │
│  ┌──────┬──────┬──────┬──────┬──────┐  ← Row 2 (Attempt 3) │
│  │      │      │      │      │      │                      │
│  └──────┴──────┴──────┴──────┴──────┘                      │
│                                                            │
│  ┌──────┬──────┬──────┬──────┬──────┐  ← Row 3 (Attempt 4) │
│  │      │      │      │      │      │                      │
│  └──────┴──────┴──────┴──────┴──────┘                      │
│                                                            │
│  ┌──────┬──────┬──────┬──────┬──────┐  ← Row 4 (Attempt 5) │
│  │      │      │      │      │      │                      │
│  └──────┴──────┴──────┴──────┴──────┘                      │
│                                                            │
│  ┌──────┬──────┬──────┬──────┬──────┐  ← Row 5 (Attempt 6) │
│  │      │      │      │      │      │                      │
│  └──────┴──────┴──────┴──────┴──────┘                      │
│                                                            │
│  ┌─────────────────────────┬────────┐  ← Row 6 (Input)     │
│  │ Type your guess here... │   OK   │                      │
│  └─────────────────────────┴────────┘                      │
│                                                            │
└────────────────────────────────────────────────────────────┘
         Width: 800px            Height: 600px
```

### Leaderboard Window Layout

```
┌──────────────────────────────────────┐
│            LeaderBoard               │
├──────────────────────────────────────┤
│ ┌──────────────────────────────────┐ │
│ │ Name    │ Tries │ Time(seconds)  │ │
│ ├─────────┼───────┼────────────────┤ │
│ │ Mohammed│   3   │      108       │ │
│ │ Sayeed  │   4   │      232       │ │
│ │ Morshed │   5   │      163       │ │
│ │ Nihal   │   6   │      246       │ │
│ │ Habib   │   6   │      306       │ │
│ └──────────────────────────────────┘ │
│              (Scrollable)            │
└──────────────────────────────────────┘
    Width: 400px     Height: 250px
```

### How-To-Play Dialog Layout

```
┌────────────────────────────────────────────────────────────┐
│                    How To Play                             │
├────────────────────────────────────────────────────────────┤
│                                                            │
│  Player will get 6 attempts to guess a 5-letter word.      │
│                                                            │
│  After Each guess, the background color of the letter      │
│  turns:                                                    │
│  'Yellow': If the letter is correct but is placed wrong    │
│            in the word.                                    │
│  'Green': If the letter is correctly placed in the word.   │
│  'Grey': If the letter is incorrect.                       │
│                                                            │
│  The Player have to guess the correct word within given    │
│  attempts in order to win.                                 │
│                                                            │
└────────────────────────────────────────────────────────────┘
       Font: Verdana, Bold, 15pt
       Window: Auto-sized (pack())
```

---

## Game Mechanics

### Game Flow State Machine

```
                    ┌─────────────────────┐
                    │      START          │
                    │  (new WordleGame()) │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │  Load Random Word   │
                    │  Start Timer        │
                    │  Initialize tries=0 │
                    └──────────┬──────────┘
                               │
                               ▼
          ┌───────────────────────────────────────┐
          │         WAITING FOR INPUT             │◄─────────┐
          │         (count = current row)         │          │
          └───────────────────┬───────────────────┘          │
                              │                              │
                              │ User clicks OK               │
                              ▼                              │
                    ┌─────────────────────┐                  │
                    │  Validate Input     │                  │
                    │  (length > 4?)      │                  │
                    └──────────┬──────────┘                  │
                               │                              │
              ┌────────────────┼────────────────┐            │
              │                │                │            │
       length ≤ 4        length > 4       length > 4        │
       (ignored)         (no match)       (MATCH!)          │
              │                │                │            │
              │                ▼                ▼            │
              │    ┌─────────────────┐  ┌─────────────────┐  │
              │    │ Display colors  │  │     YOU WIN!    │  │
              │    │ Increment count │  │ Calculate time  │  │
              │    │ Increment tries │  │ Get player name │  │
              │    │ Clear input     │  │ Save score      │  │
              │    └────────┬────────┘  └────────┬────────┘  │
              │             │                    │           │
              │             │                    ▼           │
              │             │           ┌─────────────────┐  │
              │             │           │  Play Again?    │  │
              │             │           └────────┬────────┘  │
              │             │                    │           │
              │             │         ┌──────────┴──────────┐│
              │             │         │                     ││
              │             │        YES                   NO│
              │             │         │                     ││
              │             │         ▼                     ▼│
              │             │  ┌─────────────┐    ┌──────────┴┐
              │             │  │ NEW GAME    │    │   EXIT    │
              │             │  └─────────────┘    └───────────┘
              │             │
              │             ▼
              │    ┌─────────────────┐
              │    │  count > 4?     │
              │    │  (6th attempt)  │
              │    └────────┬────────┘
              │             │
              │      ┌──────┴──────┐
              │      │             │
              │     NO            YES
              │      │             │
              │      │             ▼
              │      │    ┌─────────────────┐
              │      │    │   GAME OVER     │
              │      │    │ Show answer     │
              │      │    │ Auto-restart    │
              │      │    └─────────────────┘
              │      │
              └──────┴────────────────────────────────────────┘
                              (Loop back)
```

### Word Validation Process

```
Input: "CRANE"    Target: "GRAPE"

Position 0: C
├─ Is 'C' in "GRAPE"? NO → GRAY

Position 1: R
├─ Is 'R' in "GRAPE"? YES
└─ Is 'R' at position 1 in "GRAPE"? YES → GREEN

Position 2: A
├─ Is 'A' in "GRAPE"? YES
└─ Is 'A' at position 2 in "GRAPE"? YES → GREEN

Position 3: N
├─ Is 'N' in "GRAPE"? NO → GRAY

Position 4: E
├─ Is 'E' in "GRAPE"? YES
└─ Is 'E' at position 4 in "GRAPE"? YES → GREEN

Result: [GRAY][GREEN][GREEN][GRAY][GREEN]
```

---

## Data Storage & File Formats

### Score.txt (Append-Only Game Log)

**Location**: Project root
**Format**: `PlayerName/Attempts/TimeInSeconds`
**Mode**: Append (new entries added to end)

**Example Contents**:
```
Nihal/6/246
Morshed/5/163
Mohammed/3/108
Habib/6/306
Sayeed/4/232
```

**Fields**:
| Field | Description | Example |
|-------|-------------|---------|
| PlayerName | Name entered after winning | "Mohammed" |
| Attempts | Number of guesses used (1-6) | "3" |
| TimeInSeconds | Seconds from game start to win | "108" |

### SortedScore.txt (Generated Leaderboard)

**Location**: Project root
**Format**: Same as Score.txt
**Mode**: Overwritten each time leaderboard opens

**Sorting Algorithm**:
1. Primary: Attempts (ascending) - fewer = better
2. Secondary: Time (ascending) - faster = better

**Example Contents**:
```
Mohammed/3/108    ← Best: 3 attempts in 108 seconds
Sayeed/4/232
Morshed/5/163
Nihal/6/246
Habib/6/306       ← Worst: 6 attempts in 306 seconds
```

### HighScore.dat (Binary High Score)

**Location**: Project root
**Format**: `PlayerName:Attempts`
**Mode**: Overwritten when beaten

**Example Content**: `Mohammed:3`

**Default Value** (if file doesn't exist): `Nobody:7`

**Note**: The value "7" is intentional - since maximum attempts is 6, any win will beat the default.

---

## Color Feedback System

### Color Definitions

| Color | Java Constant | RGB Value | Meaning |
|-------|---------------|-----------|---------|
| Green | `Color.GREEN` | (0, 255, 0) | Correct letter, correct position |
| Yellow | `Color.YELLOW` | (255, 255, 0) | Correct letter, wrong position |
| Gray | `Color.GRAY` | (128, 128, 128) | Letter not in word |
| Light Gray | `Color.LIGHT_GRAY` | (192, 192, 192) | Tile border color |

### Visual Example

```
Target Word: CRANE
Guess:       REACT

R → YELLOW (exists in CRANE, but not at position 0)
E → YELLOW (exists in CRANE, but not at position 1)
A → YELLOW (exists in CRANE, but not at position 2)
C → YELLOW (exists in CRANE, but not at position 3)
T → GRAY   (does not exist in CRANE)

Display:
┌────────┬────────┬────────┬────────┬────────┐
│   R    │   E    │   A    │   C    │   T    │
│ YELLOW │ YELLOW │ YELLOW │ YELLOW │  GRAY  │
└────────┴────────┴────────┴────────┴────────┘
```

---

## Font Specifications

### Defined Fonts (in WordleGame.java)

| Variable | Font Family | Style | Size | Used For |
|----------|-------------|-------|------|----------|
| `normalFont` | Times New Roman | Bold | 25pt | Reserved (unused) |
| `textfieldfont` | Comic Sans MS | Plain | 20pt | Input text field |
| `notificationfont` | Times New Roman | Bold | 15pt | Reserved (unused) |
| `notificationgreen` | Times New Roman | Bold | 13pt | Reserved (unused) |

### Other Fonts Used

| Location | Font Family | Style | Size |
|----------|-------------|-------|------|
| How-To-Play text | Verdana | Bold | 15pt |
| Letter tiles | Default (Dialog) | Default | Default |

---

## Event Handling

### ActionListener Implementations

#### 1. OK Button (Main Game)

**Registered In**: `WordleGame` constructor
**Handler**: `WordleGame.actionPerformed(ActionEvent e)`

```java
userPanel.getOkButton().addActionListener(this);  // 'this' implements ActionListener
```

#### 2. LeaderBoard Menu Item

**Registered In**: `WordleGame` constructor
**Handler**: Anonymous inner class

```java
LeaderBoard.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        new Leaderboard();  // Opens leaderboard window
    }
});
```

#### 3. Exit Menu Item

**Registered In**: `WordleGame` constructor
**Handler**: Anonymous inner class

```java
exit.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);  // Terminates application
    }
});
```

#### 4. How-To-Play Menu Item

**Registered In**: `WordleGame` constructor
**Handler**: Anonymous inner class

```java
howtoplay.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame leaderboardframe = new JFrame();
        leaderboardframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leaderboardframe.setTitle("How To Play");
        leaderboardframe.setLayout(new FlowLayout());

        JTextArea jTextField = new JTextArea();
        jTextField.setLineWrap(true);
        jTextField.setText(Constants.HOWTOPLAY);
        jTextField.setFont(new Font("Verdana", 1, 15));
        jTextField.setPreferredSize(new Dimension(800, 200));

        leaderboardframe.add(jTextField);
        leaderboardframe.setSize(600, 600);
        leaderboardframe.setLocationRelativeTo(null);
        leaderboardframe.pack();
        leaderboardframe.setVisible(true);
    }
});
```

---

## Word Dictionary

### File Details

**Location**: `assets/Words.txt`
**Total Words**: 500 (499 words visible + 1 blank line)
**Word Length**: All 5 letters
**Format**: One word per line, lowercase (converted to uppercase during loading)

### Sample Words (First 50)

```
about, above, abuse, actor, acute, admit, adopt, adult, after, again,
agent, agree, ahead, alarm, album, alert, alike, alive, allow, alone,
along, alter, among, anger, Angle, angry, apart, apple, apply, arena,
argue, arise, array, aside, asset, audio, audit, avoid, award, aware,
badly, baker, bases, basic, basis, beach, began, begin, begun, being...
```

### Word Categories Present

- Common English words (about, after, again)
- Names (Billy, Harry, Jimmy, Henry, Terry, Peter, Maria, Frank, Roger, Robin, Lewis, Jones, Smith)
- Places (Japan, China, Texas, Calif)
- Abstract concepts (faith, truth, grace, peace)
- Technical terms (array, index, input, logic)

### Loading Mechanism

```java
Path path = Paths.get("..\\\\Wordle_Final\\\\assets\\\\Words.txt");
List<String> wordList = Files.readAllLines(path);  // Loads all 500 words into memory
Random random = new Random();
int position = random.nextInt(wordList.size());    // Random index 0-499
return wordList.get(position).trim().toUpperCase();
```

---

## Build & Deployment

### Compiling from Source

```bash
# Navigate to src directory
cd Wordle_Final/src

# Compile all Java files
javac WordleGame.java Leaderboard.java Constants.java

# Run the game
java WordleGame
```

### Creating JAR File (IntelliJ IDEA)

1. Go to **File → Project Structure → Artifacts**
2. Click **+ → JAR → From modules with dependencies**
3. Select `WordleGame` as the main class
4. Build: **Build → Build Artifacts → Build**

### Manifest File (META-INF/MANIFEST.MF)

```
Manifest-Version: 1.0
Main-Class: WordleGame
```

### Running the JAR

```bash
java -jar Wordle_Final.jar
```

### Windows Executable

The `Game.exe` file is a wrapper for the JAR file, created using tools like Launch4j or similar Java-to-EXE converters.

---

## Modification Guide

### How to Change the Window Size

**File**: `src/Constants.java`

```java
public static final int SCREEN_WIDTH = 800;   // Change to desired width
public static final int SCREEN_HEIGHT = 600;  // Change to desired height
```

### How to Change the Game Title

**File**: `src/Constants.java`

```java
public static final String SCREEN_TITLE = "WORDLE";  // Change to your title
```

### How to Change the Number of Attempts

**Files to modify**:

1. `src/WordleGame.java` - Line 133:
   ```java
   private WordPanel[] wordPanelArray = new WordPanel[6];  // Change 6 to desired attempts
   ```

2. `src/WordleGame.java` - Line 146:
   ```java
   gameFrame.setLayout(new GridLayout(7, 1));  // Change 7 to (attempts + 1)
   ```

3. `src/WordleGame.java` - Line 197:
   ```java
   for (int i = 0; i < 6; i++) {  // Change 6 to desired attempts
   ```

4. `src/WordleGame.java` - Line 245:
   ```java
   if (count > 4) {  // Change 4 to (desired attempts - 2)
   ```

### How to Add More Words to Dictionary

**File**: `assets/Words.txt`

- Add new 5-letter words, one per line
- Words can be lowercase (automatically converted to uppercase)
- No special characters or numbers

### How to Change Input Font

**File**: `src/WordleGame.java` - Line 18:

```java
private static Font textfieldfont = new Font("Comic Sans MS", Font.PLAIN, 20);
// Change "Comic Sans MS" to desired font
// Change Font.PLAIN to Font.BOLD or Font.ITALIC
// Change 20 to desired size
```

### How to Change Tile Colors

**File**: `src/WordleGame.java` - `isWordleWordEqualTo()` method:

```java
// Correct position
getActivePanel().setPanelText(userWordsArray[i], i, Color.GREEN);

// Wrong position
getActivePanel().setPanelText(userWordsArray[i], i, Color.YELLOW);

// Not in word
getActivePanel().setPanelText(userWordsArray[i], i, Color.GRAY);
```

Replace `Color.GREEN`, `Color.YELLOW`, `Color.GRAY` with:
- `Color.RED`, `Color.BLUE`, `Color.CYAN`, `Color.MAGENTA`, `Color.ORANGE`, `Color.PINK`
- Or custom colors: `new Color(r, g, b)` where r, g, b are 0-255

### How to Change the Help Text

**File**: `src/Constants.java`

```java
public static final String HOWTOPLAY = "Your new help text here...";
```

### How to Modify Leaderboard Sorting

**File**: `src/Leaderboard.java` - Line 89:

```java
Collections.sort(playerRecords, new triesCompare());
// Change to:
// new nameCompare() - sort alphabetically
// new timeCompare() - sort by time (descending)
// new triesCompare() - sort by attempts then time (default)
```

---

## Technical Specifications

### System Requirements

| Requirement | Minimum |
|-------------|---------|
| Java Version | JDK 8 or higher (built with JDK 16) |
| Screen Resolution | 800x600 pixels |
| Operating System | Windows (for .exe), Cross-platform (for .jar) |
| Memory | ~50 MB (JVM overhead) |
| Disk Space | ~1 MB |

### Dependencies

- **No external libraries required**
- Uses only standard Java SE libraries:
  - `java.awt.*`
  - `javax.swing.*`
  - `java.io.*`
  - `java.nio.file.*`
  - `java.util.*`

### File Paths

| File | Path Type | Notes |
|------|-----------|-------|
| Words.txt | Relative | `..\\Wordle_Final\\assets\\Words.txt` |
| Score.txt | Relative | Working directory |
| SortedScore.txt | Relative | Working directory |
| HighScore.dat | Relative | Working directory |

### Known Limitations

1. **Duplicate Letter Handling**: The color feedback algorithm doesn't perfectly handle words with duplicate letters. For example, if the target is "APPLE" and guess is "PAPER", both 'P' letters may show yellow even though only one should.

2. **Path Dependency**: The word dictionary path is hardcoded as a relative path. If running from a different directory, the game may fail to load words.

3. **No Input Validation for Non-Letters**: The game accepts any 5+ character input, including numbers and special characters.

4. **No Enter Key Support**: Users must click the OK button; pressing Enter does not submit the guess.

5. **Single Player Only**: No multiplayer or networked gameplay.

---

## Development Tasks Completed

1. **Core Game Logic**
   - Random word selection from dictionary
   - Word comparison algorithm
   - Color feedback system (Green/Yellow/Gray)
   - Attempt tracking (max 6 tries)
   - Win/Lose condition detection

2. **Graphical User Interface**
   - Main game window (800x600)
   - 6-row word display grid
   - Input text field with submit button
   - Menu bar with Game and Help menus
   - Leaderboard window with sortable table
   - How-To-Play dialog

3. **Data Persistence**
   - Score logging to Score.txt
   - Sorted leaderboard generation
   - High score tracking in binary format
   - Automatic data sorting

4. **User Experience**
   - Replay functionality after game end
   - Timer for tracking solve speed
   - Name entry dialogs
   - Informative win/lose messages

5. **Code Organization**
   - Separation of concerns (Constants, Leaderboard, Main)
   - Inner classes for UI components
   - Comparator classes for flexible sorting

---

## Author

**Nihal Morshed**

---

## License

This project was developed for educational purposes as a Java programming exercise.

---

*Last Updated: December 2025*
