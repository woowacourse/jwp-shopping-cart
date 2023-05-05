package cart.domain.exception;

public class DbNotAffectedException extends RuntimeException {

    private static final String DB_NOT_AFFECTED_MESSAGE = "변경된 정보가 없습니다.";

    public DbNotAffectedException() {
        super(DB_NOT_AFFECTED_MESSAGE);
    }
}
