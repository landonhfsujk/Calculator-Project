/**
 * CalculatorModel.java — Sprint 4 Starter
 *
 * REMEMBER from Pong:
 *   Your PongGame class held the game logic — score, ball speed,
 *   collision detection. Nobody else touched that logic directly.
 *
 *   CalculatorModel does the same thing for the calculator.
 *   It holds all the math and state. No buttons. No display. Just logic.
 *
 * YOUR GOAL THIS SPRINT:
 *   Move all the math and state from Calculator.java into this class.
 *   The View will ask the Model what to display.
 *   The Controller will tell the Model what the user did.
 *
 * ✔ SELF-CHECK:
 *   Does CalculatorModel.java compile with NO Swing or AWT imports?
 *   If you see "import javax.swing" — something is wrong.
 */
public class CalculatorModel {

    // ── Step 1: Move your state fields here ──────────────────────────────────
    // These were in Calculator.java — move them here instead.
    // TODO: declare firstOperand (double), currentOperator (String),
    //       startNewNumber (boolean), and displayValue (String)

    private double firstOperand;
    private String currentOperator = "";
    private boolean startNewNumber = true;
    private String displayValue = "0";


    // ────────────────────────────────────────────────────────────────────────
    //  INPUT METHODS
    // ────────────────────────────────────────────────────────────────────────

    /**
     * appendDigit — adds a digit to the display value
     *
     * THINK ABOUT IT:
     *   Same logic as before, but instead of calling display.setText()
     *   you update the displayValue field.
     *   The Controller will read displayValue and pass it to the View.
     */
    public void appendDigit(String digit) {
        if (startNewNumber) {
            displayValue = digit;
            startNewNumber = false;
        } else if (displayValue.equals("0") && !digit.equals("0")) {
            displayValue = digit;
        } else if (!(displayValue.equals("0") && digit.equals("0"))) {
            displayValue += digit;
        }
    }

    /**
     * setOperator — stores the operator and first operand
     */
    public void setOperator(String operator) {
        if (!currentOperator.isEmpty() && !startNewNumber) {
            computeResult();
        }

        firstOperand = parseDisplay();
        currentOperator = operator;
        startNewNumber = true;
    }

    /**
     * computeResult — does the math
     *
     * THINK ABOUT IT:
     *   Same logic as before, but store the result in displayValue
     *   instead of calling display.setText()
     */
    public void computeResult() {
        if (currentOperator.isEmpty() || startNewNumber) {
            return;
        }

        double secondOperand = parseDisplay();
        double result;

        switch (currentOperator) {
            case "+" -> result = firstOperand + secondOperand;
            case "-" -> result = firstOperand - secondOperand;
            case "*" -> result = firstOperand * secondOperand;
            case "/" -> {
                if (secondOperand == 0) {
                    displayValue = "Error";
                    currentOperator = "";
                    startNewNumber = true;
                    return;
                }
                result = firstOperand / secondOperand;
            }
            default -> {
                return;
            }
        }

        displayValue = formatResult(result);
        firstOperand = result;
        currentOperator = "";
        startNewNumber = true;
    }

    /**
     * clear — resets everything
     */
    public void clear() {
        firstOperand = 0;
        currentOperator = "";
        startNewNumber = true;
        displayValue = "0";
    }

    /**
     * backspace — removes last character
     */
    public void backspace() {
        if (startNewNumber) {
            return;
        }

        if (displayValue.length() <= 1) {
            displayValue = "0";
            startNewNumber = true;
            return;
        }

        displayValue = displayValue.substring(0, displayValue.length() - 1);
    }

    /**
     * toggleSign — flips positive/negative
     */
    public void toggleSign() {
        double value = parseDisplay();
        displayValue = formatResult(-value);
        startNewNumber = false;
    }

    /**
     * applyPercent — divides by 100
     */
    public void applyPercent() {
        double value = parseDisplay() / 100;
        displayValue = formatResult(value);
        startNewNumber = false;
    }

    /**
     * appendDecimal — adds decimal point
     */
    public void appendDecimal() {
        if (startNewNumber) {
            displayValue = "0.";
            startNewNumber = false;
            return;
        }

        if (!displayValue.contains(".")) {
            displayValue += ".";
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    //  ACCESSOR — the Controller reads this to update the View
    // ────────────────────────────────────────────────────────────────────────

    /**
     * getDisplayValue — returns what should be shown on screen
     *
     * The Controller calls this after every action, then passes
     * the result to the View. This is how Model talks to View
     * WITHOUT knowing the View exists.
     */
    public String getDisplayValue() {
        return displayValue;
    }

    // ────────────────────────────────────────────────────────────────────────
    //  HELPERS — provided for you, do not modify
    // ────────────────────────────────────────────────────────────────────────

    private double parseDisplay() {
        try   { return Double.parseDouble(displayValue); }
        catch (NumberFormatException e) { return 0; }
    }

    private String formatResult(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value))
            return String.valueOf((long) value);
        return String.valueOf(value);
    }
}