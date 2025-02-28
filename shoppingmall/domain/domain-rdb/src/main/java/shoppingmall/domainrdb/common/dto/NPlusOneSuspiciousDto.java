package shoppingmall.domainrdb.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter

public class NPlusOneSuspiciousDto {
    private String url;
    private Long duration;
    private Map<String, Integer> query2cnt;

    @Builder
    private NPlusOneSuspiciousDto(String url, Long duration, Map<String, Integer> query2cnt) {
        this.url = url;
        this.duration = duration;
        this.query2cnt = query2cnt;
    }

    @Override
    public String toString() {


        StringBuilder sb = new StringBuilder("NPlusOneSuspiciousDto{");
        sb.append("url='").append(url).append('\'');
        sb.append(", duration=").append(duration);
        sb.append(", query2cnt={\n");
        for (Map.Entry<String, Integer> entry : query2cnt.entrySet()) {
            sb.append("호출된 횟수: ").append(entry.getValue()).append(", ")
                    .append("호출된 쿼리: ")
                    .append(entry.getKey())
                    .append(",\n");
        }
        sb.deleteCharAt(sb.length() - 2); // 마지막 ", " 삭제
        sb.append('}');
        return sb.toString();
    }

}
