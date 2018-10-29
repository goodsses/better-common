//package com.better.common.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//
///**
// * @author 陈平
// * @version 1.0
// * @since 16/8/29
// */
//@Configuration
//public class DatabaseConfig {
//
//    private static Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);
//
//    @Bean(name = "priDs")
//    @Qualifier("priDs")
//    @Primary
//    public DataSource primaryDataSource(@Value("${spring.datasource.url}") String url,
//                                        @Value("${spring.datasource.du}") String username,
//                                        @Value("${spring.datasource.dad}") String password,
//                                        @Value("${spring.datasource.ini}") int ini,
//                                        @Value("${spring.datasource.min}") int min,
//                                        @Value("${spring.datasource.max}") int max) {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setUrl(url);
//        druidDataSource.setUsername(username);
//        druidDataSource.setPassword(password);
//        druidDataSource.setInitialSize(ini);//1
//        druidDataSource.setMinIdle(min);//1
//        druidDataSource.setMaxActive(max);//20
//        druidDataSource.setMaxWait(60000);
//        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
//        druidDataSource.setMinEvictableIdleTimeMillis(300000);
//        druidDataSource.setValidationQuery("SELECT 'x' FROM DUAL");
//        druidDataSource.setTestWhileIdle(true);
//        druidDataSource.setTestOnBorrow(false);
//        druidDataSource.setTestOnReturn(false);
//        /*try {
//            druidDataSource.setFilters("wall,stat");
//        } catch (SQLException e) {
//            LOGGER.error("", e);
//        }*/
//        return druidDataSource;
//    }
//
//    @Bean(name = "primaryJdbc")
//    public JdbcTemplate primaryJdbcTemplate(
//            @Qualifier("priDs") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    /**
//     * SqlSessionFactory是mybatis依赖，用来做增删改查
//     *
//     * @param ds                 连接池
//     * @param typeAliasesPackage 实体类存放的位置，永存对象、表映射（orm），多个typeAliases用“，”隔开
//     * @param mapperLocations    mapper文件存放的位置，多个mapper用“，”隔开
//     * @return SqlSessionFactory
//     * @throws Exception
//     */
//    @Bean(name = "priSf")
//    @Qualifier("priSf")
//    @Primary
//    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "priDs") DataSource ds,
//                                               @Value("${mybatis.typeAliasesPackage}") String typeAliasesPackage,
//                                               @Value("${mybatis.mapperLocations}") String mapperLocations
//    ) throws Exception {
//        return getMyBatisSession(ds, typeAliasesPackage, mapperLocations);
//    }
//
//    /**
//     * tap 数据源
//     *
//     * @param url      url
//     * @param username username
//     * @param password password
//     * @return DataSource
//     */
//    @Bean(name = "tapDs")
//    @Qualifier("tapDs")
//    public DataSource tapDataSource(@Value("${custom.datasource.tapDs.url}") String url,
//                                    @Value("${custom.datasource.tapDs.du}") String username,
//                                    @Value("${custom.datasource.tapDs.dad}") String password) {
//        return this.setDs(url, username, password);
//    }
//
//    @Bean(name = "tapJdbc")
//    public JdbcTemplate tapJdbcTemplate(
//            @Qualifier("tapDs") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    /**
//     * sms 数据源
//     *
//     * @param url      url
//     * @param username username
//     * @param password password
//     * @return DataSource
//     */
//    @Bean(name = "smsDs")
//    @Qualifier("smsDs")
//    public DataSource smsDataSource(@Value("${custom.datasource.smsDs.url}") String url,
//                                    @Value("${custom.datasource.smsDs.du}") String username,
//                                    @Value("${custom.datasource.smsDs.dad}") String password) {
//        return this.setDs(url, username, password);
//    }
//
//    @Bean(name = "smsJdbc")
//    public JdbcTemplate smsJdbcTemplate(
//            @Qualifier("smsDs") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    private DruidDataSource setDs(String url, String username, String password) {
//        return this.setDs(url, username, password, 2, 40);
//    }
//
//    private DruidDataSource setDs(String url, String username, String password, Integer iniSize, Integer maxWait) {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setUrl(url);
//        druidDataSource.setUsername(username);
//        druidDataSource.setPassword(password);
//        if (iniSize != null) {
//            druidDataSource.setInitialSize(iniSize);//1
//        } else {
//            druidDataSource.setInitialSize(2);//1
//        }
//        druidDataSource.setMinIdle(1);//1
//        if (maxWait != null) {
//            druidDataSource.setMaxActive(maxWait);//20
//        } else {
//            druidDataSource.setMaxActive(40);//20
//        }
//        druidDataSource.setMaxWait(60000);
//        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
//        druidDataSource.setMinEvictableIdleTimeMillis(300000);
//        druidDataSource.setValidationQuery("SELECT 'x' FROM DUAL");
//        druidDataSource.setTestWhileIdle(true);
//        druidDataSource.setTestOnBorrow(false);
//        druidDataSource.setTestOnReturn(false);
//        try {
//            druidDataSource.setFilters("wall,stat");
//        } catch (SQLException e) {
//            LOGGER.error("", e);
//        }
//        return druidDataSource;
//    }
//
//    private SqlSessionFactory getMyBatisSession(DataSource ds, String typeAliasesPackage, String mapperLocations) throws Exception {
//        SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
//        sfb.setDataSource(ds);
//        sfb.setTypeAliasesPackage(typeAliasesPackage);
//        sfb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
//        return sfb.getObject();
//    }
//
//
//    /**
//     * fund 财会数据源
//     *
//     * @param url      url
//     * @param username username
//     * @param password password
//     * @return DataSource
//     */
//    @Bean(name = "fundDs")
//    @Qualifier("fundDs")
//    public DataSource fundDataSource(@Value("${custom.datasource.fundDs.url}") String url,
//                                     @Value("${custom.datasource.fundDs.du}") String username,
//                                     @Value("${custom.datasource.fundDs.dad}") String password) {
//        return this.setDs(url, username, password);
//    }
//
//    @Bean(name = "fundJdbc")
//    public JdbcTemplate fundJdbcTemplate(
//            @Qualifier("fundDs") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//
//    /**
//     * wind 数据源
//     *
//     * @param url      url
//     * @param username username
//     * @param password password
//     * @return DataSource
//     */
//    @Bean(name = "windDs")
//    @Qualifier("windDs")
//    public DataSource windSource(@Value("${custom.datasource.windDs.url}") String url,
//                                 @Value("${custom.datasource.windDs.du}") String username,
//                                 @Value("${custom.datasource.windDs.dad}") String password) {
//        return this.setDs(url, username, password);
//    }
//
//    @Bean(name = "windJdbc")
//    public JdbcTemplate windJdbcTemplate(@Qualifier("windDs") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    /**
//     * 网站数据库mybatis
//     *
//     * @param ds
//     * @param typeAliasesPackage
//     * @param mapperLocations
//     * @return
//     * @throws Exception
//     */
//    @Bean(name = "ecpSf")
//    @Qualifier("ecpSf")
//    public SqlSessionFactory ecpSessionFactory(@Qualifier(value = "ecpDs") DataSource ds,
//                                               @Value("${mybatis.typeAliasesPackage}") String typeAliasesPackage,
//                                               @Value("${mybatis.mapperLocations}") String mapperLocations
//    ) throws Exception {
//        return this.getMyBatisSession(ds, typeAliasesPackage, mapperLocations);
//    }
//
//    /**
//     * 网站数据库DS
//     *
//     * @param url
//     * @param username
//     * @param password
//     * @return
//     * @throws SQLException
//     */
//    @Bean(name = "ecpDs")
//    public DataSource ecpDataSource(@Value("${custom.datasource.ecpapp.url}") String url,
//                                    @Value("${custom.datasource.ecpapp.du}") String username,
//                                    @Value("${custom.datasource.ecpapp.dad}") String password) throws SQLException {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setUrl(url);
//        druidDataSource.setUsername(username);
//        druidDataSource.setPassword(password);
//        druidDataSource.setInitialSize(2);//1
//        druidDataSource.setMinIdle(1);//1
//        druidDataSource.setMaxActive(40);//20
//        druidDataSource.setMaxWait(60000);
//        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
//        druidDataSource.setMinEvictableIdleTimeMillis(300000);
//        druidDataSource.setValidationQuery("SELECT 'x' FROM DUAL");
//        druidDataSource.setTestWhileIdle(true);
//        druidDataSource.setTestOnBorrow(false);
//        druidDataSource.setTestOnReturn(false);
////        druidDataSource.setFilters("wall");
//        return druidDataSource;
//    }
//
//    /**
//     * 财会数据库mybatis
//     *
//     * @param ds
//     * @param typeAliasesPackage
//     * @param mapperLocations
//     * @return
//     * @throws Exception
//     */
//    @Bean(name = "fatSf")
//    @Qualifier("fatSf")
//    public SqlSessionFactory fatSessionFactory(@Qualifier(value = "fatDs") DataSource ds,
//                                               @Value("${mybatis.typeAliasesPackage}") String typeAliasesPackage,
//                                               @Value("${mybatis.mapperLocations}") String mapperLocations
//    ) throws Exception {
//        return this.getMyBatisSession(ds, typeAliasesPackage, mapperLocations);
//    }
//
//    /**
//     * 财会数据库DS
//     *
//     * @param url
//     * @param username
//     * @param password
//     * @return
//     * @throws SQLException
//     */
//    @Bean(name = "fatDs")
//    public DataSource fatDataSource(@Value("${custom.datasource.fatodc.url}") String url,
//                                    @Value("${custom.datasource.fatodc.du}") String username,
//                                    @Value("${custom.datasource.fatodc.dad}") String password) throws SQLException {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setUrl(url);
//        druidDataSource.setUsername(username);
//        druidDataSource.setPassword(password);
//        druidDataSource.setInitialSize(2);//1
//        druidDataSource.setMinIdle(1);//1
//        druidDataSource.setMaxActive(40);//20
//        druidDataSource.setMaxWait(60000);
//        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
//        druidDataSource.setMinEvictableIdleTimeMillis(300000);
//        druidDataSource.setValidationQuery("select 1");
//        druidDataSource.setTestWhileIdle(true);
//        druidDataSource.setTestOnBorrow(false);
//        druidDataSource.setTestOnReturn(false);
////        druidDataSource.setFilters("wall");
//        return druidDataSource;
//    }
//
//    @Bean(name = "fatJdbc")
//    public JdbcTemplate fatJdbcTemplate(
//            @Qualifier("fatDs") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Bean(name = "priDsJdbc")
//    public JdbcTemplate priDsJdbcTemplate(
//            @Qualifier("priDs") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//}
