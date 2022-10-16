package com.github.yuriiyukh.controllers.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import com.github.yuriiyukh.dao.DaoException;
import com.github.yuriiyukh.reader.PropertiesReader;

@Configuration
@ComponentScan("com.github.yuriiyukh")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
    
    @Bean
    public DataSource dataSource() throws DaoException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        PropertiesReader propertiesReader = new PropertiesReader("db_configuration.properties");
        dataSource.setDriverClassName(propertiesReader.readData("driverUrl"));
        dataSource.setUrl(propertiesReader.readData("dbUrl"));
        dataSource.setUsername(propertiesReader.readData("dbUsername"));
        dataSource.setPassword(propertiesReader.readData("dbPassword"));
        
        return dataSource;
    }
    
    @Bean 
    public JdbcTemplate jdbcTemplate() throws DaoException {
        return new JdbcTemplate(dataSource());
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }

}
