package application.validator;

import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;

import java.util.HashMap;
import java.util.Map;

public class Validation {
    private Expression requiredValidationExpression = new RequiredExpression();
    private Expression nameValidationExpression = new NameExpression();
    private Map<String, TextInputControl> fields = new HashMap<>();

    public void addFieldToValidationMap(String key, TextInputControl value){
        fields.put(key, value);
    }

    private boolean isRequired(TextInputControl field) {
        String text = field.getText();

        if (text == null || text.trim().isEmpty()) {
            showValidationMessage(field, "is required!");
            return false;
        }
        return true;
    }
    private boolean isShortField(TextInputControl field ,String key) {
        if ((key.equals("outputName")| key.equals("nameProject")) && !nameValidationExpression.interpret(field.getText().trim())) {
            showValidationMessage(field, "it should be more than 1 symbol");
            return true;
        }
        return false;
    }

    private void showValidationMessage(TextInputControl field, String message) {
        field.setStyle("-fx-border-color: red; -fx-border-width:2px ");
        Tooltip tooltip = new Tooltip(message);
        tooltip.setStyle("-fx-text-fill: red;");
        Tooltip.install(field, tooltip);
    }

    public boolean validate() {
        boolean isValid = true;
        for (Map.Entry<String, TextInputControl> entry : fields.entrySet()) {
            String key = entry.getKey();
            TextInputControl field = entry.getValue();

            if (!isRequired(field) || isShortField(field, key)) {
                isValid = false;
            } else {
                resetFieldStyle(field);
            }
        }
        return isValid;
    }

    private void resetFieldStyle(TextInputControl field) {
        field.setStyle("-fx-border-color: none;");
        Tooltip.uninstall(field, field.getTooltip());
    }


}
