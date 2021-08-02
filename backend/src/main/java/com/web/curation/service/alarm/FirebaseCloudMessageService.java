package com.web.curation.service.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.web.curation.model.alarm.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Component
public class FirebaseCloudMessageService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/\n" +
            "webproject-1559735149402/messages:send";
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(FirebaseCloudMessageService.class);

    public void send(final NotificationRequest notificationRequest) throws InterruptedException, ExecutionException {
        Message message = Message.builder()
                .setToken(notificationRequest.getToken())
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(notificationRequest.getTitle(),
                                notificationRequest.getMessage()))
                        .build())
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        logger.info("Sent message: " + response);
    }


//    public void sendMessageTo(String targetToken, String title, String body) throws IOException{
//        String message = makeMessage(targetToken, title, body);
//
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .post(requestBody)
//                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
//                .build();
//        Response response = client.newCall(request)
//                .execute();
//        System.out.println(response.body().string());
//
//    }
//
//    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
//        FcmMessage fcmMessage = FcmMessage.builder()
//                .message(FcmMessage.Message.builder()
//                    .token(targetToken)
//                    .notification(FcmMessage.Notification.builder()
//                        .title(title)
//                        .body(body)
//                        .image(null)
//                        .build()
//                    )
//                .build()
//                )
//                .validate_only(false)
//                .build();
//        return objectMapper.writeValueAsString(fcmMessage);
//    }
//
//    private String getAccessToken() throws IOException{
//        String firebaseConfigPath = "firebase service key.json";
//
//        GoogleCredentials googleCredential = GoogleCredentials.fromStream(
//                new ClassPathResource(firebaseConfigPath).getInputStream()
//        ).createScoped(Collections.singleton("https://www.googleapis.com/auth/cloud-platform"));
//
//        googleCredential.refreshIfExpired();
//
//        return googleCredential.getAccessToken().getTokenValue();
//
//    }
}
