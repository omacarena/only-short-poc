package my.java.spring.jpa.multi.repositories.config;

import my.java.spring.jpa.multi.models.Organization;
import my.java.spring.jpa.multi.repositories.interfaces.OrganizationRepository;
import my.java.spring.jpa.multi.repositories.source2.Source2OrganizationRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Repository configuration.
 * @EnableJpaRepositories to enable JPA automatic repositories.
 */
@Configuration
@EnableJpaRepositories(
        basePackageClasses = { Source2OrganizationRepositoryImpl.class },
        entityManagerFactoryRef = MySqlSource2Config.EntityManagerFactoryBeanName,
        transactionManagerRef = MySqlSource2Config.TransactionManagerBeanName
)
public class MySqlSource2Config {

    public static final String PrefixName = "source2";
    public static final String DataSourceBeanName = PrefixName + ".data-source";
    public static final String JpaVendorAdapterBeanName = PrefixName + "jpa-vendor-adapter";
    public static final String EntityManagerFactoryBeanName = PrefixName + ".entity-manager-factory";
    public static final String TransactionManagerBeanName = PrefixName + ".transaction-manager";
    public static final String RepositoryBeanName = PrefixName + ".repository";

    @Bean(TransactionManagerBeanName)
    public PlatformTransactionManager transactionManager(
            @Qualifier(EntityManagerFactoryBeanName) EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(EntityManagerFactoryBeanName)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier(DataSourceBeanName) DataSource dataSource,
            @Qualifier(JpaVendorAdapterBeanName) JpaVendorAdapter vendorAdapter) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactoryBean.setPackagesToScan(Organization.class.getPackage().getName());
        entityManagerFactoryBean.setPersistenceUnitName("mysqlsource2");
        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean;
    }

    @Bean(JpaVendorAdapterBeanName)
    @ConfigurationProperties(prefix = "source2.mysql.jpa")
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean(DataSourceBeanName)
    @ConfigurationProperties(prefix = "source2.mysql.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}