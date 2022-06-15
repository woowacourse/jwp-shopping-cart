package woowacourse.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	EMAIL_FORMAT(2101),
	NICKNAME_FORMAT(2102),
	PASSWORD_FORMAT(2103),
	DUPLICATE_EMAIL(2001),
	PASSWORD_NOT_MATCH(2202),

	LOGIN(2201),

	PRODUCT_NOT_FOUND(3001),
	QUANTITY_FORMAT(4101),
	NOT_IN_CART(4001),

	NOT_EXIST_ORDER(5001),

	NOT_EXIST_URI(1001),
	TOKEN_EXPIRED(1002),
	TOKEN_INVALID(1003),
	AUTH(1004),

	ARGUMENT(6000);

	private final int code;

	ErrorCode(int code) {
		this.code = code;
	}
}
