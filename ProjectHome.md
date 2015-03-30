The Java Entity Management Service (JEMS) allows remote clients to interact directly with Java JPA entities.

A Generic Data Access Object (DAO) provides CRUD operations (i.e. Create, Read, Update and Delete) on Java JPA entities. The JEMS service layer then exposes these CRUD operations to remote clients.

The first version contains a Flex/AS3 client implementation that communicates with the JEMS service using the AMF protocol. You can see the Flex client running an example Artist/Album/Track example [here](http://www.jamescowan.org.uk/jems-example-flex-client/).

Future versions will include new clients (e.g. Javascript etc) that will use a variety of protocols (e.g SOAP/REST).

The REST server version of JEMS has now been implemented:

  * Click [here](http://www.jamescowan.org.uk/jems-example-server-rest/meta/) to display the entity meta data of the example application in your browser.
  * Click [here](http://www.jamescowan.org.uk/jems-example-server-rest/Artist/) to display all the Artists.

A git version of this project can be found at [here](http://code.google.com/p/entity-management-service/); the scaffolding section of the git project is more advanced.