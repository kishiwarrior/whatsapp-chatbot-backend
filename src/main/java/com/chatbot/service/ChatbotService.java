package com.chatbot.service;

import com.chatbot.model.BotResponse;
import com.chatbot.model.IncomingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Core chatbot service that:
 *  - Logs all incoming messages
 *  - Generates predefined replies based on keywords
 *  - Maintains an in-memory message log
 */
@Service
public class ChatbotService {

    private static final Logger logger = LoggerFactory.getLogger(ChatbotService.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // In-memory log of all processed messages
    private final List<Map<String, String>> messageLog = new ArrayList<>();

    /**
     * Predefined reply map — extend this to add more bot responses.
     */
    private static final Map<String, String> REPLY_MAP = Map.ofEntries(
        Map.entry("hi",       "Hello! 👋 How can I help you today?"),
        Map.entry("hello",    "Hello! 👋 How can I help you today?"),
        Map.entry("hey",      "Hey there! 😊 What can I do for you?"),
        Map.entry("bye",      "Goodbye! 👋 Have a wonderful day!"),
        Map.entry("goodbye",  "Goodbye! 👋 Take care!"),
        Map.entry("thanks",   "You're welcome! 😊"),
        Map.entry("thank you","You're welcome! Happy to help! 😊"),
        Map.entry("help",     "Here are things I can do:\n• Say 'Hi' to greet\n• Say 'Bye' to farewell\n• Say 'Help' for assistance"),
        Map.entry("hours",    "Our support hours are Mon–Fri, 9 AM – 6 PM IST."),
        Map.entry("price",    "Please visit our website for the latest pricing information."),
        Map.entry("order",    "To track your order, please share your order ID.")
    );

    /**
     * Process an incoming message and return a bot response.
     */
    public BotResponse processMessage(IncomingMessage incoming) {
        String now = LocalDateTime.now().format(FORMATTER);

        // 1. LOG the message
        logger.info("📨 Incoming Message | From: {} | Message: '{}' | Time: {}",
                incoming.getFrom(), incoming.getMessage(), now);

        // 2. Store in memory log
        messageLog.add(Map.of(
            "from",      incoming.getFrom(),
            "message",   incoming.getMessage(),
            "received_at", now
        ));

        // 3. Generate reply
        String replyText = generateReply(incoming.getMessage());

        logger.info("📤 Outgoing Reply   | To: {} | Reply: '{}'", incoming.getFrom(), replyText);

        return new BotResponse(incoming.getFrom(), replyText, "sent", now);
    }

    /**
     * Match user message to a predefined reply.
     * Matching is case-insensitive and trims whitespace.
     */
    private String generateReply(String userMessage) {
        if (userMessage == null || userMessage.isBlank()) {
            return "I didn't receive a message. Please try again.";
        }

        String normalized = userMessage.trim().toLowerCase();

        // Exact match first
        if (REPLY_MAP.containsKey(normalized)) {
            return REPLY_MAP.get(normalized);
        }

        // Partial/contains match
        for (Map.Entry<String, String> entry : REPLY_MAP.entrySet()) {
            if (normalized.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        // Default fallback
        return "🤖 I'm not sure how to respond to that yet. Type 'help' to see what I can do!";
    }

    /**
     * Returns read-only view of all logged messages.
     */
    public List<Map<String, String>> getMessageLog() {
        return Collections.unmodifiableList(messageLog);
    }
}
