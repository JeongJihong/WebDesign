package com.web.curation.service.alarm;

import com.web.curation.model.alarm.Alarm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final FirebaseCloudMessageService fcmService;

    private final Map<Long, String> tokenMap = new HashMap<>();

    public NotificationService(final FirebaseCloudMessageService fcmService) {
        this.fcmService = fcmService;
    }

    public void register(final Long userId, final String token) {
        tokenMap.put(userId, token);
    }

    public void deleteToken(final Long userId) {
        tokenMap.remove(userId);
    }

    public String getToken(final Long userId) {
        return tokenMap.get(userId);
    }

    public void sendNotification(final Alarm request, String receiverToken) {
        try {
            fcmService.send(request, receiverToken);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }

//    private void createNotification(Post savedPost) {
//        if (savedPost.getReceiver().isPresent()) {
//            createReceiveNotification(savedPost.getAuthor(), savedPost.getReceiver().get());
//        }
//        if (!savedPost.getTaggedUsers().isEmpty()) {
//            createTaggedNotification(savedPost.getAuthor(), savedPost.getTaggedUsers());
//        }
//    }

//    private void createReceiveNotification(User sender, User receiver) {
////        if (receiver.isLogin()) {
//            NotificationRequest notificationRequest = NotificationRequest.builder()
//                    .title("POST RECEIVED")
//                    .token(getToken(receiver.getUid()))
//                    .message(NotificationType.POST_RECEIVED.generateNotificationMessage(sender, receiver))
//                    .build();
//            sendNotification(notificationRequest);
////        }
//    }

//    private void createTaggedNotification(User sender, List<User> receivers) {
//        receivers.stream()
////                .filter(User::isLogin)
//                .forEach(receiver -> {
//                    NotificationRequest notificationRequest = NotificationRequest.builder()
//                            .title("POST TAGGED")
//                            .token(getToken(receiver.getUid()))
//                            .message(NotificationType.POST_TAGGED.generateNotificationMessage(sender, receiver))
//                            .build();
//                    sendNotification(notificationRequest);
//                });
//    }




}
