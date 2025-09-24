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
- 📊 Success tracking & analytics (success ratio / lift between feature and control groups)  

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

### Track Success Event
To record when a user action is successful (used later for computing the **success ratio / lift**):

**Endpoint:**  
`POST /api/success/{id}`  

**Headers:**  
X-API-Key: your-api-key  

**Query Parameters:**  
- `userId` – ID of the user triggering the success  

**Request example:**  
POST /api/success/550e8400-e29b-41d4-a716-446655440000?userId=user123  

**Response:**  
200 OK  

This increments the **success count** for the user’s assigned group (feature vs control). The frontend dashboard uses these counts to calculate the **success ratio (lift)** of the feature against the control group.

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
<img width="1901" height="977" alt="Screenshot_20250924_233403" src="https://github.com/user-attachments/assets/f0de895e-6b80-4c32-808d-407547bcda23" />


### Creating new Feature Flag
<img width="1901" height="977" alt="Screenshot_20250920_201347" src="https://github.com/user-attachments/assets/947d5533-af61-4dd7-b29d-4dfd33ed5df1" />
