# Myhealthsystem

# 🎓 期末專案
### 📊 智慧健康日誌與多層決策樹風險評估系統 (Smart Health Log System)
本專案為網頁後端開發與雲端應用架構之**期末實作成果展示**。系統基於 **Spring Boot** 框架開發，整合前端 **Tailwind CSS** 動態響應式面板與雲端 **Railway MySQL** 資料庫，並全量部署於雲端 PaaS 平台。核心功能包含每日健康指標監測，並透過**多層分支決策樹代數邏輯**，進行即時的健康風險評估與資料持久化儲存。

### 🛠️ 核心技術棧 (Tech Stack)

#### 前端 UI & 互動層
* **Tailwind CSS**：打造純響應式、現代化且輕量化的健康管理儀表板。
* **JavaScript (Vanilla JS)**：實作無延遲的**多層決策樹即時演算分支**，並透過 `Fetch API` 異步處理與後端 RESTful API 的數據通訊（GET/POST）。

#### 後端服務 & 資料庫層
* **Spring Boot (Web / Data JPA)**：建構強健的 RESTful 服務層，遵循標準的 MVC 架構設計。
* **Hibernate / JPA**：實現物件關係對映 (ORM)，進行全自動建表、物件封裝與資料庫操作。
* **MySQL Database**：於雲端儲存高可靠性的歷史健康指標數據。

---

### 🌲 核心演算法：多層分支決策樹 (Multi-layer Decision Tree)

系統捨棄傳統模糊的觀測法，改採用**嚴格的區間代數分支邏輯**。當使用者輸入健康數據時，前端即時觸發多層條件分支進行交叉比對：

```text
[輸入健康數據：睡眠 hours, 步數 steps, 心情 mood]
       |
       +---> 睡眠時間 < 6.0 小時？
       |         |
       |         +---> (Yes) 步數 < 4000 步？ -------> [ HIGH 高風險 ]
       |         |         |
       |         |         +---> (No) 心情 < 4 分？ --> [ HIGH 高風險 ]
       |         |                    |
       |         |                    +---> (No) ----> [ MEDIUM 中風險 ]
       |
       +---> 睡眠時間 ≥ 6.0 小時？
                 |
                 +---> (Yes) 步數 < 3000 步？
                           |
                           +---> (Yes) 心情 < 5 分？ -> [ MEDIUM 中風險 ]
                           |           |
                           |           +---> (No) ----> [ LOW 低風險 ]
                           |
                           +---> (No) ----------------> [ LOW 低風險 ]


Myhealthsystem/
│
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── DemoApplication.java         # 系統啟動進入點
│   │   │   ├── HealthLog.java               # MySQL 資料庫實體模型 (Entity)
│   │   │   ├── HealthLogRepository.java     # JPA 資料庫存取介面 (Repository)
│   │   │   └── HealthLogController.java     # RESTful API 控制器 (Controller)
│   │   │
│   │   └── resources/
│   │       ├── static/
│   │       │   └── index.html               # 前端 Tailwind CSS 儀表板
│   │       └── application.properties       # 雲端資料庫連線設定檔 (環境變數自動化)
│   │
│   └── test/
│
└── pom.xml                                  # Maven 相依性管理與編譯設定檔
