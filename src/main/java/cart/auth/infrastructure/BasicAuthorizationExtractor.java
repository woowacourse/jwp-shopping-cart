package cart.auth.infrastructure;

import cart.member.dto.MemberRequest;
import cart.member.dto.MemberResponse;
import cart.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasicAuthorizationExtractor implements AuthorizationExtractor<MemberRequest> {
    private static final String BASIC_TYPE = "Basic";
    private static final String DELIMITER = ":";
    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;
    
    private final MemberService memberService;
    
    @Override
    public MemberRequest extract(final String header) {
        validateBasic(header);
        
        String authHeaderValue = header.substring(BASIC_TYPE.length()).trim();
        byte[] decodedBytes = Base64.decodeBase64(authHeaderValue);
        String decodedString = new String(decodedBytes);
        
        String[] credentials = decodedString.split(DELIMITER);
        String email = credentials[EMAIL_INDEX];
        String password = credentials[PASSWORD_INDEX];
        
        MemberResponse memberResponse = memberService.findByEmailAndPassword(email, password);
        
        return new MemberRequest(memberResponse.getId(), memberResponse.getEmail(), memberResponse.getPassword());
    }
    
    private void validateBasic(final String header) {
        if (!header.toLowerCase().startsWith(BASIC_TYPE.toLowerCase())) {
            throw new IllegalArgumentException("[ERROR] Basic 형식이 아닙니다.");
        }
    }
}
