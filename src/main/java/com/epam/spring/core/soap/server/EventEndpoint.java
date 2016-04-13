package com.epam.spring.core.soap.server;

import com.epam.spring.core.model.*;
import com.epam.spring.core.service.EventService;
import com.epam.spring.core.soap.model.*;
import com.epam.spring.core.soap.model.Event;
import com.epam.spring.core.soap.model.EventRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

/**
 * Created by Sergiy_Dakhniy
 */

@Endpoint
public class EventEndpoint {
    @Autowired
    private EventService eventService;


    @PayloadRoot(namespace = "http://www.dakhniy.com/schema", localPart="getEventRequest")
    @ResponsePayload
    public GetEventResponse getEvent(@RequestPayload GetEventRequest request) throws DatatypeConfigurationException {
        com.epam.spring.core.model.Event event = eventService.getByName(request.getName());
        GetEventResponse response = new GetEventResponse();
        response.setEvent(remapEvent(event));
        return response;
    }


    private Event remapEvent(com.epam.spring.core.model.Event event) throws DatatypeConfigurationException {
        Event soapEvent = new Event();
        soapEvent.setName(event.getName());
        soapEvent.setBasePrice(event.getBasePrice());
        soapEvent.setRating(EventRating.fromValue(event.getRating().name()));
        return soapEvent;
    }

    @PostConstruct
    void insertTestEvent() {
        com.epam.spring.core.model.Event event = new com.epam.spring.core.model.Event();
        event.setName("EPAM Spring sailing");
        event.setBasePrice(1300);
        event.setRating(com.epam.spring.core.model.EventRating.HIGH);
        eventService.create(event);
    }

    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
