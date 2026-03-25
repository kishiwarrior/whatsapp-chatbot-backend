# 📱 WhatsApp Chatbot Backend — Spring Boot

A production-ready REST API backend that simulates a WhatsApp chatbot, built with Java 17 and Spring Boot 3.

---

## 🚀 Features

- **POST `/webhook`** — Receive simulated WhatsApp messages and get instant bot replies
- **GET `/webhook/logs`** — View all messages processed since server start
- **GET `/webhook/health`** — Health check for deployment platforms
- Predefined keyword-based replies (Hi → Hello, Bye → Goodbye, and more)
- Full request validation with clean error responses
- Structured console logging for every message
- CORS-enabled for frontend integration
- Ready to deploy on **Render** (free tier)

---

## 📋 Prerequisites

- Java 17+
- Maven 3.8+

---

## ⚡ Run Locally

```bash
# Clone the repo
git clone https://github.com/YOUR_USERNAME/whatsapp-chatbot-backend.git
cd whatsapp-chatbot-backend

# Build and run
mvn spring-boot:run
```

Server starts at: `http://localhost:8080`

---

## 🧪 API Usage

### 1. Send a Message — `POST /webhook`

**Request:**
```bash
curl -X POST http://localhost:8080/webhook \
  -H "Content-Type: application/json" \
  -d '{"from": "+91-9876543210", "message": "Hi", "timestamp": "2024-01-15T10:30:00"}'
```

**Response:**
```json
{
  "to": "+91-9876543210",
  "reply": "Hello! 👋 How can I help you today?",
  "status": "sent",
  "processed_at": "2024-01-15 10:30:00"
}
```

---

### 2. Send "Bye"

```bash
curl -X POST http://localhost:8080/webhook \
  -H "Content-Type: application/json" \
  -d '{"from": "+91-9876543210", "message": "Bye"}'
```

**Response:**
```json
{
  "reply": "Goodbye! 👋 Have a wonderful day!"
}
```

---

### 3. View Message Logs — `GET /webhook/logs`

```bash
curl http://localhost:8080/webhook/logs
```

---

### 4. Health Check — `GET /webhook/health`

```bash
curl http://localhost:8080/webhook/health
```

---

## 🤖 Supported Bot Replies

| User Sends     | Bot Replies                               |
|----------------|-------------------------------------------|
| hi / hello     | Hello! 👋 How can I help you today?      |
| hey            | Hey there! 😊 What can I do for you?     |
| bye / goodbye  | Goodbye! 👋 Have a wonderful day!        |
| thanks         | You're welcome! 😊                        |
| help           | Lists available commands                  |
| hours          | Support hours information                 |
| price          | Pricing info redirect                     |
| order          | Order tracking prompt                     |
| *(anything else)* | Default fallback reply                |

---

## ☁️ Deploy on Render (Free)

1. Push this repo to GitHub
2. Go to [render.com](https://render.com) → **New Web Service**
3. Connect your GitHub repo
4. Set:
   - **Environment**: Java
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/whatsapp-bot-1.0.0.jar`
5. Click **Deploy** — your API will be live in ~2 minutes!

The `render.yaml` in this repo auto-configures the deployment.

---

## 📁 Project Structure

```
src/
└── main/
    ├── java/com/chatbot/
    │   ├── WhatsAppBotApplication.java       # Entry point
    │   ├── controller/
    │   │   ├── WebhookController.java        # REST endpoints
    │   │   └── GlobalExceptionHandler.java   # Error handling
    │   ├── model/
    │   │   ├── IncomingMessage.java          # Request model
    │   │   └── BotResponse.java              # Response model
    │   └── service/
    │       └── ChatbotService.java           # Bot logic & logging
    └── resources/
        └── application.properties
```

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.2**
- **Spring Web MVC**
- **Bean Validation (Jakarta)**
- **Maven**

---

## 📄 License

MIT License — free to use and modify.
