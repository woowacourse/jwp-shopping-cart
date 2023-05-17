package cart.auth.infrastructure;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;

import cart.auth.dto.AuthInfo;

public class BasicAuthorizationExtractor implements AuthorizationExtractor<AuthInfo> {
	private static final String BASIC_TYPE = "Basic";
	private static final String DELIMITER = ":";
	private static final int CREDENTIAL_SIZE = 2;
	private static final int EMAIL_INDEX = 0;
	private static final int PW_INDEX = 1;

	@Override
	public AuthInfo extract(final HttpServletRequest request) {
		final String header = request.getHeader(AUTHORIZATION);

		final String authHeaderValue = getBase64Code(header);
		byte[] decodedBytes = Base64.decodeBase64(header);
		final String decodedString = new String(decodedBytes);

		final String[] credentials = getCredentials(decodedString);
		String email = credentials[EMAIL_INDEX];
		String password = credentials[PW_INDEX];

		return new AuthInfo(email, password);
	}

	private String getBase64Code(final String header) {
		validateHeader(header);
		validateBasicAuthorizationType(header);
		return header;
	}

	private void validateHeader(final String header) {
		if (header == null || header.isBlank()) {
			throw new IllegalArgumentException("Header 에 인증정보를 담아주세요");
		}
	}

	private void validateBasicAuthorizationType(final String header) {
		if (!header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
			throw new IllegalArgumentException("Basic 타입 인증 요청을 해주세요");
		}
	}

	private String[] getCredentials(final String plain) {
		final String[] credentials = plain.split(DELIMITER);
		validateCredentials(credentials);

		return credentials;
	}

	private void validateCredentials(final String[] credentials) {
		if (credentials.length != CREDENTIAL_SIZE) {
			throw new IllegalArgumentException("올바른 base64 인증정보를 입력하세요");
		}
	}
}
