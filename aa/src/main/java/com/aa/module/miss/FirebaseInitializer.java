package com.aa.module.miss;

import java.io.InputStream;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseInitializer {
	@PostConstruct
	public void initialize() {
	    try {
	        InputStream serviceAccount =
	            new ClassPathResource("firebase/firebase-adminsdk.json").getInputStream();

	        FirebaseOptions options = FirebaseOptions.builder()
	            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	            .build();

	        if (FirebaseApp.getApps().isEmpty()) {
	            FirebaseApp.initializeApp(options);
	            System.out.println("Firebase 초기화 완료");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}