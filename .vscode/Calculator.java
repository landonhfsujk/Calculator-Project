import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



/**
 * Calculator.java — Sprint 1 Starter
 * 
 * YOUR GOAL THIS SPRINT:
 *   - Create a window with a display at the top
 *   - Add number buttons (0-9) that show on the display
 *   - Add a Clear button that resets the display to "0"
 *
 * REMEMBER from Pong:
 *   - You used JFrame to create a window
 *   - You used awt and swing for drawing
 *   - This is the same idea — just buttons instead of a game loop
 */
public class Calculator extends JFrame implements ActionListener {

    
    
    // ── Step 1: Declare your display field ──────────────────────────────────
    // This is the text box at the top that shows numbers.
    // In Pong you had a score — this is like that, but it shows input too.
    private final JTextField display; 
    



    // ── Step 2: Declare your state fields ───────────────────────────────────
    // You need to know: what was the first number the user typed?
    // What operator did they press? Are we starting a new number?
    private double firstOperand;
    private String currentOperator = "";
    private boolean startNewNumber = true;


    // ── Button labels ────────────────────────────────────────────────────────
    // These are all the buttons on the calculator, in order.
    // GridLayout will place them left-to-right, top-to-bottom.
    private static final String[] BUTTON_LABELS = {
        "C",   "⌫",  "%",  "/",
        "7",   "8",  "9",  "*",
        "4",   "5",  "6",  "-",
        "1",   "2",  "3",  "+",
        "+/-", "0",  ".",  "="
    };

    // ────────────────────────────────────────────────────────────────────────
    //  CONSTRUCTOR — builds the window
    // ────────────────────────────────────────────────────────────────────────

