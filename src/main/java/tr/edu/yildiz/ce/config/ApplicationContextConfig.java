package tr.edu.yildiz.ce.config;
 
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import tr.edu.yildiz.ce.dao.UserDAO;
import tr.edu.yildiz.ce.dao.UserRoleDAO;
import tr.edu.yildiz.ce.dao.impl.UserDAOImpl;
import tr.edu.yildiz.ce.dao.impl.UserRoleDAOImpl;

@Configuration
@ComponentScan("tr.edu.yildiz.ce.*")
@EnableTransactionManagement
// Load to Environment.
@PropertySource("classpath:datasource-cfg.properties")
public class ApplicationContextConfig {
 
  // The Environment class serves as the property holder
  // and stores all the properties loaded by the @PropertySource
  @Autowired
  private Environment env;
 
 // @Autowired
//  private UserInfoDAO userInfoDAO;
  // created for validation(will fill)
 /* @Bean
  public ResourceBundleMessageSource messageSource() {
      ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
      // Load property in message/validator.properties
      rb.setBasenames(new String[] { "messages/validator" });
      return rb;
  }*/
  
  @Bean(name = "userDAO")
  public UserDAO getUserDAO() {
      return new UserDAOImpl();
  }
  
  @Bean(name = "userRoleDAO")
  public UserRoleDAO getUserRoleDAO() {
      return new UserRoleDAOImpl();
  }
 
  @Bean(name = "viewResolver")
  public InternalResourceViewResolver getViewResolver() {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setPrefix("/WEB-INF/pages/");
      viewResolver.setSuffix(".jsp");
      return viewResolver;
  }
 
  @Bean(name = "dataSource")
  public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
 
      // See: datasouce-cfg.properties
      dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
      dataSource.setUrl(env.getProperty("ds.url"));
      dataSource.setUsername(env.getProperty("ds.username"));
      dataSource.setPassword(env.getProperty("ds.password"));
 
      System.out.println("## getDataSource: " + dataSource);
 
      return dataSource;
  }
  
  @Autowired
  @Bean(name = "sessionFactory")
  public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
      Properties properties = new Properties();
     
      // See: ds-hibernate-cfg.properties
      properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
      properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      properties.put("current_session_context_class", env.getProperty("current_session_context_class"));
       
  
      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
      factoryBean.setPackagesToScan(new String[] { "tr.edu.yildiz.ce.entity" });
      factoryBean.setDataSource(dataSource);
      factoryBean.setHibernateProperties(properties);
      factoryBean.afterPropertiesSet();
      //
      SessionFactory sf = factoryBean.getObject();
      return sf;
  }
  
  @Autowired
  @Bean(name = "transactionManager")
  public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
      HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
  
      return transactionManager;
  }
 
  // Transaction Manager
/*  @Autowired
  @Bean(name = "transactionManager")
  public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
      DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
 
      return transactionManager;
  }*/
  
}