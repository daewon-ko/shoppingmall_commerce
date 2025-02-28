package shoppingmall.web.common.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import shoppingmall.domainrdb.common.JpaInspector;
import shoppingmall.domainrdb.common.dto.NPlusOneSuspiciousDto;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class NPlusOneDetectInterceptor implements HandlerInterceptor {
    private final JpaInspector jpaInspector;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        jpaInspector.start();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if (!ObjectUtils.isEmpty(ex)) {
            jpaInspector.clear();
            log.error("QueryCountInterceptor Exception : {}", ex.getMessage());
            return;
        }

        long duration = jpaInspector.getDuration();
        Map<String, Integer> query2Cnt = jpaInspector.getQuery2Cnt();
        logNPlusOneSuspiciousApi(request, duration, query2Cnt);
        jpaInspector.clear();

    }

    private void logNPlusOneSuspiciousApi(HttpServletRequest request, long duration, Map<String, Integer> query2cnt) {

        String url = String.format("url: %s %s", request.getMethod(), request.getRequestURI());

        for (String key : query2cnt.keySet()) {
            if (query2cnt.get(key) >= 2) {
                log.info(String.format("N + 1이 의심되는 api 정보: " +
                        NPlusOneSuspiciousDto.builder()
                                .url(url)
                                .duration(duration)
                                .query2cnt(query2cnt)
                                .build())

                );
                return;
            }
        }
    }
}
