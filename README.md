# Java Wordle Game

A desktop implementation of the popular word puzzle game Wordle, built using Java Swing. This version features a graphical user interface, leaderboard system, and all the core mechanics of the original game.

## Game Description

In this game, players attempt to guess a five-letter word within six attempts. After each guess, the game provides feedback through color-coded tiles:
* Green: Letter is correct and in the right position
* Yellow: Letter is in the word but in the wrong position
* Gray: Letter is not in the word

## Features

* Graphical user interface built with Java Swing
* Real-time color feedback for guesses
* Persistent leaderboard system
* Game statistics tracking
* High score system
* Menu bar with game options
* How-to-play guide
* Timer to track solving speed
* Option to replay after completing a puzzle

## Technical Requirements

* Java Development Kit (JDK) 8 or higher
* Minimum screen resolution: 800x600
* Operating System: Windows

## Project Structure

```
Wordle_Final/
├── src/
│   ├── WordleGame.java       # Main game logic and UI
│   ├── Constants.java        # Game constants and configurations
│   ├── Leaderboard.java      # Leaderboard implementation
├── assets/
│   └── Words.txt            # Dictionary of possible words
├── Score.txt                # Player scores storage
└── HighScore.dat           # High score storage
```

## Installation

1. Clone the repository:
```bash
git clone [repository-url]
```

2. Navigate to the project directory:
```bash
cd Wordle_Final
```

3. Compile the Java files:
```bash
javac WordleGame.java Constants.java Leaderboard.java
```

4. Run the game:
```bash
java WordleGame
```

## How to Play

1. Launch the game
2. Type a five-letter word guess in the input field
3. Press the OK button or Enter to submit your guess
4. Observe the color feedback:
   * Green tiles indicate correct letters in correct positions
   * Yellow tiles indicate correct letters in wrong positions
   * Gray tiles indicate letters not in the word
5. Continue guessing until you solve the word or run out of attempts
6. View your score and time upon completion

## Features in Detail

### Leaderboard System
* Tracks player names, number of attempts, and completion time
* Automatically sorts entries by performance
* Accessible through the Game menu

### High Score System
* Records best performances
* Saves scores persistently between sessions
* Updates automatically when records are broken

### Menu Options
* Game
  * Leaderboard
  * Exit
* Help
  * How-to-Play guide

## Contributing

Contributions are welcome! Please feel free to submit pull requests or create issues for bugs and feature requests.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

* Inspired by the original Wordle game by Josh Wardle
* Built using Java Swing for the graphical interface
* Uses a curated dictionary of five-letter English words
