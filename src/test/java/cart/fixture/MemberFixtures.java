package cart.fixture;

import cart.dto.MemberAuthRequest;
import cart.dto.MemberRegisterRequest;
import cart.entity.MemberEntity;

public class MemberFixtures {

    /**
     * DUMMY_DATA
     */
    public static final String DUMMY_NICKNAME = "SeongHa";
    public static final String DUMMY_EMAIL = "seongha@gmail.com";
    public static final String DUMMY_PASSWORD = "1234";
    public static final Long DUMMY_MEMBER_ID = 1L;
    public static final Long DUMMY_CART_ID = 1L;

    /**
     * DUMMY_ENTITY
     */
    public static final MemberEntity INSERT_MEMBER_ENTITY =
            new MemberEntity.Builder()
                    .nickname(DUMMY_NICKNAME)
                    .email(DUMMY_EMAIL)
                    .password(DUMMY_PASSWORD)
                    .build();

    /**
     * DUMMY_MEMBER_REGISTER_REQUEST
     */
    public static final MemberRegisterRequest MEMBER_REGISTER_REQUEST =
            new MemberRegisterRequest(DUMMY_NICKNAME, DUMMY_EMAIL, DUMMY_PASSWORD);

    /**
     * DUMMY_MEMBER_AUTH_REQUEST
     */
    public static final MemberAuthRequest MEMBER_AUTH_REQUEST =
            new MemberAuthRequest(DUMMY_EMAIL, DUMMY_PASSWORD);
}
