package shoppingmall.domainservice.domain.user;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.user.service.UserRdbService;
import shoppingmall.domainservice.common.annotation.DomainService;

@DomainService
@RequiredArgsConstructor
public class UserFindService {
    private final UserRdbService userRdbService;


}
