package service.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import service.regiment.UserService;

import static application.Constants.MAIL;
import static application.Constants.USERNAME;

@Service
public class NotifyServiceImpl implements NotifyService {

    private SimpMessagingTemplate messagingTemplate;
    private UserService userService;

    @Autowired
    public NotifyServiceImpl(SimpMessagingTemplate messagingTemplate,UserService userService){
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
    }
    @Override
    public void notifyAdmin(Message message) {
        messagingTemplate.convertAndSendToUser(userService.findAdmin().getUsername(), "/queue/reply", message);
    }

    @Override
    public void notifyRegimentCommander(int regimentCode, Message message) {
        String username = USERNAME + regimentCode + MAIL;
        messagingTemplate.convertAndSendToUser( username,"/queue/reply", message);
    }
}
