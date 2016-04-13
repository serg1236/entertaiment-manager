package com.epam.spring.core.soap.test;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.soap.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:soap-client.xml")
public class SoapServerTest {
 
    @Autowired
    private WebServiceTemplate webServiceTemplate;
 
    @Test
    public void testUserService() {

        GetUserRequest request = new ObjectFactory().createGetUserRequest();
        request.setEmail("admin@em.com");
        GetUserResponse response = (GetUserResponse) webServiceTemplate
                .marshalSendAndReceive(request);

        assertEquals("admin@em.com", response.getUser().getEmail());
        assertEquals("Chuck Norris", response.getUser().getName());
    }

    @Test
    public void testEventService() {
        GetEventRequest request = new ObjectFactory().createGetEventRequest();
        request.setName("EPAM Spring sailing");
        GetEventResponse response = (GetEventResponse) webServiceTemplate
                .marshalSendAndReceive(request);

        assertEquals("EPAM Spring sailing", response.getEvent().getName());
        assertEquals(EventRating.HIGH, response.getEvent().getRating());
    }

    public void setWebServiceTemplate(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }
}