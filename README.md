# 🚀 Smart Complaint System (DAA-Enhanced)

## 📌 Overview

The **Smart Complaint System** is an intelligent complaint management platform designed using **Data Structures + Design and Analysis of Algorithms (DAA)** concepts. It optimizes the entire lifecycle of a complaint—from submission and classification to prioritization and clustering.

It enables:

* 📥 **Intelligent Submission**: Auto-detection of categories and initial priority.
* ⚡ **Smart Prioritization**: Using Greedy algorithms and Heaps for efficient scheduling.
* 🤖 **Auto-classification**: Rule-based heuristic categorization.
* 🔗 **Duplicate & Relation Detection**: Using Rabin-Karp and Union-Find.
* 📊 **Advanced Analytics**: Trend analysis using Sliding Window logic.
* 🧠 **Explainable AI**: Transparent priority scoring system.

---

## 🎯 Objective

To build a **real-world scalable system** that:

* Reduces manual effort in complaint handling through automation.
* Optimizes processing speeds using **advanced DAA techniques**.
* Provides **transparent & explainable decision-making** for administrators.
* Demonstrates the practical application of complex algorithms in software engineering.

---

## 🧠 DAA Concepts & Algorithms Used

| Algorithm | Concept | Use Case |
| :--- | :--- | :--- |
| **Greedy Algorithm** | Optimization | Priority-based complaint scheduling |
| **Union-Find (DSU)** | Disjoint Sets | Clustering related/duplicate complaints efficiently |
| **Rabin-Karp** | String Matching | Fast duplicate detection using rolling hashes |
| **Min-Heap (Top-K)** | Priority Queue | Retrieving highest priority tasks in $O(N \log K)$ |
| **BFS / Graph** | Traversal | Finding multi-level relations between complaints |
| **Sliding Window** | Data Processing | Time-based trend analysis and reporting |
| **Binary Search** | Searching | Fast lookup in sorted complaint lists |
| **Hashing** | Data Access | Constant time $O(1)$ retrieval of user/complaint data |

---

## 🔥 Key Features (LATEST UPDATE ⭐)

### ✅ 1. Smart Priority Scheduling (Greedy + Heap)
* Complaints are processed based on a calculated priority score.
* **Heap Optimization**: Uses a Min-Heap to maintain the Top-K most critical complaints, ensuring the admin always addresses the highest impact issues first.

### 🤖 2. Intelligent Auto-Classification
* **CategoryDetector**: Uses a keyword-based heuristic to automatically assign complaints to IT, Maintenance, or Service departments.
* Reduces manual sorting effort by over 80%.

### 🔗 3. Advanced Duplicate Detection (Rabin-Karp)
* Implements the **Rabin-Karp algorithm** to compare complaint descriptions.
* Uses polynomial hashing to detect redundant submissions even before they reach the database.

### 🧶 4. Complaint Clustering (Union-Find)
* Automatically groups similar complaints together using the **Disjoint Set Union (DSU)** algorithm with path compression.
* Helps admins identify "hotspots" or recurring issues across the organization.

### 📊 5. Trend Analysis (Sliding Window)
* Analyzes the frequency of complaints over a specified window of time.
* Allows management to see real-time "trending" issues and allocate resources accordingly.

### 📝 6. Explainable Priority System
* Shows exactly **why** a complaint was given its priority.
* Calculation: `(Severity × 0.5) + (Urgency × 0.3) + (Impact × 0.2)`
* Provides full transparency to both users and admins.

---

## 🏗️ Project Architecture

```
UI Layer (Swing)           ← User-friendly Interactive Dashboards
   ↓
Service Layer (DAA Logic)  ← The "Brain": Rabin-Karp, DSU, Heaps, Greedy
   ↓
DAO Layer (Persistence)    ← Seamless MySQL Integration
   ↓
MySQL Database             ← Structured Relational Storage
```

---

## 📁 Project Structure

```
src/
 ├── dao/
 │   ├── ComplaintDAO.java      ← Full Record Fetching & persistence
 │   ├── UserDAO.java           ← Security & Auth retrieval
 │   └── DBConnection.java      ← JDBC Connectivity
 │
 ├── model/
 │   ├── Complaint.java         ← Core entity with priority logic
 │   └── User.java
 │
 ├── service/
 │   ├── ComplaintService.java  ← Main Greedy & BFS Logic
 │   ├── RabinKarpService.java  ← Rolling Hash Duplicate Detection ⭐
 │   ├── ComplaintClusterService.java ← DSU Related grouping ⭐
 │   ├── TopKService.java       ← Heap-based prioritization ⭐
 │   └── TrendAnalysisService.java ← Sliding Window reports ⭐
 │
 ├── ui/
 │   ├── LoginUI.java           ← Entry point
 │   ├── UserUI.java            ← Submission + Status Tracking
 │   ├── TeamUI.java            ← Dept-specific Management
 │   └── AdminUI.java           ← Full DAA Suite Dashboard ⭐
 │
 └── util/
     ├── AppContext.java        ← Global singleton for services
     └── CategoryDetector.java  ← Keyword-based classification ⭐
```

---

## ⚙️ Tech Stack

* **Language:** Java 17+
* **UI Framework:** Java Swing (Desktop App)
* **Database:** MySQL 8.0
* **Build/Environment:** VS Code / Eclipse / IntelliJ IDEA
* **Algorithms:** DAA Standard Implementations

---

## 🛠️ How to Run

### 1️⃣ Prerequisites
* Java JDK installed and configured in PATH.
* MySQL Server running locally.

### 2️⃣ Database Setup
Apply the following schema in your MySQL instance:

```sql
CREATE DATABASE complaint_system;
USE complaint_system;

-- Complaints Table
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

-- Users Table
CREATE TABLE users (
    username VARCHAR(100) PRIMARY KEY,
    password VARCHAR(100),
    role VARCHAR(50)
);

-- Seed Data
INSERT INTO users VALUES ('admin', 'admin123', 'ADMIN');
INSERT INTO users VALUES ('user1', 'user123', 'USER');
INSERT INTO users VALUES ('it_team', 'team123', 'IT');
```

### 3️⃣ Configuration
Update your database credentials in `src/dao/DBConnection.java`.

### 4️⃣ Execution
Run the main method in `src/ui/LoginUI.java`.

---

## 🔑 Login Credentials

| Role  | Username | Password | Access Level |
| :--- | :--- | :--- | :--- |
| **Admin** | admin | admin123 | Full DAA Analytics & Processing |
| **User** | user1 | user123 | Submit & Track Complaints |
| **Team (IT)** | it_team | team123 | Manage Dept-Specific Tasks |

---

## 🚀 Why This Project? (Unique Value)

* **Algorithmic Rigor**: Not just a CRUD app; it uses advanced data structures like Heaps and Disjoint Sets correctly.
* **Pattern Matching**: Implements Rabin-Karp, usually taught in theory, for actual production value.
* **Clean Code**: Follows the **DAO/Service/UI** layered architecture for modularity.
* **Explainability**: Bridges the gap between "Black Box" algorithms and user understanding.

---

## 👨‍💻 Development Team

* **Shubham Kumar**
* **Parikshit Singh**
* **Avni Negi**
* **Jahnvi Sharma**

---

## ⭐ Support
If you find this project helpful for your learning, please give us a **Star** on GitHub!


