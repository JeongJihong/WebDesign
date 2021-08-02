package com.web.curation.config.firebase;

import com.web.curation.model.user.User;

public enum NotificationType {

    POST_RECEIVED(((sender, receiver) ->
            sender.getNickname() + " 님이 " + receiver.getNickname() + " 님에게 글을 작성했습니다")),
    POST_TAGGED(((sender, receiver) ->
            receiver.getNickname() + " 님이 " + sender.getNickname() + " 님의 글에 태그되었습니다"));

    private NotificationMessageGenerator notificationMessageGenerator;

    NotificationType(NotificationMessageGenerator notificationMessageGenerator) {
        this.notificationMessageGenerator = notificationMessageGenerator;
    }

    public String generateNotificationMessage(User sender, User receiver) {
        return notificationMessageGenerator.generateNotificationMessage(sender, receiver);
    }

}