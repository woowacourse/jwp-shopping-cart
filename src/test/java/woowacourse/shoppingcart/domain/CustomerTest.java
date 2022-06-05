package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @DisplayName("닉네임이 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwWhenNicknameNullOrEmpty(String nickname) {
        assertThatThrownBy(() ->
                new Customer(new Account("hamcheeseburger"),
                        nickname, new EncodedPassword("password"),
                        "address", "01012345678"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임은 비어있을 수 없습니다.");
    }

    @DisplayName("닉네임 길이가 2~10을 만족하지 못하면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"n", "nicknameLong"})
    void throwWhenInvalidNicknameLength(String nickname) {
        assertThatThrownBy(() ->
                new Customer(new Account("hamcheeseburger"),
                        nickname, new EncodedPassword("password"),
                        "address", "01012345678"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 길이는 2~10자를 만족해야 합니다.");
    }

    @DisplayName("비밀번호가 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwWhenPasswordNullOrEmpty(String password) {
        assertThatThrownBy(() ->
                new Customer(new Account("hamcheeseburger"),
                        "nickname", new EncodedPassword(password),
                        "address", "01012345678"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 비어있을 수 없습니다.");
    }

    @DisplayName("주소가 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwWhenAddressNullOrEmpty(String address) {
        assertThatThrownBy(() ->
                new Customer(new Account("hamcheeseburger"),
                        "nickname", new EncodedPassword("password"),
                        address, "01012345678"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소는 비어있을 수 없습니다.");
    }

    @DisplayName("주소 길이가 255자를 초과하면 예외를 발생한다.")
    @Test
    void throwWhenInvalidAddressLength() {
        final String address = "a".repeat(256);
        assertThatThrownBy(() ->
                new Customer(new Account("hamcheeseburger"),
                        "nickname", new EncodedPassword("password"),
                        address, "01012345678"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소 길이는 255자를 초과할 수 없습니다.");
    }

    @DisplayName("핸드폰 번호가 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwWhenPhoneNumberNullOrEmpty(String phoneNumber) {
        assertThatThrownBy(() ->
                new Customer(new Account("hamcheeseburger"),
                        "nickname", new EncodedPassword("password"),
                        "address", phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("핸드폰 번호는 비어있을 수 없습니다.");
    }

    @DisplayName("핸드폰 번호의 길이가 11자가 아니라면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"0101234567", "010123456789"})
    void throwWhenInvalidPhoneLength(String phoneNumber) {
        assertThatThrownBy(() ->
                new Customer(new Account("hamcheeseburger"),
                        "nickname", new EncodedPassword("password"),
                        "address", phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("핸드폰 번호 길이는 11자 이어야 합니다.");
    }
}
