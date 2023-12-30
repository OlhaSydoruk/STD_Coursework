package application.validator;

public class NameExpression implements Expression{
    @Override
    public boolean interpret(String text) {
        return text.length() >= 2;
    }
}
