package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhatsappService {
    WhatsappRepository whatsappRepository = new WhatsappRepository();

    public boolean isNewUser(String mobile){
        return whatsappRepository.isNewUser(mobile);
    }

    public String createUser(String name, String mobile){
        whatsappRepository.createUser(name, mobile);
        return "Success";
    }

    public Group createGroup(List<User> users){
        return whatsappRepository.createGroup(users);
    }
    public int createMessage(String content){
        return whatsappRepository.createMessage(content);
    }
    public int sendMessage(Message message, User sender, Group group) throws Exception {
        return whatsappRepository.sendMessage(message, sender, group);
    }
    public String changeAdmin(User approver, User user, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.

        return whatsappRepository.changeAdmin(approver, user, group);
    }
}
