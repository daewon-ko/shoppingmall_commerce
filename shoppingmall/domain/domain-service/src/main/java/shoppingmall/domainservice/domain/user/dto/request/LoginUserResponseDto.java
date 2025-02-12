package shoppingmall.domainservice.domain.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.user.entity.User;

@Getter
public class LoginUserResponseDto {
    private String sessionId;
    private String email;


    @Builder
    private LoginUserResponseDto(String sessionId, String email) {
        this.sessionId = sessionId;
        this.email = email;
    }


}
