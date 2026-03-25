package com.chatbot;

import com.chatbot.model.IncomingMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testHiReply() throws Exception {
        IncomingMessage msg = new IncomingMessage("+91-9876543210", "Hi", null);

        mockMvc.perform(post("/webhook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(msg)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.reply").value("Hello! 👋 How can I help you today?"))
            .andExpect(jsonPath("$.status").value("sent"));
    }

    @Test
    void testByeReply() throws Exception {
        IncomingMessage msg = new IncomingMessage("+91-9876543210", "Bye", null);

        mockMvc.perform(post("/webhook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(msg)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.reply").value("Goodbye! 👋 Have a wonderful day!"));
    }

    @Test
    void testDefaultFallback() throws Exception {
        IncomingMessage msg = new IncomingMessage("+91-9876543210", "random text xyz", null);

        mockMvc.perform(post("/webhook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(msg)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.reply").exists());
    }

    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/webhook/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void testLogsEndpoint() throws Exception {
        mockMvc.perform(get("/webhook/logs"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.total_messages").exists());
    }

    @Test
    void testValidationError() throws Exception {
        // Missing "from" field → should return 400
        String badJson = "{\"message\": \"Hi\"}";

        mockMvc.perform(post("/webhook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(badJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value("error"));
    }
}
