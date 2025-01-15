package shoppingmall.domainservice.domain.user;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.user.service.UserRdbService;

@DomainService
@RequiredArgsConstructor
public class UserFindService {
    private final UserRdbService userRdbService;


}
