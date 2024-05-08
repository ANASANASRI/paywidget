package ma.m2t.paywidget.controller;

import lombok.Data;
import ma.m2t.paywidget.model.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    private SimpMessagingTemplate template;
    // Initialize Notifications
    private Notifications notifications = new Notifications(0);
    @GetMapping("/notify")
    public String getNotification() {
        // Increment Notification by one
        notifications.increment();
        // Push notifications to front-end
        template.convertAndSend("/topic/notification", notifications);
        return "Notifications successfully sent to Angular !";
    }
}

