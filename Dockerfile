FROM openjdk:8
COPY target/*.jar /usr/lib/kube-demo-frontend.jar
WORKDIR /usr/lib/
CMD ["java", "-jar" , "kube-demo-frontend.jar"]