package com.web.curation.config.firebase;

import com.web.curation.model.user.User;

@FunctionalInterface
public interface NotificationMessageGenerator {

    String generateNotificationMessage(User sender, User receiver);

}