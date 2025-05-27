package com.chess.view;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

public class StyleSettings {

    public enum BoardStyle {
        PIECE_FONT,
        EVEN_SQUARE,
        ODD_SQUARE,
        EVEN_HIGHLIGHT,
        ODD_HIGHLIGHT,
        LAST_MOVE_BORDER,
        PIECE_COLOR,
        BOARD_BG
    }

    public enum ControlStyle {
        BG,
        BUTTON_FONT,
        BUTTON_TEXT_COLOR,
        BUTTON_BG
    }

    public enum ProgressBarStyle {
        BAR_COLOR,
        BAR_BG,
        BAR_BORDER
    }

    public enum Preset {
        CLASSIC,
        CHESS_COM,
        DARK,
        LICHESS,
        HIGH_CONTRAST
    }

    private static Preset currentPreset;
    private static final PropertyChangeSupport pcs = new PropertyChangeSupport(StyleSettings.class);
    private static final Map<Preset, Map<Enum<?>, Object>> presets = new EnumMap<>(Preset.class);
    private static final Map<Enum<?>, Object> current = new HashMap<>();

    static {
        // 1) Classic
        {
            Map<Enum<?>, Object> preset = new HashMap<>();
            preset.put(BoardStyle.PIECE_FONT, Style.CHESS_PIECE_FONT);
            preset.put(BoardStyle.EVEN_SQUARE, new Color(0xF0, 0xD9, 0xB5));
            preset.put(BoardStyle.ODD_SQUARE, new Color(0xB5, 0x88, 0x63));
            preset.put(BoardStyle.EVEN_HIGHLIGHT, new Color(0xFF, 0xF3, 0xC0));
            preset.put(BoardStyle.ODD_HIGHLIGHT, new Color(0xD1, 0xA6, 0x79));
            preset.put(BoardStyle.LAST_MOVE_BORDER, new LineBorder(new Color(0xC0, 0x32, 0x32), 4));
            preset.put(BoardStyle.PIECE_COLOR, new Color(0x10, 0x10, 0x10));
            preset.put(BoardStyle.BOARD_BG, new Color(0x80, 0x50, 0x20));

            preset.put(ControlStyle.BG, new Color(0x20, 0x20, 0x20));
            preset.put(ControlStyle.BUTTON_FONT, Style.CONTROL_BUTTONS_FONT);
            preset.put(ControlStyle.BUTTON_TEXT_COLOR, new Color(0xB5, 0x88, 0x63));
            preset.put(ControlStyle.BUTTON_BG, new Color(0xF0, 0xD9, 0xB5));

            preset.put(ProgressBarStyle.BAR_BG, Color.BLACK);
            preset.put(ProgressBarStyle.BAR_COLOR, Color.RED);
            preset.put(ProgressBarStyle.BAR_BORDER, BorderFactory.createLineBorder(Color.WHITE));

            presets.put(Preset.CLASSIC, Collections.unmodifiableMap(preset));
        }

        // 2) Chess.com
        {
            Map<Enum<?>, Object> preset = new HashMap<>();
            preset.put(BoardStyle.PIECE_FONT, Style.CHESS_PIECE_FONT);
            preset.put(BoardStyle.PIECE_COLOR, new Color(0x10, 0x10, 0x10));
            preset.put(BoardStyle.EVEN_SQUARE, new Color(0xEE, 0xEE, 0xD2));
            preset.put(BoardStyle.ODD_SQUARE, new Color(0x76, 0x96, 0x56));
            preset.put(BoardStyle.EVEN_HIGHLIGHT, new Color(0xFF, 0xF8, 0x9C));
            preset.put(BoardStyle.ODD_HIGHLIGHT, new Color(0x8B, 0xC3, 0x4A));
            preset.put(BoardStyle.LAST_MOVE_BORDER, new LineBorder(new Color(0x64, 0xBA, 0x2C), 4));
            preset.put(BoardStyle.BOARD_BG, new Color(0x6D, 0x8F, 0x5F));

            preset.put(ControlStyle.BG, new Color(0x24, 0x5E, 0x46));
            preset.put(ControlStyle.BUTTON_FONT, Style.CONTROL_BUTTONS_FONT);
            preset.put(ControlStyle.BUTTON_TEXT_COLOR, new Color(0xEE, 0xEE, 0xD2));
            preset.put(ControlStyle.BUTTON_BG, new Color(0x76, 0x96, 0x56));

            preset.put(ProgressBarStyle.BAR_BG, Color.BLACK);
            preset.put(ProgressBarStyle.BAR_COLOR, Color.RED);
            preset.put(ProgressBarStyle.BAR_BORDER, BorderFactory.createLineBorder(Color.WHITE));

            presets.put(Preset.CHESS_COM, Collections.unmodifiableMap(preset));
        }

        // 3) Lichess
        {
            Map<Enum<?>, Object> preset = new HashMap<>();
            preset.put(BoardStyle.PIECE_FONT, Style.CHESS_PIECE_FONT);
            preset.put(BoardStyle.EVEN_SQUARE, new Color(0xF0, 0xD9, 0xB5));
            preset.put(BoardStyle.ODD_SQUARE, new Color(0x58, 0x58, 0x58));
            preset.put(BoardStyle.EVEN_HIGHLIGHT, new Color(0xF7, 0xEC, 0x71));
            preset.put(BoardStyle.ODD_HIGHLIGHT, new Color(0xD5, 0xB4, 0x6A));
            preset.put(BoardStyle.LAST_MOVE_BORDER, new LineBorder(new Color(0xBF, 0x30, 0x30), 4));
            preset.put(BoardStyle.PIECE_COLOR, new Color(0x30, 0x30, 0x30));
            preset.put(BoardStyle.BOARD_BG, new Color(0xE2, 0xD8, 0xB7));

            preset.put(ControlStyle.BG, new Color(0x30, 0x30, 0x30));
            preset.put(ControlStyle.BUTTON_FONT, Style.CONTROL_BUTTONS_FONT);
            preset.put(ControlStyle.BUTTON_TEXT_COLOR, new Color(0xF0, 0xD9, 0xB5));
            preset.put(ControlStyle.BUTTON_BG, new Color(0x58, 0x58, 0x58));

            preset.put(ProgressBarStyle.BAR_BG, Color.BLACK);
            preset.put(ProgressBarStyle.BAR_COLOR, Color.RED);
            preset.put(ProgressBarStyle.BAR_BORDER, BorderFactory.createLineBorder(Color.WHITE));

            presets.put(Preset.LICHESS, Collections.unmodifiableMap(preset));
        }

        // 4) Dark
        {
            Map<Enum<?>, Object> preset = new HashMap<>();
            preset.put(BoardStyle.PIECE_FONT, Style.CHESS_PIECE_FONT);
            preset.put(BoardStyle.EVEN_SQUARE, new Color(0x55, 0x55, 0x55));
            preset.put(BoardStyle.ODD_SQUARE, new Color(0x33, 0x33, 0x33));
            preset.put(BoardStyle.EVEN_HIGHLIGHT, new Color(0x77, 0x77, 0x66));
            preset.put(BoardStyle.ODD_HIGHLIGHT, new Color(0x66, 0x55, 0x44));
            preset.put(BoardStyle.LAST_MOVE_BORDER, new LineBorder(new Color(0xFF, 0xA0, 0x00), 4));
            preset.put(BoardStyle.PIECE_COLOR, new Color(0xEE, 0xEE, 0xEE));
            preset.put(BoardStyle.BOARD_BG, new Color(0x40, 0x30, 0x20));

            preset.put(ControlStyle.BG, new Color(0x10, 0x10, 0x10));
            preset.put(ControlStyle.BUTTON_FONT, Style.CONTROL_BUTTONS_FONT);
            preset.put(ControlStyle.BUTTON_TEXT_COLOR, new Color(0xEE, 0xEE, 0xEE));
            preset.put(ControlStyle.BUTTON_BG, new Color(0x33, 0x33, 0x33));

            preset.put(ProgressBarStyle.BAR_BG, Color.BLACK);
            preset.put(ProgressBarStyle.BAR_COLOR, Color.RED);
            preset.put(ProgressBarStyle.BAR_BORDER, BorderFactory.createLineBorder(Color.WHITE));

            presets.put(Preset.DARK, Collections.unmodifiableMap(preset));
        }

        // 5) High Contrast
        {
            Map<Enum<?>, Object> preset = new HashMap<>();
            preset.put(BoardStyle.PIECE_FONT, Style.CHESS_PIECE_FONT);
            preset.put(BoardStyle.PIECE_COLOR, new Color(0xFF, 0x00, 0x00));
            preset.put(BoardStyle.EVEN_SQUARE, Color.WHITE);
            preset.put(BoardStyle.ODD_SQUARE, Color.BLACK);
            preset.put(BoardStyle.EVEN_HIGHLIGHT, new Color(0xFF, 0xFF, 0x00));
            preset.put(BoardStyle.ODD_HIGHLIGHT, new Color(0xFF, 0xFF, 0x00));
            preset.put(BoardStyle.LAST_MOVE_BORDER, new LineBorder(new Color(0x00, 0xFF, 0xFF), 4));
            preset.put(BoardStyle.BOARD_BG, new Color(0x11, 0x11, 0x11));

            preset.put(ControlStyle.BG, Color.WHITE);
            preset.put(ControlStyle.BUTTON_FONT, Style.CONTROL_BUTTONS_FONT);
            preset.put(ControlStyle.BUTTON_TEXT_COLOR, Color.BLACK);
            preset.put(ControlStyle.BUTTON_BG, new Color(0xFF, 0xFF, 0x00));

            preset.put(ProgressBarStyle.BAR_BG, Color.BLACK);
            preset.put(ProgressBarStyle.BAR_COLOR, Color.RED);
            preset.put(ProgressBarStyle.BAR_BORDER, BorderFactory.createLineBorder(Color.WHITE));

            presets.put(Preset.HIGH_CONTRAST, Collections.unmodifiableMap(preset));
        }

        applyPreset(Preset.CLASSIC);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Enum<?> key) {
        return (T) current.get(key);
    }

