package cart.controller.argumentresolver;

import org.springframework.web.context.request.NativeWebRequest;

import cart.dto.user.UserRequest;

public interface AuthDecoder {

    UserRequest decode(NativeWebRequest request);
}
