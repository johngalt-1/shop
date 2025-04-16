FROM eclipse-temurin:21-jdk-noble
WORKDIR /shop
COPY . .
RUN \
tr -d '\015' <mvnw >mvnw_temp && \
chmod 777 mvnw_temp && \
rm mvnw && \
mv mvnw_temp mvnw
CMD bash ./mvnw test