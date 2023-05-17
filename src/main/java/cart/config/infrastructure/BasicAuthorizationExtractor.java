package cart.config.infrastructure;

import cart.controller.dto.MemberRequest;
import cart.controller.dto.MemberResponse;
import cart.service.MemberService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthorizationExtractor implements AuthorizationExtractor<MemberRequest> {
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    private final MemberService memberService;

    public BasicAuthorizationExtractor(MemberService memberService) {
        this.memberService = memberService;
    }

    public MemberRequest extract(String header) {
        if (header == null) {
            return null;
        }
        if ((header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase()))) {
            String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
            byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
            String decodedString = new String(decodedBytes);

            String[] credentials = decodedString.split(DELIMITER);
            String email = credentials[EMAIL_INDEX];
            String password = credentials[PASSWORD_INDEX];

            MemberResponse memberResponse = memberService.findMemberByEmail(email);
            return new MemberRequest(memberResponse.getEmail(), memberResponse.getPassword());
        }
        return null;
    }
}
