package cart.error;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class ExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CartException e) {
            writerErrorCode(response, e.getErrorCode());
        } catch (Exception e) {
<<<<<<< HEAD
<<<<<<< HEAD
            writerErrorCode(response, new ErrorCode(500, "SERVER-500-1", "Internal Server Error"));
=======
            writerErrorCode(response, new ErrorCode(400, "PRODUCT-400-1", "Internal Server Error"));
>>>>>>> 39f685c2 (refactor: Exception 클래스에서 에러 반환 값 선언)
=======
            writerErrorCode(response, new ErrorCode(500, "SERVER-500-1", "Internal Server Error"));
>>>>>>> 2c4cb820 (refactor: 예외 수정 및 변수 추출)
        }
    }

    private void writerErrorCode(
            final HttpServletResponse response,
            final ErrorCode errorCode
    ) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.getStatus(),
                errorCode.getCode(),
                errorCode.getMessage()
        );

        response.setStatus(errorCode.getStatus());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(errorResponse.toString());
    }

}
