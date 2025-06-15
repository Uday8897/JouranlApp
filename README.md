# Journal App: AI-Powered Sentiment Analysis 📓🤖

## 🚀 Project Overview
Journal App is a full-stack web application that enables users to add, update, and delete journal entries securely. The application integrates AI-powered sentiment analysis using Gemini AI to analyze user journal entries weekly. Results are cached using Redis, optimizing performance and minimizing redundant computations. Additionally, the system sends out weekly email reports to users with their sentiment analysis.

## 🛠️ Tech Stack
- **Frontend:** React, Tailwind CSS
- **Backend:** Spring Boot, Spring Security
- **Database:** MongoDB
- **Caching:** Redis
- **Authentication:** JWT (JSON Web Tokens)
- **AI Model:** Gemini AI

## 🌟 Features
- **User Authentication:** JWT-based authentication for secure login.
- **Sentiment Analysis:** Weekly sentiment analysis using Gemini AI.
- **Redis Caching:** Stores weekly analysis results for quick access.
- **Email Notifications:** Automated weekly sentiment report via email.
- **Swagger Documentation:** API documentation for easy integration.

## 📁 Project Structure
```
/JournalApp
├── src
│   ├── main
│   │   ├── java (Backend Code)
│   │   └── resources
│   └── frontend (React Frontend)
├── target
└── README.md
```

## 🔧 Installation and Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/Uday8897/JournalApp.git
   cd JournalApp
   ```
2. Backend Setup:
   - Navigate to the backend directory and configure `application.properties` for MongoDB and Redis.
   - Run the Spring Boot application.
3. Frontend Setup:
   - Navigate to the `frontend` folder.
   - Install dependencies:
   ```bash
   npm install
   npm start
   ```

## 📊 API Endpoints
Swagger documentation available at: `/swagger-ui.html`

## 📧 Weekly Email Reports
Sentiment analysis reports are sent every week using Spring Scheduler.

## 🤝 Contributing
Feel free to contribute! Open issues and submit pull requests for improvements.

## 📄 License
This project is licensed under the MIT License.

## 🌐 Connect
[GitHub Repository](https://github.com/Uday8897/JournalApp)

