package com.aa.module.notification;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.FirebaseMessagingException;



@Service
public class NotificationService {
	
	public void sendNotification(String token, String title, String body) {
		Message message = Message.builder()
			    .setToken(token)
			    .setNotification(
			        Notification.builder()
			            .setTitle(title)
			            .setBody(body)
			            .build()
			    )
			    .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("푸시 성공: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}
