package woowacourse.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

public enum ErrorCodeToStatusCodeMapper {

	EMAIL_FORMAT(ErrorCode.EMAIL_FORMAT, HttpStatus.BAD_REQUEST.value()),
	NICKNAME_FORMAT(ErrorCode.NICKNAME_FORMAT, HttpStatus.BAD_REQUEST.value()),
	PASSWORD_FORMAT(ErrorCode.PASSWORD_FORMAT, HttpStatus.BAD_REQUEST.value()),
	DUPLICATE_EMAIL(ErrorCode.DUPLICATE_EMAIL, HttpStatus.BAD_REQUEST.value()),
	PASSWORD_NOT_MATCH(ErrorCode.PASSWORD_NOT_MATCH, HttpStatus.UNAUTHORIZED.value()),

	LOGIN(ErrorCode.LOGIN, HttpStatus.UNAUTHORIZED.value()),

	PRODUCT_NOT_FOUND(ErrorCode.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND.value()),

	QUANTITY_FORMAT(ErrorCode.QUANTITY_FORMAT, HttpStatus.BAD_REQUEST.value()),
	NOT_IN_CART(ErrorCode.NOT_IN_CART, HttpStatus.NOT_FOUND.value()),
	NOT_EXIST_ORDER(ErrorCode.NOT_EXIST_ORDER, HttpStatus.NOT_FOUND.value()),

	NOT_EXIST_URI(ErrorCode.NOT_EXIST_URI, HttpStatus.NOT_FOUND.value()),
	TOKEN_EXPIRED(ErrorCode.TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED.value()),
	TOKEN_INVALID(ErrorCode.TOKEN_INVALID, HttpStatus.UNAUTHORIZED.value()),
	AUTH(ErrorCode.AUTH, HttpStatus.UNAUTHORIZED.value());

	private final ErrorCode code;
	private final int statusCode;

	ErrorCodeToStatusCodeMapper(ErrorCode code, int statusCode) {
		this.code = code;
		this.statusCode = statusCode;
	}

	public static int find(ErrorCode code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.map(value -> value.statusCode)
			.findAny()
			.orElse(500);
	}
}
