package com.textbookvalet.ui.www.springboot;

import org.flywaydb.core.Flyway;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.textbookvalet")
@Configuration
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
@EnableJpaRepositories(basePackages = "com.textbookvalet")
@EntityScan("com.textbookvalet.*")
@EnableSwagger2 
public class Application extends SpringBootServletInitializer {

	public static final String BASE_REST_API = "/api";

	@Autowired
	private ApplicationContext context; 	

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	// Add separate child context just for RESTful services
	@Bean
	public ServletRegistrationBean restApiV1() {
        
         //Use this context if you want to use Spring XML configuration file  
       /* XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
        applicationContext.setParent(context);
        applicationContext.setConfigLocation("classpath:/rest.xml"); */

        //Create non-xml based ApplicationContext and set main application context as it's parents so this child context has access to all the beans registered with parent context.
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.setParent(context);
        applicationContext.register(WebConfig.class); 

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setApplicationContext(applicationContext);

        //URL this child context should listen to
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, Application.BASE_REST_API + "/v1/*");
        servletRegistrationBean.setName("restApiV1");

        return servletRegistrationBean;
    }

	@Bean
	@Profile({ "default", "development" })
	public FlywayMigrationStrategy cleanMigrateStrategy() {
		FlywayMigrationStrategy strategy = new FlywayMigrationStrategy() {
			@Override
			public void migrate(Flyway flyway) {
				flyway.clean();
				flyway.migrate();
			}
		};

		return strategy;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipart = new CommonsMultipartResolver();
		multipart.setMaxUploadSize(3 * 1024 * 1024);
		return multipart;
	}

	@Bean
	@Order(0)
	public MultipartFilter multipartFilter() {
		MultipartFilter multipartFilter = new MultipartFilter();
		multipartFilter.setMultipartResolverBeanName("multipartResolver");
		return multipartFilter;
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	} 
	
	@Bean
	public Docket createRestApi() {
		  
		 return new Docket(DocumentationType.SWAGGER_2) 
	                .apiInfo(apiInfo())
	                .pathMapping(Application.BASE_REST_API + "/v1/") //Base URL of the APIs
	                .select() 
	                .apis(RequestHandlerSelectors.basePackage("com.textbookvalet.rest")) //which package to scan for APIs
	                .paths(PathSelectors.any())
	                .build();
	                
	} 

	private ApiInfo apiInfo() {
		Contact contact = new Contact("TextValet", "https://textbookvalet.com/", "admin@textbookvalet.com");
		return new ApiInfoBuilder()
				.title("Build RESTful APIs with Swagger2")
				.description("more info, pelase https://textbookvalet.com")
				.termsOfServiceUrl("https://textbookvalet.com")
				.contact(contact).version("1.0").build();
	} 

}