package shoppingmall.web.common.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.util.*;

// Multipart -Form 로깅 목적 Custom 객체
public class CustomContentCachingRequestWrapper extends HttpServletRequestWrapper {
    private byte[] cachedBody;
    private final Map<String, List<String>> cachedHeaders = new HashMap<>();


    public CustomContentCachingRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // Request의 Body를 캐싱
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            List<String> valueLists = new ArrayList<>();
            while (headerValues.hasMoreElements()) {
                valueLists.add(headerValues.nextElement());
            }
            cachedHeaders.put(headerName, valueLists);
        }
    }




    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedServletInputStream(this.cachedBody);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    public byte[] getCachedBody() {
        return this.cachedBody;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = cachedHeaders.getOrDefault(name, Collections.emptyList());
        return Collections.enumeration(values);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(cachedHeaders.keySet());
    }

    private static class CachedServletInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public CachedServletInputStream(byte[] cachedBody) {
            this.inputStream = new ByteArrayInputStream(cachedBody);
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }


        @Override
        public void setReadListener(ReadListener readListener) {
            // 비동기 요청 지원하지 않음
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }


    }
}