    public static <T> void set(Enum<?> key, T newValue) {
        Object old = current.put(key, newValue);
        pcs.firePropertyChange(key.name(), old, newValue);
    }

    public static void addChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public static void applyControlButtonStyle(JButton btn) {
        btn.setBackground(get(ControlStyle.BUTTON_BG));
        btn.setForeground(get(ControlStyle.BUTTON_TEXT_COLOR));
        btn.setFont(get(ControlStyle.BUTTON_FONT));
        btn.setFocusPainted(false);
    }

    public static void applyBoardButtonStyle(JButton button) {
        button.setFocusPainted(false);
        button.setFont(get(BoardStyle.PIECE_FONT));
        button.setForeground(get(BoardStyle.PIECE_COLOR));
        button.setOpaque(true);
    }

    public static void applyProgressBarStyle(JProgressBar bar) {
        bar.setForeground(get(ProgressBarStyle.BAR_COLOR));
        bar.setBackground(get(ProgressBarStyle.BAR_BG));
        bar.setStringPainted(true);
        bar.setBorder(get(ProgressBarStyle.BAR_BORDER));
    }

    public static void applyPreset(Preset preset) {
        currentPreset = preset;
        Map<Enum<?>, Object> theme = presets.get(preset);
        theme.forEach((key, newValue) -> {
            Object old = current.put(key, newValue);
            if (old == null || !old.equals(newValue)) {
                pcs.firePropertyChange(key.name(), old, newValue);
            }
        });
    }

    public static void togglePreset() {
        Preset[] vals = Preset.values();
        int next = (currentPreset.ordinal() + 1) % vals.length;
        applyPreset(vals[next]);
    }

    public static void applyBoardPanelStyle(BoardPanel boardPanel) {
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
}
