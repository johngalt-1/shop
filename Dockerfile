FROM eclipse-temurin:21-jdk-noble
WORKDIR /shop
COPY . .
CMD \
tr -d '\015' <mvnw >mvnw_temp && \
chmod 777 mvnw_temp && \
rm mvnw && \
mv mvnw_temp mvnw && \
bash ./mvnw clean package && \
./target/shop.jar