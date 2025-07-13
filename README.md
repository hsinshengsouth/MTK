# MTK
 MTK 是一個電影查詢與訂票系統，採用 DDD (Domain-Driven Design)
 並使用OOAD分析設計類別圖，採用流程圖和use case，輔助設計流程

  ## 目錄結構
 ```

 MTK
 ├── MTK_SA          # 系統分析與設計文件
 ├── MTK_SRC         # 主要程式碼與 Docker 設定
 │   ├── src/main/java/idv/po/mtk_src
 │   │   ├── movie      # 電影相關模組 (domain/app/infra/web)
 │   │   └── booking    # 訂票相關模組
 │   │   └── booking    # 管理相關模組
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


