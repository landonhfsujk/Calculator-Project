import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * CalculatorView.java — Sprint 4 Starter
 *
 * REMEMBER from Pong:
 *   Your Paddles and Ball classes knew how to draw themselves.
 *   They didn't know anything about scoring or game logic.
 *
 *   CalculatorView does the same thing.
 *   It builds the window, buttons, and display.
 *   It does NOT do any math. It just shows what it is told.
 *
 * YOUR GOAL THIS SPRINT:
 *   Move all the UI code from Calculator.java into this class.
 *   Add a setDisplay() method so the Controller can update the screen.
 *   Add an addButtonListener() method so the Controller can
 *   listen for button clicks.
 *
 * ✔ SELF-CHECK:
 *   Does CalculatorView only contain UI code?
 *   Does it have NO math, NO operators, NO firstOperand?
 *   Can you describe what each method does in one sentence?
 */
public class CalculatorView extends JFrame {

    // ── Step 1: Declare your UI fields ───────────────────────────────────────
    private final JTextField display;
    private final JButton[] buttons;

    private static final String[] BUTTON_LABELS = {
        "C",   "⌫",  "%",  "/",
        "7",   "8",  "9",  "*",
        "4",   "5",  "6",  "-",
        "1",   "2",  "3",  "+",
        "+/-", "0",  ".",  "="
    };

    // ── Constructor ───────────────────────────────────────────────────────────

    public CalculatorView() {
        // ── Step 2: Build the window ──────────────────────────────────────────
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setFont(new Font("SansSerif", Font.PLAIN, 28));
        display.setPreferredSize(new Dimension(300, 60));

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttons = new JButton[BUTTON_LABELS.length];
        for (int i = 0; i < BUTTON_LABELS.length; i++) {
            buttons[i] = createButton(BUTTON_LABELS[i]);
            buttonPanel.add(buttons[i]);
        }

        setLayout(new BorderLayout(10, 10));
        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    // ────────────────────────────────────────────────────────────────────────
    //  PUBLIC API — these are the only ways the Controller talks to the View
    // ────────────────────────────────────────────────────────────────────────

    /**
     * setDisplay — updates the text shown on the calculator screen
     *
     * The Controller calls this after every button press.
     * THINK ABOUT IT: one line — display.setText(text)
     */
    public void setDisplay(String text) {
        display.setText(text);
    }

    /**
     * addButtonListener — registers the Controller as the click listener
     *
     * THINK ABOUT IT:
     *   Loop through all buttons and call btn.addActionListener(listener)
     *   The Controller passes itself as the listener.
     */
    public void addButtonListener(ActionListener listener) {
        for (JButton button : buttons) {
            button.addActionListener(listener);
        }
    }

    /**
     * show — makes the window visible
     * Called by the Controller when everything is ready.
     */
    @Override
    public void show() {
        setVisible(true);
    }

    // ────────────────────────────────────────────────────────────────────────
    //  PRIVATE HELPER
    // ────────────────────────────────────────────────────────────────────────

    /**
     * createButton — builds and styles one button
     * TODO: move your createButton code here from Calculator.java
     */
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(70, 55));
        return button;
    }
}