package shoppingmall.commerce.exception.domain;

import shoppingmall.commerce.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode implements ErrorCode {

    NO_EXIST_CHATROOM(HttpStatus.BAD_REQUEST, "채팅방이 없습니다"),
    INVALID_CHATROOM_NUMBER(HttpStatus.BAD_REQUEST, "잘못된 채팅방 번호입니다.");

    private final HttpStatus httpStatus;
    private final String message;


}
