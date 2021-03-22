# QIot Station Service

A microservice responsible for managing the data of the measurement stations.

Data are stored into a table hosted by a MySQL instance.

The Geographical coordinates of the measurement station are retrieved through the connection to the location-service and stored into the table using the GEOMETRY type. Moreover, the coordinates are indexed using SPATIAL index.

## Choose one of:

### To build and create container image

$ ./mvnw clean package

### To build, create container image and push it to registry

$ ./mvnw -Dhttps.protocols=TLSv1.2 clean package -Dquarkus.container-image.push=true

### To build native and create container image

$ ./mvnw clean package -Pnative

### To build native, create container image and push it to registry

$ ./mvnw -Dhttps.protocols=TLSv1.2 clean package -Pnative -Dquarkus.container-image.push=true
