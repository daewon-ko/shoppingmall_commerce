package shoppingmall.web.common.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Profile({"local", "dev", "prod"})
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ReqResLoggingFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID = "request_id";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MultipartResolver multipartResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        if (isMultipartContent(request)) {
            HttpServletRequest processedRequest = request;
            if (multipartResolver.isMultipart(request)) {
                processedRequest = multipartResolver.resolveMultipart(request);
            }
            logMultipartRequest(processedRequest, response, filterChain);
        } else {
            logStandardRequest(request, response, filterChain);
        }
    }

    private void logStandardRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        CustomContentCachingRequestWrapper requestWrapper = new CustomContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);
        long endTime = System.currentTimeMillis();

        try {
            MDC.put(REQUEST_ID, generateRequestId());
            logRequestDetails(requestWrapper, responseWrapper, startTime, endTime);
            responseWrapper.copyBodyToResponse();
        } finally {
            MDC.remove(REQUEST_ID);
        }
    }

    private void logMultipartRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        long endTime = System.currentTimeMillis();

        StringBuilder logBuilder = new StringBuilder();

        if (request instanceof MultipartHttpServletRequest multipartRequest) {
            multipartRequest.getParameterMap().forEach((key, values) ->
                    logBuilder.append("[PARAM] ").append(key).append(" = ").append(String.join(", ", values)).append("\n"));

            multipartRequest.getFileMap().forEach((key, file) ->
                    logBuilder.append("[FILE] ").append(key).append(" = ")
                            .append(file.getOriginalFilename()).append(" (").append(file.getSize()).append(" bytes)").append("\n"));
        }

        log.info("""
                |
                [{}] {}
                [Execution Time] {}s
                [HEADERS] {}
                [BODY]
                {}
                """,
                request.getMethod(), request.getRequestURI(),
                (endTime - startTime) / 1000.0, getHeaders(request), logBuilder.toString());
    }

    private void logRequestDetails(HttpServletRequest request, HttpServletResponse response, long startTime, long endTime) {
        log.info("""
                |
                [REQUEST] [{}] {} - Status: {}
                [Execution Time] {}s
                [HEADERS] {}
                [PARAMETERS] {}
                [REQUEST BODY]
                {}
                [RESPONSE BODY]
                {}
                """,
                request.getMethod(), request.getRequestURI(), response.getStatus(),
                (endTime - startTime) / 1000.0, getHeaders(request), getRequestParams(request),
                getRequestBody(request), getResponseBody(response));
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(header -> header, request::getHeader));
    }

    private Map<String, String> getRequestParams(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> String.join(", ", entry.getValue())));
    }

    private String getRequestBody(HttpServletRequest request) {
        if (request instanceof CustomContentCachingRequestWrapper wrapper && wrapper.getContentLength() > 0) {
            return formatJson(new String(wrapper.getCachedBody(), StandardCharsets.UTF_8));
        }
        return "";
    }

    private String getResponseBody(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper wrapper && wrapper.getContentSize() > 0) {
            return formatJson(new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8));
        }
        return "";
    }

    private String formatJson(String json) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                    objectMapper.readValue(json, Object.class));
        } catch (JsonProcessingException e) {
            return json;
        }
    }

    private String generateRequestId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private boolean isMultipartContent(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }
}
