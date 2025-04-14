FROM eclipse-temurin:21-jdk-noble AS build
WORKDIR /shop
COPY . .
RUN \
tr -d '\015' <mvnw >mvnw_temp && \
chmod 777 mvnw_temp && \
rm mvnw && \
mv mvnw_temp mvnw && \
bash ./mvnw -Dmaven.test.skip=true clean package

FROM eclipse-temurin:21-jdk-noble
COPY --from=build /shop/target/shop.jar /shop/target/shop.jar
COPY --from=build /shop/src/main/resources/static /shop/src/main/resources/static
CMD /shop/target/shop.jar