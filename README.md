# station-service project

A microservice responsible for managing the data of the measurement stations. 

Data are stored into a table hosted by a MySQL instance.

The Geographical coordinates of the measurement station are retrieved through the connection to the location-service and stored into the table using the GEOMETRY type. Moreover, the coordinates are indexed using SPATIAL index.