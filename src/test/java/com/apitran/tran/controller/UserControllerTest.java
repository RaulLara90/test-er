package com.apitran.tran.controller;

import com.apitran.tran.controllers.UserController;
import com.apitran.tran.models.dto.ContactDTO;
import com.apitran.tran.models.dto.UserRQDTO;
import com.apitran.tran.models.dto.UserRSDTO;
import com.apitran.tran.services.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    IUserService userService;

    @Mock
    UserRSDTO userRSDTO;

    @Mock
    UserRQDTO userRQDTO;

    @Mock
    ContactDTO contactDTO;


    private static final Long ID = 1L;

    @Test
    public void createUser() {
        doReturn(ResponseEntity.ok(userRSDTO)).when(userService).createUser(any());
        ResponseEntity<?> user = userController.createUser(userRQDTO);
        assertNotNull(user);
    }

    @Test
    public void saveContacts() {
        doReturn(ResponseEntity.ok(userRSDTO)).when(userService).saveContacts(anyLong(), any());
        ResponseEntity<?> user = userController.saveContacts(ID, Collections.singletonList(contactDTO));
        assertNotNull(user);
    }

    @Test
    public void obtainCommonContacts() {
        doReturn(ResponseEntity.ok(userRSDTO)).when(userService).obtainCommonContacts(anyLong(), anyLong());
        ResponseEntity<?> user = userController.obtainCommonContacts(ID, ID);
        assertNotNull(user);
    }

    @Test
    public void getContactsByUser() {
        doReturn(ResponseEntity.ok(userRSDTO)).when(userService).getContactsByUser(anyLong());
        ResponseEntity<?> user = userController.getContactsByUser(ID);
        assertNotNull(user);
    }
}
