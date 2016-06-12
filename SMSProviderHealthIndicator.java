package com.africasys.contact.config.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.util.Assert;

import com.africasys.contact.service.ContactService;

/**
 * @author zoumana
 * @since 20160612
 * 
 * AfricaSys custom HealthIndicator check for our FAI SMS provider APIs
 */
public class SMSProviderHealthIndicator extends AbstractHealthIndicator {

    private final Logger log = LoggerFactory.getLogger(SMSProviderHealthIndicator.class);
    private ContactService contactService; 
    
    public SMSProviderHealthIndicator(ContactService contactService) {
        Assert.notNull(contactService, "contactService must not be null");
        this.contactService = contactService;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        log.debug("Initializing SMSProvider health indicator");
        try {
        	contactService.getSmsService().getBalance();
            builder.up();

        } catch (Exception e) {
            log.debug("Cannot connect to SMS API server. Error: {}", e.getMessage());
            builder.down(e);
        }
    }
}
