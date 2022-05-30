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

	public Customer(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
