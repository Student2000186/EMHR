package riskScore;

import java.time.LocalDateTime;

public class RiskScore {

    private String level;
    private int score;
    private LocalDateTime timestamp;

    public RiskScore(String level, int score) {
        this.level = level;
        this.score = score;
        this.timestamp = LocalDateTime.now();
    }
    // Getters
    public String getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setters with validation
    public void setLevel(String level) {

        if(level.equals("LOW") || level.equals("MEDIUM") || level.equals("HIGH")) {
            this.level = level;
        } else {   
        	  System.out.println("Invalid risk level.");
        }
    }

    public void setScore(int score) {

        if(score >= 0) {
            this.score = score;
        }
    }

    // Display method
    public void displayRiskScore() {

        System.out.println("Risk Level: " + level);
        System.out.println("Score: " + score);
        System.out.println("Timestamp: " + timestamp);
    }
    
  }