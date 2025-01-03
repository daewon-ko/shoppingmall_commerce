package shoppingmall.web.common.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Profile({"local", "dev", "prod"})
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReqResLoggingFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID = "request_id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomContentCachingRequestWrapper requestWrapper = new CustomContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long endTime = System.currentTimeMillis();

        try {
            String requestId = UUID.randomUUID().toString().substring(0, 8);
            MDC.put(REQUEST_ID, requestId);
            log.info("""
                            |
                            |[REQUEST] {} - {} {} - {}
                            |>>HEADERS : {}
                            |>>REQUEST PARAMS : {}
                            |>>REQUEST BODY : {}
                            |>>RESPONSE BODY : {}
                            """, request.getMethod(), request.getRequestURI(), responseWrapper.getStatus(),
                    (endTime - startTime) / 1000.0, getHeaders(request), getRequestParams(request),
                    getRequestBody(requestWrapper), getResponseBody(responseWrapper));
            responseWrapper.copyBodyToResponse();
        } catch (Exception ex) {
            log.error("[" + this.getClass().getSimpleName() + "] Logging 실패", ex);
        } finally {
            MDC.remove(REQUEST_ID);
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(headerName -> headerName, request::getHeader));
    }

    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        return params.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> String.join(", ", entry.getValue())));


    }

    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(requestWrapper,
                ContentCachingRequestWrapper.class);
        if (wrapper != null && wrapper.getContentLength() > 0) {
            return formatJson(new String(wrapper.getContentAsByteArray()));
        }
        return "";
    }

    private String getRequestBody(final HttpServletRequest request) {
        CustomContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request,
                CustomContentCachingRequestWrapper.class);
        if (wrapper != null && wrapper.getContentLength() > 0) {
            String body = new String(wrapper.getCachedBody());
            if (request.getContentType() != null && request.getContentType().startsWith("multipart/form-data")) {
                return parseMultipartFormData(body);
            }
            return formatJson(body);
        }
        return "";
    }

    private String parseMultipartFormData(String body) {
        // 파일 관련 데이터 제외하고 필드 이름과 값만 추출
        StringBuilder result = new StringBuilder();
        String[] parts = body.split("----------------------------");
        for (String part : parts) {
            if (part.contains("Content-Disposition") && part.contains("name=")) {
                if (!part.contains("filename=")) {
                    // 파일이 아닌 일반 필드 값 로깅
                    int startIndex = part.indexOf("\r\n\r\n") + 4;
                    int endIndex = part.lastIndexOf("\r\n");
                    String fieldValue = part.substring(startIndex, endIndex).trim();
                    result.append(fieldValue).append("\n");
                } else {
                    // 파일 데이터는 이름만 로깅
                    String filename = part.contains("filename=")
                            ? part.substring(part.indexOf("filename=") + 10).split("\"")[0]
                            : "unknown file";
                    result.append(parts[0]);
                    result.append("File uploaded: ").append(filename).append("\n");


                }
            }
        }
        return result.toString();
    }



    private String getResponseBody(final HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response,
                ContentCachingResponseWrapper.class);
        if (wrapper != null && wrapper.getContentSize() > 0) {
            return formatJson(new String(wrapper.getContentAsByteArray()));
        }
        return "";
    }

    private String formatJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            return json; // 포맷 실패 시 원래 문자열 반환
        }
    }

}



