**Introduction**

The Example directory contains a sample Java/Flex JEMS application.

The application is a simple Artist/Album/Track library application.

**Model**

The model is defined using XSD - see example/model/server/src/main/xsd/model.xsd. The XSD is converted into Java classes by The JiBX XSD compiler. The Java to AS3 compiler is used to convert the model from Java classes to AS3 classes,

**AMF Server**

The server directory contains an AMF server. Change directory into the example/server/amf and start the server using mvn jetty:run.

**Flex Client**

The client directory contains a Flex client that manages the entities defined in the model. Change directory into example/clientflex and start the client by entering the command target/jems-example-client-flex.swf.

If everything is working correctly, you should be add, delete and edit Artists, Albums and Tracks.

