package cart.domain.user;

import java.time.LocalDateTime;

public class User {

	private final Long id;
	private final String name;
	private final String email;
	private final String password;
	private final String phoneNumber;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;

	public User(final String email, final String password) {
		this(null, null, email, password, null, null, null);
	}

	public User(final Long id, final String name, final String email, final String password, final String phoneNumber,
		final LocalDateTime createdAt,
		final LocalDateTime updatedAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
