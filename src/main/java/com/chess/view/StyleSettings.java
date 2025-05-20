package com.chess.view;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JButton;

public class StyleSettings {
    public enum Key {
        // BoardPanel
        PIECE_FONT,
        EVEN_SQUARE,
        ODD_SQUARE,
        EVEN_HIGHLIGHT,
        ODD_HIGHLIGHT,
        // ControlPanel
        CONTROL_BG,
        CONTROL_FONT,
        CONTROL_FG,
        CONTROL_BUTTON_BG
    }

    private static final PropertyChangeSupport pcs = new PropertyChangeSupport(StyleSettings.class);
    private static final Map<Key, Object> values = new EnumMap<>(Key.class);

    static {
        // BoardPanel defaults
        values.put(Key.PIECE_FONT,      Style.CHESS_PIECE_FONT);
        values.put(Key.EVEN_SQUARE,     Style.CHESS_BEIGE);
        values.put(Key.ODD_SQUARE,      Style.CHESS_GREEN);
        values.put(Key.EVEN_HIGHLIGHT,  Style.CHESS_BEIGE_HIGHLIGHT);
        values.put(Key.ODD_HIGHLIGHT,   Style.CHESS_GREEN_HIGHLIGHT);
        // ControlPanel defaults
        values.put(Key.CONTROL_BG,         Color.BLACK);
        values.put(Key.CONTROL_FONT,       Style.CONTROL_BUTTONS_FONT);
        values.put(Key.CONTROL_FG,         Style.CHESS_GREEN);
        values.put(Key.CONTROL_BUTTON_BG,  Style.CHESS_BEIGE);
    }

    /** Get the current value of a style key. */
    @SuppressWarnings("unchecked")
    public static <T> T get(Key key) {
        return (T) values.get(key);
    }

    /**
     * Change a style at runtime. Fires a PropertyChangeEvent so listeners
     * (e.g. your panels) can repaint or re‐apply styles.
     */
    public static <T> void set(Key key, T newValue) {
        Object old = values.put(key, newValue);
        pcs.firePropertyChange(key.name(), old, newValue);
    }

    /** Listen for *any* style changes. */
    public static void addChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    /** Convenience to apply the ControlButton style to any JButton. */
    public static void applyControlButtonStyle(JButton btn) {
        btn.setBackground(get(Key.CONTROL_BUTTON_BG));
        btn.setForeground(get(Key.CONTROL_FG));
        btn.setFont(get(Key.CONTROL_FONT));
        btn.setFocusPainted(false);
    }
}
