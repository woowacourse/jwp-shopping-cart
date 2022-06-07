package woowacourse.config;

import static woowacourse.config.WebMvcConfig.ALLOWED_METHOD_NAMES;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class CorsFilter implements Filter {

    public static final String ALLOWED_HEADER_NAMES = "Origin, X-Requested-With, Content-Type, Accept, Authorization";
    public static final String PREFLIGHT_MAX_AGE = "3600";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHOD_NAMES);
        httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, PREFLIGHT_MAX_AGE);
        httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADER_NAMES);
        httpResponse.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.LOCATION);

        if (HttpMethod.OPTIONS.matches(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpStatus.OK.value());
            return;
        }
        chain.doFilter(request, response);
    }
}
