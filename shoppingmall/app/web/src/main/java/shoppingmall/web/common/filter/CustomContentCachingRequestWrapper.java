package shoppingmall.web.common.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;

// Multipart -Form 로깅 목적 Custom 객체
public class CustomContentCachingRequestWrapper extends HttpServletRequestWrapper {
    private byte[] cachedBody;

    public CustomContentCachingRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // Request의 Body를 캐싱
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
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
