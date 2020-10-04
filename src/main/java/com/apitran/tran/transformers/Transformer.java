package com.apitran.tran.transformers;

import com.apitran.tran.models.dto.ContactDTO;
import com.apitran.tran.models.dto.UserRQDTO;
import com.apitran.tran.models.dto.UserRSDTO;
import com.apitran.tran.models.entity.Contact;
import com.apitran.tran.models.entity.User;

/**
 * Transformer Class
 */
public final class Transformer {

    /**
     * toUser
     *
     * @param userRQDTO userRQDTO
     * @return User
     */
    public static User toUser(UserRQDTO userRQDTO) {

        User user = new User();
        user.setLastName(userRQDTO.getLastName());
        user.setName(userRQDTO.getName());
        user.setPhone(userRQDTO.getPhone());

        return user;
    }

    /**
     * toUserRSDTO
     *
     * @param user user
     * @return UserRSDTO
     */
    public static UserRSDTO toUserRSDTO(User user) {

        UserRSDTO userRSDTO = new UserRSDTO();
        userRSDTO.setLastName(user.getLastName());
        userRSDTO.setName(user.getName());
        userRSDTO.setPhone(user.getPhone());
        userRSDTO.setId(user.getId());

        return userRSDTO;
    }

    /**
     * toContact
     *
     * @param contactDTO contactDTO
     * @return Contact
     */
    public static Contact toContact(ContactDTO contactDTO) {

        Contact contact = new Contact();
        contact.setContactName(contactDTO.getContactName());
        contact.setPhone(contactDTO.getPhone());

        return contact;
    }

    /**
     * toContactDTO
     *
     * @param contact contact
     * @return ContactDTO
     */
    public static ContactDTO toContactDTO(Contact contact) {

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setContactName(contact.getContactName());
        contactDTO.setPhone(contact.getPhone());

        return contactDTO;
    }
}
