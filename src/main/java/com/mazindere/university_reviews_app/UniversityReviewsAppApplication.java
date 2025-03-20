package com.mazindere.university_reviews_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniversityReviewsAppApplication {
	//todo check on the user type -admin and user -to be checked later not now
	//todo -shld navbar be also in sign up and log in pages?
	//todo-make the hero section in university to delay-js not working
	//todo-on first load up the index appears-upon writing a review user taken to login/sign up
	//todo-check the bug on pressing logout -review prompt appears
	//todo-a place to hide/unhide password
	//todo-forgot -password-later cz we;ll use email-when a review posted an email sent

	public static void main(String[] args) {
		SpringApplication.run(UniversityReviewsAppApplication.class, args);
	}

}
