package com.mazindere.university_reviews_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniversityReviewsAppApplication {
	//todo check on the user type -admin and user -to be checked later not now
	//todo -shld navbar be also in sign up and log in pages?
	//todo-make the hero section in university to delay-js not working

	//todo-global.css not working
	//todo-forgot -password-later cz we;ll use email-when a review posted an email sent
	//todo-remove the uni cards at index and use a loop with info being fed from university controller

	public static void main(String[] args) {
		SpringApplication.run(UniversityReviewsAppApplication.class, args);
	}

}
