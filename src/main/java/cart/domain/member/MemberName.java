package cart.domain.member;

public class MemberName {

    public static final int MIN_MEMBER_NAME_LENGTH = 1;
    public static final int MAX_MEMBER_NAME_LENGTH = 10;
    public static final String MEMBER_NAME_LENGTH_ERROR_MESSAGE =
            "사용자 이름의 길이는 " + MIN_MEMBER_NAME_LENGTH + "자 이상 " + MAX_MEMBER_NAME_LENGTH + "자 이하입니다.";

    private final String name;

    public MemberName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (name == null) {
            throw new IllegalArgumentException(MEMBER_NAME_LENGTH_ERROR_MESSAGE);
        }
        final String trimmedName = name.trim();
        if (trimmedName.length() < MIN_MEMBER_NAME_LENGTH || MAX_MEMBER_NAME_LENGTH < trimmedName.length()) {
            throw new IllegalArgumentException(MEMBER_NAME_LENGTH_ERROR_MESSAGE);
        }
    }

    public String getValue() {
        return name;
    }
}
