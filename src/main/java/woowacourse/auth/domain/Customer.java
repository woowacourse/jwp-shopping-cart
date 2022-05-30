package woowacourse.auth.domain;

import static lombok.EqualsAndHashCode.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Customer {

	@Include
	private Long id;
	private final String email;
	private final String password;
	private final String nickname;

	public Customer(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}
}
