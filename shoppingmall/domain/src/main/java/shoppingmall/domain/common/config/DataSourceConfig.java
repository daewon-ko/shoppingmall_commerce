package shoppingmall.domain.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;

@Profile({"dev", "prd", "local"})
@Configuration
public class DataSourceConfig {

    private static final String MASTER_DB = "master";
    private static final String SLAVE_DB = "slave";

    @Bean
    @Qualifier(MASTER_DB)
    @ConfigurationProperties(prefix = "spring.datasource.master.hikari")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier(SLAVE_DB)
    @ConfigurationProperties(prefix = "spring.datasource.slave.hikari")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource routingDataSource(@Qualifier(MASTER_DB) final DataSource masterDataSource,
                                        @Qualifier(SLAVE_DB) final DataSource slaveDataSource) {
        final RoutingDataSource routingDataSource = new RoutingDataSource();
        final HashMap<Object, Object> targetDataSources = new HashMap<>();

        targetDataSources.put(MASTER_DB, masterDataSource);
        targetDataSources.put(SLAVE_DB, slaveDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;

    }

    @Bean
    @Primary
    public DataSource dataSource() {
        final DataSource determinedDataSource = routingDataSource(masterDataSource(), slaveDataSource());
        return new LazyConnectionDataSourceProxy(determinedDataSource);
    }


}
