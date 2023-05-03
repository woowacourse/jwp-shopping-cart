package cart.config.auth;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import cart.config.auth.dto.AuthInfo;

@Component
public class BasicAuthorizationExtractor implements AuthorizationExtractor<AuthInfo> {

	private static final String BASIC_TYPE = "Basic";
	private static final String DELIMITER = ":";

	@Override
	public AuthInfo extract(final HttpServletRequest request) {
		String header = request.getHeader(AUTHORIZATION);

		if (header == null) {
			throw new IllegalStateException("Header 값이 비어있습니다.");
		}

		if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
			String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
			byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
			String decodedString = new String(decodedBytes);

			String[] credentials = decodedString.split(DELIMITER);
			String email = credentials[0];
			String password = credentials[1];

			return new AuthInfo(email, password);
		}

		return null;
	}
}
