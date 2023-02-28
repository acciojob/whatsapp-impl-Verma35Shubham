package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashMap<String, User> userData;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userData = new HashMap<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public boolean isNewUser(String mobile){
        return userData.containsKey(mobile);
    }

    public void createUser(String name, String mobile){
        userData.put(mobile, new User(name, mobile));
    }

    public Group createGroup(List<User> users){
        if(users.size() == 2) return this.createPersonalChat(users);
        customGroupCount++;

        String groupname = "Group"+ " " + customGroupCount;
        Group group = new Group(groupname, users.size());
        adminMap.put(group, users.get(0));
        groupUserMap.put(group, users);
        return  group;
    }

    public  Group createPersonalChat(List<User> users) {
        String groupname = users.get(1).getName();
        Group personalGroup = new Group(groupname, 2);
        groupUserMap.put(personalGroup, users);
        return personalGroup;
    }

    public int createMessage(String content){
        messageId++;
        Message message = new Message(messageId, content);
        return this.messageId;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        if(!groupUserMap.containsKey(group)) throw new Exception("Group does not exist");
        if(!userExistsInGroup(group, sender)) throw new Exception("You are not allowed to send message");

        List<Message> messages = new ArrayList<>();
        if(groupMessageMap.containsKey(group))
            messages = groupMessageMap.get(group);
        messages.add(message);
        groupMessageMap.put(group, messages);
        return messages.size();
    }
    public boolean userExistsInGroup(Group group, User sender){
        List<User> users = groupUserMap.get(group);
        for(User user : users){
            if(user.equals(sender)){
                return true;
            }
        }
        return false;
    }
    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if (!groupUserMap.containsKey(group)) throw new Exception("Group does not exist");
        if(!adminMap.get(group).equals(approver)) throw new Exception("Approver does not have rights");
        if(!userExistsInGroup(group, user)) throw new Exception("User is not a participant");
        adminMap.put(group, user);
        return "Success";
    }
}
