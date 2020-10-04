package com.apitran.tran.services;


import com.apitran.tran.dao.UserDao;
import com.apitran.tran.models.dto.ContactDTO;
import com.apitran.tran.models.dto.UserRQDTO;
import com.apitran.tran.models.entity.Contact;
import com.apitran.tran.models.entity.User;
import com.apitran.tran.transformers.Transformer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * User Service implementation
 */
@Service
public class UserServiceImpl implements IUserService {


    private final UserDao userDao;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final String ERROR = "The user %s doesn't exist";

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Method that creates an user
     *
     * @param user user
     * @return ResponseEntity
     */
    @Override
    @Transactional
    public ResponseEntity<?> createUser(UserRQDTO user) {

        List<String> errors = validateUser(user);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(String.join(", ", errors));
        }

        if (userDao.findByNameAndLastNameAndPhone(user.getName(), user.getLastName(), user.getPhone()) != null) {
            return ResponseEntity.badRequest().body("The user already exits");
        }

        User db = userDao.save(Transformer.toUser(user));


        return ResponseEntity.ok(Transformer.toUserRSDTO(db));
    }

    /**
     * Method that saves contacts
     *
     * @param userId   userId
     * @param contacts contacts
     * @return ResponseEntity
     */
    @Override
    @Transactional
    public ResponseEntity<?> saveContacts(Long userId, List<ContactDTO> contacts) {

        User user = userDao.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(String.format(ERROR, userId));
        }

        List<Contact> dbContacts = user.getContacts();
        if (dbContacts.isEmpty()) {
            user.setContacts(contacts.stream().map(Transformer::toContact).collect(Collectors.toList()));
        } else {
            List<Contact> contactsInsert = new ArrayList<>();
            Contact aux;
            boolean alreadyExist;
            for (ContactDTO in : contacts) {
                alreadyExist = false;
                for (Contact db : dbContacts) {
                    if (in.getPhone().equals(db.getPhone())) {
                        aux = new Contact();
                        aux.setId(db.getId());
                        aux.setContactName(in.getContactName());
                        aux.setPhone(in.getPhone());
                        contactsInsert.add(aux);
                        alreadyExist = true;
                        break;
                    }
                }
                if (!alreadyExist) {
                    aux = new Contact();
                    aux.setPhone(in.getPhone());
                    aux.setContactName(in.getContactName());
                    contactsInsert.add(aux);
                }
            }
            user.setContacts(contactsInsert);
        }

        userDao.save(user);

        return ResponseEntity.ok().build();
    }

    /**
     * Method that obtain common contacts between two users
     *
     * @param userIdA userIdA
     * @param userIdB userIdB
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<?> obtainCommonContacts(Long userIdA, Long userIdB) {

        User u1 = userDao.findById(userIdA).orElse(null);
        if (u1 == null) {
            return ResponseEntity.badRequest().body(String.format(ERROR, userIdA));
        }

        User u2 = userDao.findById(userIdB).orElse(null);
        if (u2 == null) {
            return ResponseEntity.badRequest().body(String.format(ERROR, userIdB));
        }

        List<ContactDTO> contacts = new ArrayList<>();

        for (Contact u1C : u1.getContacts()) {
            for (Contact u2C : u2.getContacts()) {
                if (u1C.getPhone().equals(u2C.getPhone()) &&
                        u1C.getContactName().equals(u2C.getContactName())) {
                    ContactDTO contact = new ContactDTO();
                    contact.setContactName(u1C.getContactName());
                    contact.setPhone(u1C.getPhone());
                    contacts.add(contact);
                    break;
                }
            }
        }

        return ResponseEntity.ok(contacts);
    }

    /**
     * Method that obtains the contacts for a user
     *
     * @param userId userId
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<?> getContactsByUser(Long userId) {

        User user = userDao.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(String.format(ERROR, userId));
        }
        return ResponseEntity.ok(user.getContacts().parallelStream().map(Transformer::toContactDTO).collect(Collectors.toList()));
    }

    /**
     * validateUser
     *
     * @param userRQDTO userRQDTO
     * @return List String
     */
    private List<String> validateUser(UserRQDTO userRQDTO) {

        Set<ConstraintViolation<UserRQDTO>> violations = factory.getValidator().validate(userRQDTO);
        List<String> errors = new ArrayList<>();
        if (!violations.isEmpty()) {
            errors = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        }

        String regex =
                "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(userRQDTO.getPhone()).matches()) {
            errors.add("The phone number is not valid");
        }

        return errors;

    }
}
