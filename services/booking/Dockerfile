# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY target/booking-0.0.1-SNAPSHOT.jar app.jar

# Make port 8081 available to the world outside this container
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"] 