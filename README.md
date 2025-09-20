# 🚀 FlagShip - Feature Flag Management Platform

A full-stack feature flag management system with **React frontend** and **Spring Boot + MySQL backend**.  
Developers can create, update, and manage feature flags, then evaluate them via an API that returns a boolean value using **sticky deterministic bucketing**.

---

## ✨ Features
- 🔑 User authentication (signup/login)
- 🏷️ CRUD operations for feature flags  
- ⚙️ Configurable attributes: name, description, rollout percentage, allowed countries  
- 🧮 Deterministic rollout evaluation (sticky bucketing)  
- 🌍 Country-based targeting  
- 🔐 API key authentication for external apps  
- 📡 REST API returns `true/false` on feature evaluation  

---

## 🛠️ Tech Stack
- **Frontend:** React  
- **Backend:** Spring Boot  
- **Database:** MySQL  

---

## 📦 Installation & Setup

### Prerequisites
- Java 17+
- Node.js 18+
- MySQL running locally or on cloud

### Backend (Spring Boot)
cd backend  
./mvnw spring-boot:run  


### ⚠️ The frontend is bundled and served from the Spring Boot app. It does not need to be run seperately


### Frontend (React)
cd frontend  
npm install  
npm start  

---

## 📖 API Usage

### Authentication
Each user is issued an **API Key** after login.  
Pass this key in the request header when evaluating a feature flag.

### Evaluate Feature Flag
**Endpoint:**  
`GET /api/evaluate/{id}`  

**Headers:**  
X-API-Key: your-api-key  

**Request example:**  
GET /api/evaluate/550e8400-e29b-41d4-a716-446655440000?userId=user123&userCountry=IN  

**Response:**  
true


---

## 🧮 How Sticky Bucketing Works
1. The system hashes `userId + featureId + salt`.  
2. Hash is mapped into a bucket `0–99`.  
3. If bucket < rollout percentage → feature enabled.  
4. Country filters and whitelist rules override rollout logic.  

This ensures the same user consistently receives the same result.

---

## 📷 Screenshots

### Login Page
<img width="1920" height="978" alt="Screenshot_20250920_201250" src="https://github.com/user-attachments/assets/0b56c26f-700c-4fd0-bdca-087b1c0152a3" />

### Dashboard
<img width="1901" height="980" alt="Screenshot_20250920_201325" src="https://github.com/user-attachments/assets/e13016a1-0ff1-41f3-b565-385cf27d77e4" />

### Creating new Feature Flag
<img width="1901" height="977" alt="Screenshot_20250920_201347" src="https://github.com/user-attachments/assets/947d5533-af61-4dd7-b29d-4dfd33ed5df1" />


