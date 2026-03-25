package com.chatbot.controller;

import com.chatbot.model.BotResponse;
import com.chatbot.model.IncomingMessage;
import com.chatbot.service.ChatbotService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller exposing the WhatsApp Webhook endpoint.
 *
 * Endpoints:
 *   POST /webhook          → Receive and respond to messages
 *   GET  /webhook/logs     → View all logged messages
 *   GET  /webhook/health   → Health check
 */
@RestController
@RequestMapping("/webhook")
@CrossOrigin(origins = "*")  // Allow all origins for demo purposes
public class WebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WebhookController.class);
    private final ChatbotService chatbotService;

    public WebhookController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    /**
     * Main webhook endpoint — simulates receiving a WhatsApp message.
     *
     * Example request body:
     * {
     *   "from": "+91-9876543210",
     *   "message": "Hi",
     *   "timestamp": "2024-01-15T10:30:00"
     * }
     */
    @PostMapping
    public ResponseEntity<BotResponse> receiveMessage(@Valid @RequestBody IncomingMessage incoming) {
        logger.info("➡️  POST /webhook called from: {}", incoming.getFrom());
        BotResponse response = chatbotService.processMessage(incoming);
        return ResponseEntity.ok(response);
    }

    /**
     * Returns a log of all messages received since server start.
     */
    @GetMapping("/logs")
    public ResponseEntity<Map<String, Object>> getMessageLogs() {
        List<Map<String, String>> logs = chatbotService.getMessageLog();
        return ResponseEntity.ok(Map.of(
            "total_messages", logs.size(),
            "messages", logs
        ));
    }

    /**
     * Simple health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status",  "UP",
            "service", "WhatsApp Chatbot Backend",
            "version", "1.0.0"
        ));
    }
}
