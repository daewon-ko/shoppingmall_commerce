package shoppingmall.domainrdb.common;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import shoppingmall.domainrdb.common.dto.QueryCounterDto;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class JpaInspector implements StatementInspector {
    private static final ThreadLocal<QueryCounterDto> threadLocal = new ThreadLocal<>();

    @Override
    public String inspect(String sql) {
        QueryCounterDto queryCounterDto = threadLocal.get();
        if (Objects.nonNull(queryCounterDto) && sql.toLowerCase().startsWith("select")) {
            queryCounterDto.appendQuery(sql);
        }
        return sql;
    }

    public void start() {
        threadLocal.set(new QueryCounterDto(new ArrayList<>(), System.currentTimeMillis(), 0));
    }


    public void clear() {
        threadLocal.remove();
    }


    public long getDuration() {
        return System.currentTimeMillis() - threadLocal.get().getExecutionTime();
    }


    public Map<String, Integer> getQuery2Cnt() {
        return threadLocal.get().getQuery2cnt();
    }

}
