package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerRequest {

    public static class UserNameAndPassword {
        @NotBlank(message = "유저 이름은 빈칸일 수 없습니다.")
        private String userName;
        @Size(min = 8, message = "비밀번호는 8자리 이상이어야 합니다.")
        private String password;

        private UserNameAndPassword() {
        }

        public UserNameAndPassword(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }


    public static class UserNameOnly {
        @NotBlank(message = "유저 이름은 빈칸일 수 없습니다.")
        private String userName;

        private UserNameOnly() {

        }

        public UserNameOnly(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }
    }
}
