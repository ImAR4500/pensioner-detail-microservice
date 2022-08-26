FROM openjdk:8
ADD target/pensionerdetailapp.jar pensionerdetailapp.jar
ENTRYPOINT ["java", "-jar","pensionerdetailapp.jar"]
EXPOSE 9081