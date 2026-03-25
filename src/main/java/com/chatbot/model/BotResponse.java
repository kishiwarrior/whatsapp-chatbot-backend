package com.chatbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the chatbot's reply to an incoming message.
 */
public class BotResponse {

    @JsonProperty("to")
    private String to;

    @JsonProperty("reply")
    private String reply;

    @JsonProperty("status")
    private String status;

    @JsonProperty("processed_at")
    private String processedAt;

    // Default constructor
    public BotResponse() {}

    public BotResponse(String to, String reply, String status, String processedAt) {
        this.to = to;
        this.reply = reply;
        this.status = status;
        this.processedAt = processedAt;
    }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getProcessedAt() { return processedAt; }
    public void setProcessedAt(String processedAt) { this.processedAt = processedAt; }
}
