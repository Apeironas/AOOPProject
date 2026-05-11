package model;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Memento pattern: geçmiş metin durumlarını saklar.
 * Sadece "undo" gerektiği için stack (LIFO) yeterlidir.
 */
public class UndoHistory {
    private final int maxSize;
    private final Deque<String> stack = new ArrayDeque<>();

    public UndoHistory(int maxSize) {
        this.maxSize = Math.max(1, maxSize);
    }

    public void push(String snapshot) {
        if (snapshot == null) snapshot = "";
        if (!stack.isEmpty() && snapshot.equals(stack.peek())) return;
        stack.push(snapshot);
        while (stack.size() > maxSize) {
            stack.removeLast();
        }
    }

    public String pop() {
        return stack.isEmpty() ? null : stack.pop();
    }

    public boolean canUndo() {
        return !stack.isEmpty();
    }

    public void clear() {
        stack.clear();
    }
}

