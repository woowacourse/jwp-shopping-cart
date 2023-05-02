package cart.domain;

import static cart.exception.ErrorCode.MEMBER_INVALID_ROLE;

import cart.exception.GlobalException;

public enum MemberRole {
    USER, ADMIN;

    public static MemberRole from(final String role) {
        try {
            return MemberRole.valueOf(role);
        } catch (final IllegalArgumentException e) {
            throw new GlobalException(MEMBER_INVALID_ROLE);
        }
    }

    public static boolean isAdmin(final String role) {
        final MemberRole memberRole = from(role);
        return memberRole == MemberRole.ADMIN;
    }
}
