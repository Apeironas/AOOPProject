package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UndoHistoryTest {

    @Test
    void popReturnsLastPushedSnapshot() {
        UndoHistory h = new UndoHistory(10);
        h.push("a");
        h.push("ab");
        h.push("abc");

        assertEquals("abc", h.pop());
        assertEquals("ab", h.pop());
        assertEquals("a", h.pop());
        assertNull(h.pop());
    }

    @Test
    void respectsMaxSize() {
        UndoHistory h = new UndoHistory(2);
        h.push("1");
        h.push("2");
        h.push("3"); // should evict "1"

        assertEquals("3", h.pop());
        assertEquals("2", h.pop());
        assertNull(h.pop());
    }
}

