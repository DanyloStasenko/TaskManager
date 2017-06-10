package com.springapp.mvc.services;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@Component
@DirtiesContext
@WebAppConfiguration
public class UserServiceTest {

    @Autowired
    protected SessionFactory sessionFactory;

    @Test
    public void testGetUsersList() throws Exception {
        System.out.println(sessionFactory);
    }
}