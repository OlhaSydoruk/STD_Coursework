package application.services.convert;
@FunctionalInterface
public interface ConversionCallback {
    void onConversionResult(boolean success, String message);
}
