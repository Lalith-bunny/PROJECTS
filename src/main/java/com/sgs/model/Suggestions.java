package com.sgs.model;

import java.sql.Timestamp;

public class Suggestions{
	 private int id;
	 private int userId;
	 private String title;
	 private String message;
	 private String adminReply;
	 private Timestamp submittedAt; 
	 public Suggestions() {}
     public Suggestions(int userId,String title,String message) {
    	 this.userId=userId;
    	 this.title=title;
    	 this.message=message;
     }
     public int getId() { return id; }
     public void setId(int id) { this.id = id; }
  
     public int getUserId() { return userId; }
     public void setUserId(int userId) { this.userId = userId; }
  
     public String getTitle() { return title; }
     public void setTitle(String title) { this.title = title; }
  
     public String getMessage() { return message; }
     public void setMessage(String message) { this.message = message; }
     public String getAdminReply() { return adminReply; }
     public void setAdminReply(String adminReply) { this.adminReply = adminReply; }
     public Timestamp getSubmittedAt() {return submittedAt;}
     public void setSubmittedAt(Timestamp submittedAt) {this.submittedAt=submittedAt;}
     
}
 //id, user_id, title, message, admin_reply, submitted_at