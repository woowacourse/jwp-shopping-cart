package cart.dto;

public class ErrorResponseDto {

    private String exceptionMessage;

    public ErrorResponseDto(Exception exception) {
        this.exceptionMessage = exception.getMessage();
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

}
