package woowacourse.member.ui.dto;

import javax.validation.constraints.NotBlank;

public class UpdateNameRequest {

    @NotBlank(message = "이름은 빈 값일 수 없습니다.")
    private String name;

    public UpdateNameRequest() {
    }

    public UpdateNameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
