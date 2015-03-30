# Introduction #

The Java Entity Management Service (JEMS) allows remote clients to interact directly with Java JPA entities.

A generic Data Access Object (DAO) provides CRUD operations (i.e. Create, Read, Update and Delete) on Java JPA entities. The JEMS service layer then exposes these CRUD operations to remote clients.

The first version contains a Flex/AS3 client implementation that communicates with the JEMS service using the AMF protocol.

Future versions will include new clients (e.g. Javascript, C# etc) that will use a variety of protocols (e.g SOAP/REST).

The project contains a number of [Modules](Modules.md) that enable rapid development. An ORM generator can analyse a set of Java Beans and create a JPA mapping file automatically. A Java to AS3 compiler can take a set of Java Beans and convert them into AS3 classes.

The best way to understand JEMS is to try it. This [Example](Example.md) shows a FLEX/AS3 client interacting with JEMS running in the Maven Jetty servlet container.

You can either [check out](http://code.google.com/p/javaems/source/checkout) the sources, [download](http://code.google.com/p/javaems/downloads/list) the sources and the javadoc or browse the [javadoc](http://javaems.googlecode.com/svn/javadocs/index.html).

A git version of this project can be found [here](http://code.google.com/p/entity-management-service/).