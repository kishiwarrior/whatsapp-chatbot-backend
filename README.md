# whatsapp-chatbot-backend

A Spring Boot REST API that simulates a WhatsApp chatbot backend. Built as an internship assignment.

---

## what it does

Accepts POST requests with a JSON message body, matches the message against a set of predefined replies, logs everything to console, and returns a response.

Endpoints:
- `POST /webhook` — send a message, get a reply
- `GET /webhook/logs` — see all messages received this session
- `GET /webhook/health` — check if the server is up

---

## stack

- Java 17
- Spring Boot 3.2
- Maven

---

## running locally

```bash
./mvnw spring-boot:run
```

Server starts at `http://localhost:8080`.

---

## example

request:
```json
{
  "from": "+91-9876543210",
  "message": "Hi"
}
```

response:
```json
{
  "to": "+91-9876543210",
  "reply": "Hello! 👋 How can I help you today?",
  "status": "sent",
  "processed_at": "2026-03-25 16:51:14"
}
```

---

## supported replies

| message | reply |
|---------|-------|
| hi, hello | Hello! 👋 |
| bye, goodbye | Goodbye! 👋 |
| thanks | You're welcome! |
| help | lists available commands |
| hours | support hours |
| order | order tracking prompt |
| anything else | fallback reply |

---

## project structure

```
src/main/java/com/chatbot/
├── WhatsAppBotApplication.java
├── controller/
│   ├── WebhookController.java
│   └── GlobalExceptionHandler.java
├── model/
│   ├── IncomingMessage.java
│   └── BotResponse.java
└── service/
    └── ChatbotService.java
```

---

## deployment

Deployed on Render (Docker service). Live at:
`https://whatsapp-chatbot-backend.onrender.com/webhook/health`

### Render Docker setup

1. Ensure `Dockerfile` is at repo root.
2. In Render dashboard, create `Web Service` with environment `Docker`.
3. Set `Dockerfile Path`: `Dockerfile`.
4. Render handles `PORT`; `render.yaml` has `envVars` PORT=8080 for local override.
5. Health check path: `/webhook/health`
