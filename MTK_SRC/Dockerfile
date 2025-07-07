
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/mtk.jar  /app/mtk.jar
COPY docker-entrypoint.sh /app/docker-entrypoint.sh
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/docker-entrypoint.sh /app/wait-for-it.sh
ENTRYPOINT ["/app/docker-entrypoint.sh"]

