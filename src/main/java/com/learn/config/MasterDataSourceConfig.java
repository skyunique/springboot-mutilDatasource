package com.learn.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Master主数据源配置类
 */
@Configuration
@MapperScan(value = MasterDataSourceConfig.PACKAGE,sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {
    /*其中这两个注解说明下:

    @Primary ：标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean
    优先被考虑。多数据源配置的时候注意，必须要有一个主数据源，
    用 @Primary 标志该 Bean。
    @MapperScan： 扫描 Mapper 接口并容器管理。

    需要注意的是sqlSessionFactoryRef 表示定义一个唯一 SqlSessionFactory 实例。

    @Autowired默认是根据类型进行注入的，
    因此如果有多个类型一样的Bean候选者，则需要限定其中一个候选者，否则将抛出异常

    @Qualifier限定描述符除了能根据名字进行注入，
    更能进行更细粒度的控制如何选择候选者

    @Qualifier：当一个接口实现类有@Server("service")和@Server("service1")多个，
    可通过该注解告诉使用声明使用的那个实现类，例如：

        @Autowired
        @Qualifier("service1")
        private UserService userService;

    */
    static final String PACKAGE = "com.learn.dao.master";
    static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

    //master数据源
    @Value("${master.datasource.url}")
    private String url;

    @Value("${master.datasource.username}")
    private String username;

    @Value("${master.datasource.password}")
    private String password;

    @Value("${master.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.filters}")
    private String filters;

    @Value("{spring.datasource.connectionProperties}")
    private String connectionProperties;

    //注入MasterDataSource
    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        //具体配置
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.setConnectionProperties(connectionProperties);
        return dataSource;
    }

    //注入MasterDataSource事务管理
    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager(){
      return new DataSourceTransactionManager(masterDataSource());
    }

    //注入MasterDataSource数据源的SqlSessionFactory
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);

        //分页插件 部分参数设置
        Interceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        //数据库
        properties.setProperty("helperDialect", "mysql");
        //是否将参数offset作为PageNum使用
        properties.setProperty("offsetAsPageNum", "true");
        //是否进行count查询
        properties.setProperty("rowBoundsWithCount", "true");
        //是否分页合理化
        properties.setProperty("reasonable", "false");
        interceptor.setProperties(properties);
        sessionFactory.setPlugins(new Interceptor[] {interceptor});
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}