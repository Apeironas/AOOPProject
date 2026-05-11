package model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator pattern: metin üzerinde karakter/satır bazlı gezinim sağlar.
 * UI veya komutlar bu sınıf üzerinden sıralı gezinebilir.
 */
public class TextDocument {
    private final String text;

    public TextDocument(String text) {
        this.text = text == null ? "" : text;
    }

    public Iterable<Character> characters() {
        return CharIterable::new;
    }

    public Iterable<String> lines() {
        return LineIterable::new;
    }

    private final class CharIterable implements Iterator<Character> {
        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < text.length();
        }

        @Override
        public Character next() {
            if (!hasNext()) throw new NoSuchElementException();
            return text.charAt(i++);
        }
    }

    /**
     * Satır iterator'ı: \n veya \r\n ayırıcılarını destekler ve satır içeriğini (satır sonu hariç) döndürür.
     */
    private final class LineIterable implements Iterator<String> {
        private int i = 0;

        @Override
        public boolean hasNext() {
            return i <= text.length();
        }

        @Override
        public String next() {
            if (!hasNext()) throw new NoSuchElementException();
            if (i == text.length()) {
                i++; // hasNext() false olsun diye
                return "";
            }

            int start = i;
            while (i < text.length()) {
                char c = text.charAt(i);
                if (c == '\n' || c == '\r') break;
                i++;
            }

            String line = text.substring(start, i);

            // newline tüket
            if (i < text.length()) {
                char c = text.charAt(i);
                if (c == '\r') {
                    i++;
                    if (i < text.length() && text.charAt(i) == '\n') i++;
                } else if (c == '\n') {
                    i++;
                }
            } else {
                i = text.length();
            }

            return line;
        }
    }
}

