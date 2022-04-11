FROM openjdk:17
ADD target/DockerLikeService.jar DockerLikeService.jar
EXPOSE 3015
ENTRYPOINT ["java","-jar","DockerLikeService.jar"]