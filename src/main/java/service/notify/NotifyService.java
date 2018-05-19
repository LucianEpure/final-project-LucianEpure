package service.notify;

public interface NotifyService {

    void notifyAdmin(Message message);
    void notifyRegimentCommander(int regimentCode, Message message);
}
