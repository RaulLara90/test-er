package com.apitran.tran.services;

import com.apitran.tran.models.dto.ContactDTO;
import com.apitran.tran.models.dto.UserRQDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * User Service interface
 */
public interface IUserService {

    /**
     * Method that creates an user
     * @param user user
     * @return ResponseEntity
     */
    ResponseEntity<?> createUser(UserRQDTO user);

    /**
     *  Method that saves contacts
     * @param userId userId
     * @param contacts contacts
     * @return ResponseEntity
     */
    ResponseEntity<?> saveContacts(Long userId, List<ContactDTO> contacts);

    /**
     * Method that obtain common contacts between two users
     * @param userIdA userIdA
     * @param userIdB userIdB
     * @return ResponseEntity
     */
    ResponseEntity<?> obtainCommonContacts(Long userIdA, Long userIdB);

    /**
     * Method that obtains the contacts for a user
     * @param userId userId
     * @return ResponseEntity
     */
    ResponseEntity<?> getContactsByUser(Long userId);
}
