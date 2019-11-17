FROM maven:3.6.2-jdk-8-slim AS build
COPY . /home/app
COPY pom.xml /home/app
WORKDIR /home/app
EXPOSE 30000-50000
RUN mvn clean install
ENTRYPOINT ["mvn", "clean"]
CMD ["test"]

