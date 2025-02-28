package shoppingmall.domainrdb.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import shoppingmall.domainrdb.common.JpaInspector;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class JpaConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        // Hibernate 설정을 직접 추가
        Map<String, Object> properties = new HashMap<>();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("shoppingmall.domainrdb"); // 엔티티 패키지 지정
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        properties.put("hibernate.session_factory.statement_inspector", new JpaInspector());

        factoryBean.setJpaPropertyMap(properties);

        // Hibernate 설정

        properties.put("hibernate.hbm2ddl.auto", "none"); // 테이블 생성 전략
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
//        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return factoryBean;
    }

    @Bean
//    @Primary // 기본 EntityManager 지정
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public JpaInspector jpaInspector() {
        return new JpaInspector();
    }
}