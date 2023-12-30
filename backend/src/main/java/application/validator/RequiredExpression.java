package application.validator;

public class RequiredExpression implements Expression{
    @Override
    public boolean interpret(String text) {
        return text.isEmpty();
    }
}
