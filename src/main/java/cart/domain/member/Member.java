package cart.domain.member;

public class Member {
	private MemberId id;
	private final String name;
	private final String email;
	private final String password;

	public Member(MemberId id, String name, String email, String password) {
		this(name, email, password);
		this.id = id;
	}

	public Member(String name, String email, String password) {
		validate(name, email, password);
		this.name = name;
		this.email = email;
		this.password = password;
	}

	private void validate(final String name, final String email, final String password) {
		if (name.isBlank()) {
			throw new IllegalArgumentException("이름은 공백이거나 비어있을 수 없습니다. 이름을 입력해주세요.");
		}
		if (email.isBlank()) {
			throw new IllegalArgumentException("이메일은 공백이거나 비어있을 수 없습니다. 이메일을 입력해주세요.");
		}
		if (password.isBlank()) {
			throw new IllegalArgumentException("비밀번호는 공백이거나 비어있을 수 없습니다. 비밀번호를 입력해주세요.");
		}
	}

	public MemberId getId() {
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
}