    public Calculator() {

        // ── Step 3: Set up the window ────────────────────────────────────────
        // TODO: set the title to "Calculator"
        // TODO: set default close operation to EXIT_ON_CLOSE
        // TODO: set resizable to false

        setTitle("Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
              
            }


        });


        // ── Step 4: Build the display ────────────────────────────────────────
        // TODO: create a new JTextField starting at "0"
        // TODO: right-align the text (JTextField.RIGHT)
        // TODO: make it non-editable (users click buttons, not type)
        // TODO: set font to SansSerif, BOLD, size 28
        // TODO: set background to new Color(30, 30, 30)   ← dark
        // TODO: set foreground to Color.WHITE
        // TODO: set preferred size to new Dimension(300, 70)
        
        display = new JTextField("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setFont(new Font("SansSerif", Font.BOLD, 28));
        display.setBackground(new Color(30, 30, 30));
        display.setForeground(Color.WHITE);
        display.setPreferredSize(new Dimension(300, 70));
        
        
        

        // ── Step 5: Build the button panel ───────────────────────────────────
        // GridLayout(5, 4, 5, 5) means: 5 rows, 4 columns, 5px gaps
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(new Color(45, 45, 45));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Loop through BUTTON_LABELS, create each button, add to panel
        // TODO: complete this loop
        for (String label : BUTTON_LABELS) {
            JButton btn = createButton(label);
            // TODO: add btn to buttonPanel
            buttonPanel.add(btn); 
            this.setLayout((new BorderLayout()));
            this.add(display, BorderLayout.NORTH);
            this.add(buttonPanel, BorderLayout.CENTER);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }

        // ── Step 6: Add display and buttons to the window ────────────────────
        // TODO: set layout to new BorderLayout()
        // TODO: add display to BorderLayout.NORTH
        // TODO: add buttonPanel to BorderLayout.CENTER
        // TODO: call pack() to auto-size the window
        // TODO: call setLocationRelativeTo(null) to center on screen
        // TODO: call setVisible(true)

    }

    public Calculator(JTextField display) throws HeadlessException {
        this.display = display;
    

    }

    // ────────────────────────────────────────────────────────────────────────
    //  createButton — builds and styles one button
    // ────────────────────────────────────────────────────────────────────────

    private JButton createButton(String label) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.addActionListener(this);
        
        // ── Step 7: Color-code the buttons ───────────────────────────────────
        // = button  → orange background (255, 149, 0), white text
        // C ⌫ % +/- → gray background (100, 100, 100), white text
        // + - * /   → dark gray (80, 80, 80), orange text
        // numbers   → dark (60, 60, 60), white text
        // TODO: write the if/else chain for button colors

        switch (label) {
            case "=" -> {
                btn.setBackground(new Color(255, 149, 0));
                btn.setForeground(Color.WHITE);
            }
            case "C", "⌫", "%", "+/-" -> {
                btn.setBackground(new Color(100, 100, 100));
                btn.setForeground(Color.WHITE);
            }
            case "+", "-", "*", "/" -> {
                btn.setBackground(new Color(80, 80, 80));
                btn.setForeground(new Color(255, 149, 0));
            }
            default -> {
                btn.setBackground(new Color(60, 60, 60));
                btn.setForeground(Color.WHITE);
            }
        }

        return btn;
    }

    // ────────────────────────────────────────────────────────────────────────
    //  actionPerformed — called when ANY button is clicked
    // ────────────────────────────────────────────────────────────────────────

    @Override
    public void actionPerformed(ActionEvent e) {
        // e.getActionCommand() gives you the button label that was clicked
        String cmd = e.getActionCommand();
        // changed if statements to switch statements
        switch (cmd) {
            case "C" -> clearAll();
            case "⌫" -> backspace();
            case "+/-" -> toggleSign();
            case "%" -> applyPercent();
            case "=" -> computeResult();
            case "." -> appendDecimal();
            case "+", "-", "*", "/" -> setOperator(cmd);
            default -> appendDigit(cmd);

        }
        
   

        // ── Step 8: Route each button to the right method ────────────────────
        // HINT: use a switch statement, just like in Pong you checked
        // what kind of collision happened and called different methods
        // TODO: route "C" to clearAll()
        // TODO: route "⌫" to backspace()
        // TODO: route "+/-" to toggleSign()
        // TODO: route "%" to applyPercent()
        // TODO: route "=" to computeResult()
        // TODO: route "." to appendDecimal()
        // TODO: route "+", "-", "*", "/" to setOperator(cmd)
        // TODO: route everything else (digits) to appendDigit(cmd)

    }

    // ────────────────────────────────────────────────────────────────────────
    //  SPRINT 1 METHODS — implement these this week
    // ────────────────────────────────────────────────────────────────────────

    /**
     * appendDigit — adds a digit to the display
     * 
     * THINK ABOUT IT:
     *   - If startNewNumber is true, replace the display with just this digit
     *   - If the display currently shows "0", replace it (don't show "07")
     *   - Otherwise, add the digit to the end of what's already there
     */
    private void appendDigit(String digit) {
        // TODO: your code here
        if (startNewNumber) {
            display.setText(digit);
            startNewNumber = false;
        } else if (display.getText().equals("0")) {
            display.setText(digit);
        } else {
            display.setText(display.getText() + digit);
        }
        // nothing extra
    }

    /**
     * clearAll — resets everything back to the start
     * 
     * THINK ABOUT IT:
     *   - Display should go back to "0"
     *   - firstOperand goes back to 0
     *   - currentOperator goes back to ""
     *   - startNewNumber goes back to true
     */
    private void clearAll() {
        display.setText("0");
        firstOperand = 0;
        currentOperator = "";
        startNewNumber = true;
    }

    // ────────────────────────────────────────────────────────────────────────
    //  SPRINT 2 METHODS — leave these for next week, do not delete
    // ────────────────────────────────────────────────────────────────────────

    private void setOperator(String operator) {
        // Sprint 2
        firstOperand = Double.parseDouble(display.getText());
        currentOperator = operator;
        display.setText(operator);
        startNewNumber = true;
    }

    private void computeResult() {
        // Sprint 2
        double secondOperand = Double.parseDouble(display.getText());
        double result = 0;

        switch (currentOperator) {
            case "+" -> result = firstOperand + secondOperand;
            case "-" -> result = firstOperand - secondOperand;
            case "*" -> result = firstOperand * secondOperand;
            case "/" -> result = firstOperand / secondOperand;
        }

        display.setText(formatResult(result));
        currentOperator = "operator"; // Reset operator after computation
        startNewNumber = true;
    }

    // ────────────────────────────────────────────────────────────────────────
    //  SPRINT 3 METHODS — leave these for week 4, do not delete
    // ────────────────────────────────────────────────────────────────────────

    private void backspace() {
        // Sprint 3
        if (display.getText().length() > 1) {
            display.setText(display.getText().substring(0, display.getText().length() - 1));
        } else {
            display.setText("0");
            startNewNumber = true;
        }
    }

    private void toggleSign() {
        // Sprint 3
        double currentValue = Double.parseDouble(display.getText());
        currentValue = -currentValue;
        display.setText(formatResult(currentValue));
        startNewNumber = false;
    }

    private void applyPercent() {
        // Sprint 3
        double currentValue = Double.parseDouble(display.getText());
        currentValue = currentValue / 100;
        display.setText(formatResult(currentValue));
        startNewNumber = false;
    }

    private void appendDecimal() {
        // Sprint 3
        if (startNewNumber) {
            display.setText("0.");
            startNewNumber = false;
            return;
        }

        if (!display.getText().contains(".")) {
            display.setText(display.getText() + ".");
            startNewNumber = false;
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    //  HELPER — do not modify
    // ────────────────────────────────────────────────────────────────────────

    /**
     * formatResult — shows whole numbers without .0
     * Example: 5.0 shows as "5", but 5.5 shows as "5.5"
     * You do not need to write this — it is provided for you.
     */
    private String formatResult(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value))
            return String.valueOf((long) value);
        return String.valueOf(value);
    }

    // ────────────────────────────────────────────────────────────────────────
    //  MAIN — entry point, do not modify
    // ────────────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}
