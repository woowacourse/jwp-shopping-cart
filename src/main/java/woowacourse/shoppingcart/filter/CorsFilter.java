package woowacourse.shoppingcart.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;

@Component
@WebFilter("/api/**")
public class CorsFilter implements Filter {

    static final String ALLOWED_METHOD_NAMES = "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH";
    private static final String ALLOWED_HEADER_NAMES = "Origin, X-Requested-With, Content-Type, Accept, Authorization";
    private static final String PREFLIGHT_MAX_AGE = "3600";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest)request;
        HttpServletResponse servletResponse = (HttpServletResponse)response;

        servletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        servletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        servletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHOD_NAMES);
        servletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, PREFLIGHT_MAX_AGE);
        servletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADER_NAMES);
        servletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.LOCATION);

        if (CorsUtils.isPreFlightRequest(servletRequest)) {
            servletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }
}
