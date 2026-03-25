package com.chatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;



/** we are using @notblank a so that we 
 * can handle empty inputs
 */

public class IncomingMessage {

    @NotBlank(message = "Sender name cannot be empty")
    @JsonProperty("from")
    private String from;

    @NotBlank(message = "Message content cannot be empty")
    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private String timestamp;

    //Default constructor
    public IncomingMessage() {}

    public IncomingMessage(String from, String message, String timestamp) {
        this.from = from;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    
    public String getMessage() {  return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() { 
        return "IncomingMessage{from=' " + from + "', message = ' " + message + "', timestamp = ' " + timestamp + " '}";
    }
}
