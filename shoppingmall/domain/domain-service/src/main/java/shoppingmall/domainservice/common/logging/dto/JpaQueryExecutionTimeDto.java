package shoppingmall.domainservice.common.logging.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JpaQueryExecutionTimeDto {
    private final String query;
    private final Long executionTime;

    @Builder
    private JpaQueryExecutionTimeDto(String query, Long executionTime) {
        this.query = query;
        this.executionTime = executionTime;
    }

    @Override
    public String toString() {
        return "JpaQueryExecutionTimeDto{" +
                "query='" + query + '\n' +
                ", executionTime=" + executionTime +
                '}';
    }
}
