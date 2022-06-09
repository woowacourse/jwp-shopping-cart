package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class CheckDuplicationRequest {
    @NotNull
    private String name;

    private CheckDuplicationRequest() {
    }

    public CheckDuplicationRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
