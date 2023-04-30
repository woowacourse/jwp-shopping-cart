package cart.entity;

public class MemberEntity {

    private final Long memberId;
    private final String nickname;
    private final String email;
    private final String password;
    private final Long cartId;

    private MemberEntity(final Long memberId, final String nickname,
                         final String email, final String password,
                         final Long cartId) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.cartId = cartId;
    }

    public static class Builder {

        private Long memberId;
        private String nickname;
        private String email;
        private String password;
        private Long cartId;

        public Builder memberId(Long memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder cartId(Long cartId) {
            this.cartId = cartId;
            return this;
        }

        public MemberEntity build() {
            return new MemberEntity(memberId, nickname, email, password, cartId);
        }
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getCartId() {
        return cartId;
    }
}
