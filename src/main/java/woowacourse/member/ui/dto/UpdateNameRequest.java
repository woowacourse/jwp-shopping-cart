package woowacourse.member.ui.dto;

import woowacourse.member.application.dto.UpdateNameServiceRequest;

import javax.validation.constraints.NotBlank;

public class UpdateNameRequest {

    @NotBlank(message = "이름은 빈 값일 수 없습니다.")
    private String name;

    public UpdateNameRequest() {
    }

    public UpdateNameServiceRequest toServiceRequest(long memberId) {
        return new UpdateNameServiceRequest(memberId, name);
    }

    public UpdateNameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
