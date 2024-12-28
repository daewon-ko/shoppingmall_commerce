package shoppingmall.domain.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static shoppingmall.domain.common.config.DataSourceType.*;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        final String currentTransactionName = TransactionSynchronizationManager.getCurrentTransactionName();
        final boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        if (readOnly) {
            log.info(currentTransactionName + "Transaction is read-only, SLAVE DB 서버로 요청합니다.");
            return SLAVE;
        }

        log.info(currentTransactionName + "Transaction is Not ReadOnly, Master DB 서버로 요청합니다.");
        return MASTER;
    }
}
