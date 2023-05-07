package cart.auth.infrastructure;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;

import cart.auth.dto.AuthInfo;

public class BasicAuthorizationExtractor implements AuthorizationExtractor<AuthInfo> {
	private static final String BASIC_TYPE = "Basic";
	private static final String DELIMITER = ":";

	@Override
	public AuthInfo extract(final HttpServletRequest request) {
		final String header = request.getHeader(AUTHORIZATION);

		if (header == null) {
			return null;
		}

		if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
			final String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
			byte[] decodeBytes = Base64.decodeBase64(authHeaderValue);
			final String decodedString = new String(decodeBytes);

			final String[] credentials = decodedString.split(DELIMITER);
			String email = credentials[0];
			String password = credentials[1];

			return new AuthInfo(email, password);
		}
		return null;
	}
}
