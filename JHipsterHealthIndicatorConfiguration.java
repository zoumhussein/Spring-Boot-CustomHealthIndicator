package com.africasys.contact.config.metrics;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.africasys.contact.service.ContactService;

@Configuration
public class JHipsterHealthIndicatorConfiguration {

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Inject
    private DataSource dataSource;

    @Inject
    ContactService contactService;
    
    @Bean
    public HealthIndicator dbHealthIndicator() {
        return new DatabaseHealthIndicator(dataSource);
    }

    @Bean
    public HealthIndicator mailHealthIndicator() {
        return new JavaMailHealthIndicator(javaMailSender);
    }
    
    /**
     * our custom SMS API health indicator bean
     * 
     * @return SMSProviderHealthIndicator
     */
    @Bean
    public SMSProviderHealthIndicator smsHealthIndicator(){
    	return new SMSProviderHealthIndicator(contactService);
    }
}
