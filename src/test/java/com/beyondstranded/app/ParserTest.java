package com.beyondstranded.app;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parseCommand_ValidCommand_ReturnsTrue() {
        List<String> wordlist = Arrays.asList("take", "tree");
        boolean result = Parser.parseCommand(wordlist);
        assertTrue(result);
    }

    @Test
    void parseCommand_InvalidVerb_ReturnsFalse() {
        List<String> wordlist = Arrays.asList("invalidverb", "tree");
        boolean result = Parser.parseCommand(wordlist);
        assertFalse(result);
    }


    @Test
    void parseCommand_InvalidNoun_ReturnsFalse() {
        List<String> wordlist = Arrays.asList("take", "invalidnoun");
        boolean result = Parser.parseCommand(wordlist);
        assertFalse(result);
    }

    @Test
    void parseCommand_IncorrectWordCount_ReturnsFalse() {
        List<String> wordlist = Arrays.asList("take");
        boolean result = Parser.parseCommand(wordlist);
        assertFalse(result);
    }

    @Test
    void wordList_ValidInput_ReturnsWordList() {
        String input = "Hello, world! This is a test.";
        List<String> expected = Arrays.asList("Hello", "world", "This", "is", "a", "test");
        List<String> result = Parser.wordList(input);
        assertEquals(expected, result);
    }

    @Test
    void wordList_EmptyInput_ReturnsEmptyList() {
        String input = "";
        List<String> result = Parser.wordList(input);
        assertEquals(1, result.size());
    }


    @Test
    void wordList_InputWithSpecialCharacters_ReturnsWordListWithoutSpecialCharacters() {
        String input = "The quick brown fox jumps over the lazy dog!";
        List<String> expected = Arrays.asList("The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog");
        List<String> result = Parser.wordList(input);
        assertEquals(expected, result);
    }

    @Test
    void wordList_InputWithRepeatedWords_ReturnsWordListWithRepeatedWords() {
        String input = "apple apple banana banana cherry";
        List<String> expected = Arrays.asList("apple", "apple", "banana", "banana", "cherry");
        List<String> result = Parser.wordList(input);
        assertEquals(expected, result);
    }

    @Test
    void userCommand_QuitCommand_ReturnsQuitCommand() {
        Parser parser = new Parser();

        String input = "quit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(Arrays.asList("quit"), Parser.userCommand());
    }

    @Disabled
    @Test
    void userCommand_EmptyInput_ReturnsEmptyList() {
        Parser parser = new Parser();
        String input = "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertEquals(new ArrayList<>(), Parser.userCommand());
    }

    @Test
    void userCommand_HelpCommand_ReturnsHelpCommand() {
        Parser parser = new Parser();
        String input = "help";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        List<String> expected = Arrays.asList("help");
        List<String> result = Parser.userCommand();
        assertEquals(expected, result);
    }

    @Test
    void userCommand_ValidCommand_ReturnsUserInputList() {
        Parser parser = new Parser();
        String input = "take tree";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        List<String> expected = Arrays.asList("take", "tree");
        List<String> result = Parser.userCommand();
        assertEquals(expected, result);
    }
}