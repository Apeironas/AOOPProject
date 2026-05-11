package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextDocumentIteratorTest {

    @Test
    void linesIteratesOverLines() {
        TextDocument doc = new TextDocument("a\nbb\nccc");
        List<String> lines = new ArrayList<>();
        for (String l : doc.lines()) lines.add(l);

        assertEquals(List.of("a", "bb", "ccc", ""), lines);
    }

    @Test
    void charactersIteratesOverCharacters() {
        TextDocument doc = new TextDocument("ab");
        StringBuilder sb = new StringBuilder();
        for (char c : doc.characters()) sb.append(c);
        assertEquals("ab", sb.toString());
    }
}

