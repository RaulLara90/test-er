package com.apitran.tran.service;

import com.apitran.tran.dao.UserDao;
import com.apitran.tran.models.dto.ContactDTO;
import com.apitran.tran.models.dto.UserRQDTO;
import com.apitran.tran.models.dto.UserRSDTO;
import com.apitran.tran.models.entity.Contact;
import com.apitran.tran.models.entity.User;
import com.apitran.tran.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserDao userDao;

    @Mock
    User user;

    @Mock
    Contact contact;

    @Mock
    ContactDTO contactDTO;

    UserRQDTO userRQDTO;

    private static final Long ID = 1L;
    private static final String ANY_STRING = "ANY_STRING";

    @Before
    public void init() {
        userRQDTO = new UserRQDTO();
        reload();
    }

    @Test
    public void createUser() {

        when(userDao.findByNameAndLastNameAndPhone(anyString(), anyString(), anyString())).thenReturn(null);
        when(userDao.save(any())).thenReturn(user);
        ResponseEntity<?> userRSDTO = userService.createUser(userRQDTO);
        verify(userDao).findByNameAndLastNameAndPhone(anyString(), anyString(), anyString());
        verify(userDao).save(any());
        verifyNoMoreInteractions(userDao);
        assertNotNull(userRSDTO);
        assertTrue(userRSDTO.getBody() instanceof UserRSDTO);
    }

    @Test
    public void createUserNotValid() {

        userRQDTO.setName(null);
        userRQDTO.setPhone("asd");
        ResponseEntity<?> userRSDTO = userService.createUser(userRQDTO);
        verifyZeroInteractions(userDao);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.BAD_REQUEST);
        reload();
    }

    @Test
    public void createUserNotValid2() {

        when(userDao.findByNameAndLastNameAndPhone(anyString(), anyString(), anyString())).thenReturn(user);
        ResponseEntity<?> userRSDTO = userService.createUser(userRQDTO);
        verify(userDao).findByNameAndLastNameAndPhone(anyString(), anyString(), anyString());
        verifyNoMoreInteractions(userDao);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.BAD_REQUEST);
        reload();
    }


    @Test
    public void saveContacts() {

        when(userDao.findById(anyLong())).thenReturn(Optional.of(user));
        when(user.getContacts()).thenReturn(Collections.emptyList());
        when(userDao.save(any())).thenReturn(user);
        ResponseEntity<?> userRSDTO = userService.saveContacts(ID, Collections.singletonList(contactDTO));
        verify(userDao).findById(anyLong());
        verify(userDao).save(any());
        verifyNoMoreInteractions(userDao);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void saveContactsKO() {

        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<?> userRSDTO = userService.saveContacts(ID, Collections.singletonList(contactDTO));
        verify(userDao).findById(anyLong());
        verifyNoMoreInteractions(userDao);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void saveContacts2() {

        when(contactDTO.getPhone()).thenReturn(ANY_STRING);
        when(contactDTO.getContactName()).thenReturn(ANY_STRING);
        when(contact.getPhone()).thenReturn(ANY_STRING);
        when(contact.getContactName()).thenReturn(ANY_STRING);
        when(userDao.findById(anyLong())).thenReturn(Optional.of(user));
        when(user.getContacts()).thenReturn(Collections.singletonList(contact));
        when(userDao.save(any())).thenReturn(user);
        ResponseEntity<?> userRSDTO = userService.saveContacts(ID, Collections.singletonList(contactDTO));
        verify(userDao).findById(anyLong());
        verify(userDao).save(any());
        verifyNoMoreInteractions(userDao);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void saveContacts3() {

        when(contactDTO.getPhone()).thenReturn(ANY_STRING + ANY_STRING);
        when(contactDTO.getContactName()).thenReturn(ANY_STRING);
        when(contact.getPhone()).thenReturn(ANY_STRING);
        when(contact.getContactName()).thenReturn(ANY_STRING);
        when(userDao.findById(anyLong())).thenReturn(Optional.of(user));
        when(user.getContacts()).thenReturn(Collections.singletonList(contact));
        when(userDao.save(any())).thenReturn(user);
        ResponseEntity<?> userRSDTO = userService.saveContacts(ID, Collections.singletonList(contactDTO));
        verify(userDao).findById(anyLong());
        verify(userDao).save(any());
        verifyNoMoreInteractions(userDao);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void obtainCommonContacts() {

        when(userDao.findById(anyLong())).thenReturn(Optional.of(user)).thenReturn(Optional.of(user));
        when(contact.getPhone()).thenReturn(ANY_STRING);
        when(contact.getContactName()).thenReturn(ANY_STRING);
        when(user.getContacts()).thenReturn(Collections.singletonList(contact));
        ResponseEntity<?> userRSDTO = userService.obtainCommonContacts(ID, ID);
        verify(userDao, times(2)).findById(anyLong());
        verifyNoMoreInteractions(userDao);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void obtainCommonContacts2() {

        when(userDao.findById(anyLong())).thenReturn(Optional.of(user)).thenReturn(Optional.of(user));
        when(contact.getPhone()).thenReturn(ANY_STRING);
        when(contact.getContactName()).thenReturn(ANY_STRING + ANY_STRING);
        when(user.getContacts()).thenReturn(Collections.singletonList(contact));
        ResponseEntity<?> userRSDTO = userService.obtainCommonContacts(ID, ID);
        verify(userDao, times(2)).findById(anyLong());
        verifyNoMoreInteractions(userDao);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void obtainCommonContactsKO() {

        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<?> userRSDTO = userService.obtainCommonContacts(ID, ID);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void obtainCommonContactsKO2() {

        when(userDao.findById(anyLong())).thenReturn(Optional.of(user)).thenReturn(Optional.empty());
        ResponseEntity<?> userRSDTO = userService.obtainCommonContacts(ID, ID);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getContactsByUser() {

        when(userDao.findById(anyLong())).thenReturn(Optional.of(user));
        when(user.getContacts()).thenReturn(Collections.singletonList(contact));
        ResponseEntity<?> userRSDTO = userService.getContactsByUser(ID);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getContactsByUserKO() {

        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<?> userRSDTO = userService.getContactsByUser(ID);
        assertNotNull(userRSDTO);
        assertEquals(userRSDTO.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    private void reload() {

        userRQDTO.setLastName("aaa");
        userRQDTO.setName("ras");
        userRQDTO.setPhone("+34678908765");
    }
}
