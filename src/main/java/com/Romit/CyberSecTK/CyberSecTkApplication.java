package com.Romit.CyberSecTK;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SpringBootApplication
public class CyberSecTkApplication {
	//private static int score;
    static private String pwd;
	public static void main(String[] args) throws IOException {
		SpringApplication.run(CyberSecTkApplication.class, args);
		Scanner obj = new Scanner(System.in);
		System.out.println("Enter the String");
		pwd = obj.nextLine();
		ScoreCheck(pwd);
	}

	public static Map<String, Object> ScoreCheck(String pwd) throws IOException {
		// This method will check the score the password entered
		int score = 0;
		List<String> feedback = new ArrayList<>();

		// The minimum length that we will keep is 8
		Set<String> dictionary = Files.lines(Paths.get("dictionary.txt")).map(String::trim).collect(Collectors.toSet());

		String dictPattern = dictionary.stream()
				.map(Pattern::quote)
				.collect(Collectors.joining("|", "(", ")"));

		Pattern dictRegex = Pattern.compile(dictPattern, Pattern.CASE_INSENSITIVE);

		if(pwd.length()>=8){
			score++;
			if(pwd.length()>=15)score+=2;
			if(pwd.matches(".*[a-z].*"))score++;
			else feedback.add("Doesn't contain lowercase letters.");
			if(pwd.matches(".*[A-Z].*"))score++;
			else feedback.add("Doesn't contain uppercase letters.");
			if(pwd.matches(".*[0-9].*"))score++;
			else feedback.add("Doesn't contain digits (0-9)..");
			if(pwd.matches(".*[!@#$%^&*()_+=<>?-].*")){score++;
				}
			else feedback.add("Doesn't contain special characters.");
			if (!dictRegex.matcher(pwd.toLowerCase()).find()) {
				score += 2;
			} else {
				score--;
				feedback.add("Password contains common daily used words.");
			}
		}
		else{
			feedback.add("Password is too short. It must be at least 8 characters.");
		}
		Map<String, Object> result = new HashMap<>();
		result.put("score", score);
		result.put("feedback", feedback);

		return result;

	}

}
