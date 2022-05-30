package woowacourse.member.dto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class MemberNameUpdateRequest {

    @NotBlank(message = "이름에는 공백이 허용되지 않습니다.")
    @Length(min = 1, max = 10, message = "이름은 1자 이상 10자 이하여야합니다.")
    private String name;

    private MemberNameUpdateRequest() {
    }

    public MemberNameUpdateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
