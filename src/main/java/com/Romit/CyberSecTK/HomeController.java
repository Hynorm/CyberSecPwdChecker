package com.Romit.CyberSecTK;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "index.html"; // static/index.html
    }

    @RequestMapping("/Password_Checker")
    public String pwd() {
        return "pwd.html";
    }

    // GET version (optional)
    @ResponseBody
    @GetMapping("/api/check-password")
    public Map<String, Object> checkPassword(@RequestParam String password) throws IOException {
        return buildResponse(password);
    }

    // ✅ POST version with feedback
    @ResponseBody
    @PostMapping("/api/check-password")
    public Map<String, Object> checkPasswordPost(@RequestBody Map<String, String> request) throws IOException {
        String password = request.get("password");
        return buildResponse(password);
    }

    // ✅ Extracted method to use in both GET and POST
    private Map<String, Object> buildResponse(String password) throws IOException {
        Map<String, Object> result = CyberSecTkApplication.ScoreCheck(password); // returns score + feedback

        int score = (int) result.get("score");

        String label;
        if (score <= 3) label = "Weak";
        else if (score <= 6) label = "Moderate";
        else label = "Strong";

        result.put("strengthLabel", label);

        return result; // contains: score, feedback[], strengthLabel
    }
}
