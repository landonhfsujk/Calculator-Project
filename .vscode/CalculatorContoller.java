import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CalculatorController.java — Sprint 4 Starter
 *
 * REMEMBER from Pong:
 *   Your Main class created PongGame, Ball, and Paddles,
 *   and connected them together.
 *
 *   CalculatorController does the same thing.
 *   It creates the Model and the View, connects them,
 *   and listens for button clicks.
 *
 * YOUR GOAL THIS SPRINT:
 *   - Create a Model and a View in the constructor
 *   - Register this Controller as the button listener
 *   - In actionPerformed(), call the right Model method
 *   - After every action, ask the Model for the display value
 *     and pass it to the View
 *
 * ✔ SELF-CHECK:
 *   Does CalculatorController have NO Swing/AWT components?
 *   (No JButton, no JTextField — just logic)
 *   Does actionPerformed() end with view.setDisplay(model.getDisplayValue())?
 *   Does your calculator still work exactly like it did in Sprint 3?
 */
public class CalculatorContoller implements ActionListener {

    // Model and View fields
    private final CalculatorModel model;
    private final CalculatorView view;

    // ── Constructor ───────────────────────────────────────────────────────────

    public CalculatorContoller() {
        model = new CalculatorModel();
        view = new CalculatorView();
        view.show();
    }

    public void registerButtonListener() {
        view.addButtonListener(this);
    }

    // ────────────────────────────────────────────────────────────────────────
    //  actionPerformed — called when any button is clicked
    // ────────────────────────────────────────────────────────────────────────

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd == null || cmd.isEmpty()) {
            view.setDisplay(model.getDisplayValue());
            return;
        }

        switch (cmd) {
            case "C", "AC", "clear" -> invokeModel("clear");
            case "+", "-", "*", "/" -> invokeModel("addOperator", cmd);
            case "." -> invokeModel("addDecimal");
            case "=", "equals" -> invokeModel("computeResult");
            default -> {
                if (cmd.matches("[0-9]")) {
                    invokeModel("addDigit", cmd);
                } else {
                    invokeModel("handleButton", cmd);
                }
            }
        }

        view.setDisplay(model.getDisplayValue());
    }

    private void invokeModel(String methodName, Object... args) {
        try {
            java.lang.reflect.Method method = CalculatorModel.class.getMethod(methodName, getParameterTypes(args));
            method.invoke(model, args);
        } catch (NoSuchMethodException ex) {
            try {
                java.lang.reflect.Method method = CalculatorModel.class.getMethod(methodName);
                method.invoke(model);
            } catch (ReflectiveOperationException | IllegalArgumentException ignored) {
                // Ignore unknown button commands gracefully.
            }
        } catch (ReflectiveOperationException | IllegalArgumentException ignored) {
            // Ignore unknown button commands gracefully.
        }
    }

    private Class<?>[] getParameterTypes(Object... args) {
        Class<?>[] types = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i].getClass();
        }
        return types;
    }

    // ────────────────────────────────────────────────────────────────────────
    //  MAIN — entry point, do not modify
    // ────────────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            CalculatorContoller controller = new CalculatorContoller();
            controller.registerButtonListener();
        });
    }
}