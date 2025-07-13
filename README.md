# MTK
 MTK 是一個電影查詢與訂票系統，採用 DDD (Domain-Driven Design)
 並使用OOAD分析設計類別圖，並使用流程圖和使用者情境，輔助分析使用流程

## 主要框架

- **Spring Boot**：API介面，核心應用框架。
- **Spring Security**：處理使用者驗證與授權。
- **Redis**：作為快取層，加速登入資料的存取與訂票時RedisLock，例如:登入後的快取、訂票後RedisLock 座位。
- **Kafka**：用於事件傳遞的非同步溝通，例如:讀寫資料、訂票後寄eamil。
- **PostgreSQL / Elasticsearch**：採讀寫分離設計，寫入資料保存於 PostgreSQL，搜尋與查詢透過 Elasticsearch。
- **DDD 架構**：程式碼依照 `domain`、`app`、`infra`、`web` 等分層實作。
- **Docker Compose**：快速建置所需服務 (資料庫、Kafka、Redis、Elasticsearch 等)。
- **GitHub Action**：完成自動化CICD，部署至GCP雲端 VM

## 目錄結構
 ```

 MTK
 ├── MTK_SA          # 系統分析與設計文件
 ├── MTK_SRC         # 主要程式碼與 Docker 設定
 │   ├── src/main/java/idv/po/mtk_src
 │   │   ├── movie            # 電影相關模組 (domain/app/infra/web)
 │   │   └── booking          # 訂票相關模組
 │   │   └── management       # 後台管理相關模組
 │   │   └── infrastructure   # 中間件與DB依賴模組
 │   │   └── member           # 前台會員模組
 │   ├── docker-compose.yml
 │   └── pom.xml
 └── README.md
 ```
 
## 執行方式
 
 1. 進入 `MTK_SRC` 目錄後打包專案：
    ```bash
    ./mvnw clean package -DskipTests
    ```
 2. 使用 Docker Compose 啟動應用與相關服務：
    ```bash
    docker-compose up
    ```
    預設會開啟 PostgreSQL(寫入)、Elasticsearch(查詢)、Kafka、Redis 及應用程式等容器。
 
 啟動完成後即可透過 API 進行電影查詢及訂票相關操作。
 
## CI/CD 流程

專案提供 GitHub Actions 工作流程（配置於 `.github/workflows/main.yml`），自動化完成下列步驟：

1. Maven 編譯並執行測試。
2. 建立 Docker 映像檔並推送至 Docker Hub。
3. 透過 SSH 登入 GCP VM，使用 `docker-compose pull` 與 `docker-compose up -d` 完成部署。

此流程會在拉取請求合併至 `main` 分支後觸發，確保程式碼更新能即時套用至雲端環境。


