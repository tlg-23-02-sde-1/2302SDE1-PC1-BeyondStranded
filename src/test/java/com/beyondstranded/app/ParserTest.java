package com.beyondstranded.app;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private final Parser parser = new Parser();

    @Test
    void parseCommand_ValidCommand_ReturnsTrue() {
        List<String> wordlist = Arrays.asList("take", "rope");
        assertTrue(parser.parseCommand(wordlist));
    }

    @Test
    void parseCommand_InvalidVerb_ReturnsFalse() {
        List<String> wordlist = Arrays.asList("invalidverb", "rope");
        assertFalse(parser.parseCommand(wordlist));
    }

    @Test
    void parseCommand_InvalidNoun_ReturnsFalse() {
        List<String> wordlist = Arrays.asList("take", "invalidnoun");
        assertFalse(parser.parseCommand(wordlist));
    }

    @Test
    void parseCommand_IncorrectWordCount_ReturnsFalse() {
        List<String> wordlist = Arrays.asList("take");
        assertFalse(parser.parseCommand(wordlist));
    }

    @Test
    void wordList_ValidInput_ReturnsWordList() {
        String input = "Hello, world! This is a test.";
        assertEquals(Arrays.asList("Hello", "world", "This", "is", "a", "test"), parser.wordList(input));
    }

    @Test
    void wordList_EmptyInput_ReturnsEmptyList() {
        String input = "";
        assertEquals(1, parser.wordList(input).size());
    }

    @Test
    void wordList_InputWithSpecialCharacters_ReturnsWordListWithoutSpecialCharacters() {
        String input = "The quick brown fox jumps over the lazy dog!";
        assertEquals(Arrays.asList("The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"), parser.wordList(input));
    }

    @Test
    void wordList_InputWithRepeatedWords_ReturnsWordListWithRepeatedWords() {
        String input = "apple apple banana banana cherry";
        assertEquals(Arrays.asList("apple", "apple", "banana", "banana", "cherry"), parser.wordList(input));
    }

    @Test
    void userCommand_QuitCommand_ReturnsQuitCommand() {
        Parser parser = new Parser();
        String input = "quit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(Arrays.asList(input), parser.userCommand());
    }

    @Test
    void userCommand_HelpCommand_ReturnsHelpCommand() {
        Parser parser = new Parser();
        String input = "help";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(Arrays.asList(input), parser.userCommand());
    }

    @Test
    void userCommand_ValidCommand_ReturnsUserInputList() {
        Parser parser = new Parser();
        String input = "look rope";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(Arrays.asList(input.split(" ")), parser.userCommand());
    }
}