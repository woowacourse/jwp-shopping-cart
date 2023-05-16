package cart.infra;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import cart.dto.CartRequest;

public class CartRequestExtractor {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static CartRequest extract(HttpServletRequest httpServletRequest) throws IOException {
        String requestBody = httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        return objectMapper.readValue(requestBody, CartRequest.class);
    }
}
