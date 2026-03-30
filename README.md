# 🚀 Smart Complaint System (DAA-Enhanced)

## 📌 Overview

The **Smart Complaint System** is an intelligent complaint management platform designed using **Data Structures + Design and Analysis of Algorithms (DAA)** concepts.

It enables:

* 📥 Complaint submission
* ⚡ Smart prioritization
* 🤖 Auto-classification
* 🔗 Related complaint detection
* 🧠 Efficient processing using algorithms

---

## 🎯 Objective

To build a **real-world scalable system** that:

* Reduces manual effort in complaint handling
* Optimizes complaint processing using **DAA techniques**
* Demonstrates practical use of algorithms in software systems

---

## 🧠 DAA Concepts Used

| Algorithm            | Use Case                            |
| -------------------- | ----------------------------------- |
| **Greedy Algorithm** | Priority-based complaint scheduling |
| **Graph (BFS)**      | Detect related/duplicate complaints |
| **String Matching**  | Auto category detection             |
| **Binary Search**    | Fast complaint lookup               |
| **Hashing**          | O(1) complaint retrieval            |

---

## 🔥 Key Features

### ✅ 1. Smart Priority Scheduling (Greedy)

* Complaints are processed based on priority
* Priority = Severity + Urgency + Impact

---

### 🤖 2. Auto Category Detection

* System detects category from description
* Example:

  * “server down” → IT
  * “electric issue” → Maintenance

---

### 🔗 3. Related Complaint Detection (Graph + BFS)

* Finds similar complaints
* Helps detect duplicates

---

### ⚡ 4. Fast Search (Binary + Hashing)

* Instant lookup by ID
* Optimized searching

---

### 📊 5. Role-Based Panels

* 👤 User → Submit complaints
* 🧑‍💼 Team → Manage complaints
* 🛠️ Admin → Process complaints

---

## 🏗️ Project Architecture

```
UI Layer (Swing)
   ↓
Service Layer (DAA Logic)
   ↓
DAO Layer (Database)
   ↓
MySQL Database
```

---

## 📁 Project Structure

```
src/
 ├── dao/
 │   ├── ComplaintDAO.java
 │   ├── UserDAO.java
 │   └── DBConnection.java
 │
 ├── model/
 │   ├── Complaint.java
 │   └── User.java
 │
 ├── service/
 │   └── ComplaintService.java
 │
 ├── ui/
 │   ├── LoginUI.java
 │   ├── UserUI.java
 │   ├── TeamUI.java
 │   └── AdminUI.java
 │
 └── util/
     └── AppContext.java
```

---

## ⚙️ Tech Stack

* **Language:** Java
* **UI:** Java Swing
* **Database:** MySQL
* **Concepts:** DSA + DAA

---

## 🛠️ How to Run (Step-by-Step)

### 1️⃣ Install Requirements

* Java JDK (17+ recommended)
* MySQL Server
* VS Code / IntelliJ

---

### 2️⃣ Setup Database

Open MySQL and run:

```sql
CREATE DATABASE complaint_system;

USE complaint_system;

CREATE TABLE complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    category VARCHAR(100),
    severity INT,
    urgency INT,
    impact INT,
    priority DOUBLE,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE users (
    username VARCHAR(100) PRIMARY KEY,
    password VARCHAR(100),
    role VARCHAR(50)
);
```

---

### 3️⃣ Add Sample Users

```sql
INSERT INTO users VALUES ('admin', 'admin123', 'ADMIN');
INSERT INTO users VALUES ('user1', 'user123', 'USER');
INSERT INTO users VALUES ('it_team', 'team123', 'IT');
```

---

### 4️⃣ Update DB Credentials

Edit:

```
src/dao/DBConnection.java
```

```java
DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/complaint_system",
    "root",
    "your_password"
);
```

---

### 5️⃣ Run Project

👉 Run:

```
LoginUI.java
```

---

## 🔑 Login Credentials

| Role  | Username | Password |
| ----- | -------- | -------- |
| Admin | admin    | admin123 |
| User  | user1    | user123  |
| Team  | it_team  | team123  |

---

## 🚀 How It Works

### 👤 User Flow

1. Login
2. Submit complaint
3. System calculates priority
4. Auto category detection

---

### 🛠️ Admin Flow

1. Click "Process Complaint"
2. Highest priority complaint selected (Greedy)
3. Assigned to team

---

### 👨‍💼 Team Flow

1. View assigned complaints
2. Update status
3. Search complaints

---

## 📊 Example

```
Severity = 9
Urgency = 8
Impact = 7

Priority = 8.2
→ Processed first
```

---

## 🌟 Unique Points (Mentor Catchy)

* Real-world use of **DAA in software system**
* Intelligent complaint handling
* Graph-based relationship detection
* Explainable priority system
* Scalable architecture

---

## 📈 Future Enhancements

* 📱 Web / Mobile version
* 🤖 AI-based NLP classification
* 📊 Analytics dashboard
* ☁️ Cloud deployment

---

## 🚀 Deployment Guide (Basic)

### Option 1: Local Deployment

* Run using IDE
* Connect to local MySQL

---

### Option 2: Cloud (Advanced)

* Backend → Spring Boot (optional upgrade)
* DB → MySQL (AWS RDS)
* Hosting → Render / Railway

---

## 🧠 Learning Outcomes

* Practical use of **DAA concepts**
* System design using layered architecture
* Database integration
* Real-world problem solving

---

## 📌 Conclusion

This project demonstrates how **Design and Analysis of Algorithms** can be applied to build:

* Efficient
* Scalable
* Intelligent systems

---

## 👨‍💻 Author

* **Shubham Kumar**
* **Parikshit Singh**
* **avni negi**
* **jahnvi sharma**

---

## ⭐ If you like this project

Give it a ⭐ on GitHub!

---

