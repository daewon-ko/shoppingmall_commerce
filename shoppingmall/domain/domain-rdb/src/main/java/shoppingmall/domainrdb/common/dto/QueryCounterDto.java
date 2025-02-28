package shoppingmall.domainrdb.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@ToString
public class QueryCounterDto {
    private List<String> queries;
    private Long executionTime;
    private int cnt;

    @Builder
    public QueryCounterDto(final List<String> queries, final Long executionTime, final int cnt) {
        this.queries = queries;
        this.executionTime = executionTime;
        this.cnt = cnt;
    }

    public void appendQuery(final String sql) {
        queries.add(sql);
        cnt++;
    }

    public Map<String, Integer> getQuery2cnt() {
        return queries.stream().collect(Collectors.toMap(Function.identity(), v -> 1, Integer::sum));
    }
}
