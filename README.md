# Wordle Word Generator

A command-line tool that generates possible Wordle solutions based on game feedback, helping players make informed guesses by filtering a dictionary against known constraints.

## Overview

This utility solves the common Wordle problem: "I know the word contains 'A', doesn't contain 'E', and has 'R' as the third letter - what are my options?" The generator processes multiple hint types simultaneously and returns all valid 5-letter words matching the constraints.

## Features

- **Multiple Constraint Types**: Supports three distinct hint patterns from Wordle gameplay
- **Interactive CLI**: Simple, intuitive command-line interface for entering hints
- **Efficient Filtering**: Processes entire dictionary against all rules in a single pass
- **Repeatable Sessions**: Generate multiple word lists without restarting the program
- **Dictionary-Based**: Works with any text file containing newline-separated words

## How It Works

The generator accepts three types of hints based on Wordle's color feedback system:

1. **Contains Letter (cl)** - Green/Yellow tiles: Word must include this letter somewhere
2. **Doesn't Contain Letter (dcl)** - Gray tiles: Word must not include this letter
3. **Letter at Location (ll)** - Green tiles: Specific letter at specific position

Words are validated against all provided constraints, with only 5-letter words that satisfy every rule being returned.

## Usage

### Running the Program

```bash
javac WordleWordGenerator.java
java WordleWordGenerator
```

### Demo



### Command Reference

| Command | Description | Example |
|---------|-------------|---------|
| `cl` | Contains Letter | Word includes 'a' somewhere |
| `dcl` | Doesn't Contain Letter | Word has no 'e' |
| `ll` | Letter at Location | Word has 'r' at position 3 |
| `d` | Done entering hints | Start generating results |

**Note**: Positions are 1-indexed (first letter is position 1, not 0)

## Implementation Details

### Architecture

**Rule-Based Filtering System:**
- `Rule` inner class encapsulates constraint data (type, target letter, optional position)
- Three validation methods check individual constraints
- Main loop applies all rules to each dictionary word

**Key Design Decisions:**
- Early termination: Stops checking rules once a word fails any constraint
- Single-pass filtering: Reads dictionary once per generation session
- ArrayList-based rule storage: Supports arbitrary number of constraints

### Algorithm

```
For each word in dictionary:
    For each rule in rules:
        If word violates rule:
            Skip to next word
    If word passes all rules AND is 5 letters:
        Output word
```

**Time Complexity**: O(n × m × k) where:
- n = number of words in dictionary
- m = number of rules
- k = average word length (5 for Wordle)


## Requirements

- Java 8 or higher
- `dictionary.txt` file in the same directory as the compiled class
- Dictionary should contain one word per line


## Technical Highlights

- **Inner Class Pattern**: `Rule` class encapsulates constraint logic cleanly
- **Polymorphic Rules**: Same `Rule` class handles multiple constraint types via optional `location` field
- **Stream Processing**: Sequential file reading with immediate filtering
- **User Experience**: Clear prompts and continuous operation without restarts

## Future Enhancements

**Potential Improvements:**
- Add support for yellow tile constraints ("contains 'a' but not at position 2")
- Implement word frequency ranking to prioritize common words
- Add dictionary validation and error handling for missing files
- Support multiple dictionaries (common words vs. full dictionary)
- GUI version with visual feedback similar to actual Wordle interface
- Statistical analysis of remaining possibilities

## Learning Outcomes

This project demonstrates:
- File I/O operations in Java
- ArrayList usage for dynamic rule collection
- Switch-case statement patterns for command parsing
- Inner class design for encapsulating related data
- Early termination optimization in filtering algorithms
- Interactive CLI development with input validation

---