FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

RUN chown appuser:appgroup /app

COPY --chown=appuser:appgroup SYSTRACK/target/sys-0.0.1-SNAPSHOT.jar app.jar

USER appuser

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]