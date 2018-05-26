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

    @Autowired
    public NotifyServiceImpl(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }
    @Override
    public void notify(String username, Message message) {
        messagingTemplate.convertAndSendToUser(username, "/queue/reply", message);
    }
}
