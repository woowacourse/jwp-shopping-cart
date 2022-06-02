package woowacourse.shoppingcart.dto;

public class CustomerRequest {

    public static class UserNameAndPassword {
        private String userName;
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
