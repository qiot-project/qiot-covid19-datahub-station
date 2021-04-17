chmod +x mvn*
mvn -N io.takari:maven:wrapper
./mvnw clean package -Pnative  -Dquarkus.native.container-build=true -Dquarkus.native.container-runtime=docker
docker rmi quay.io/qiotcovid19/qiot-datahub-station:1.0.0
docker build -f src/main/docker/Dockerfile.native -t quay.io/qiotcovid19/qiot-datahub-station:1.0.0 .
docker push quay.io/qiotcovid19/qiot-datahub-station:1.0.0
#docker run -it --rm -p 5000:5000 --net host quay.io/qiot/qiot-datahub-station
