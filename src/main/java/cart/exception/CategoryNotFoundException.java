package cart.exception;

public final class CategoryNotFoundException extends GlobalException {

    private static final String message = "해당 카테코리가 존재하지 않습니다.";

    public CategoryNotFoundException() {
        super(message);
    }
}
