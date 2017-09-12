package com.textbookvalet.ui.www.springboot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.textbookvalet.rest.converters.CustomJsonConverter;
import com.textbookvalet.rest.converters.StringToIntegerConverter;
import com.textbookvalet.rest.converters.StringToLongConverter;

@Configuration 
@ComponentScan(basePackages = "com.textbookvalet")  
public class WebConfig extends WebMvcConfigurerAdapter { 
	
	/*
	 * Register REST converters that will convert JSON to JAVA and JAVA to JSON.
	 * We are using gson but can use anything
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(byteArrayHttpMessageConverter());
		converters.add(new CustomJsonConverter());
		converters.add(new MappingJackson2HttpMessageConverter());
		 
		super.configureMessageConverters(converters);
	} 
	
	/*
	 * Register Form Submission converters that will convert Request Parameters (submitted via Form Submission) to JAVA.
	 */
	@Override
    public void addFormatters (FormatterRegistry registry) {
        registry.addConverter(new StringToIntegerConverter());
        registry.addConverter(new StringToLongConverter());
    }
     
    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        arrayHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
        return arrayHttpMessageConverter;
    }
     
    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.IMAGE_JPEG);
        list.add(MediaType.IMAGE_PNG);
        list.add(MediaType.APPLICATION_OCTET_STREAM);
        
        return list;
    }  
     
}