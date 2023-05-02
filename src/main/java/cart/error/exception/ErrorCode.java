package cart.error.exception;

public class ErrorCode {
<<<<<<< HEAD
=======

<<<<<<< HEAD
//    INTERNAL_SERVER_ERROR(500, "SERVER-500-1", "Internal Server Error");
>>>>>>> 39f685c2 (refactor: Exception 클래스에서 에러 반환 값 선언)

=======
>>>>>>> 365da9ae (refactor: 반환 값에 상태코드 포함)
    private final int status;
    private final String code;
    private final String message;

    public ErrorCode(
            final int status,
            final String code,
            final String message
    ) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
