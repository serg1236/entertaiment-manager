package com.epam.spring.core.soap.server;

import com.epam.spring.core.service.UserService;
import com.epam.spring.core.soap.model.GetUserRequest;
import com.epam.spring.core.soap.model.GetUserResponse;
import com.epam.spring.core.soap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

/**
 * Created by Sergiy_Dakhniy
 */
@Endpoint
public class UserEndpoint{

    @Autowired
    private UserService userService;

    @PayloadRoot(namespace = "http://www.dakhniy.com/schema", localPart="getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) throws DatatypeConfigurationException {
        com.epam.spring.core.model.User user = userService.getUserByEmail(request.getEmail());
        GetUserResponse response = new GetUserResponse();
        response.setUser(remapUser(user));
        return response;
    }


    private User remapUser(com.epam.spring.core.model.User user) throws DatatypeConfigurationException {
        User soapUser = new User();
        soapUser.setEmail(user.getEmail());
        soapUser.setName(user.getName());

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(user.getBirthDate());
        XMLGregorianCalendar soapDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        soapUser.setBirthDate(soapDate);

        soapUser.setMoney(user.getMoney());
        return soapUser;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
