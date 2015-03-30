The source tree has the following modules:

**Client**

The client module contains the sources of the client implementations that communicate with the JEMS server.

For example the client/flex directory contains the Flex AS3 client.

**Compiler**

The compiler module contains a Java to AS3 compiler that will translate Java Beans into AS3 classes. Future releases will contain compilers that translate Java Beans into other languages.

**Dao**

The DAO module consists of a generic DAO component that performs generic Create, Read, Update and Delete (CRUD) operations on a database using JPA.

**Etc**

The etc directory contains a properties file.

**Example**

The example module contains an example model, JEMS client and server.

**Generator**

The Generator module is a component that has Code generation classes that are used by both the Compiler and ORM modules.

**Model**

The model module contains the model used to define the JEMS service model. The model is defined using in 2 XSD files: service.xsd defines the service and dao.xsd defines the dao data structures. The server directory contains the java implementation of the model and the client directory contains the flex implementation.

**ORM**

The ORM module has a ORM mapping generator which analyes Java Beans and generates JPA mapping files.

**Server**

The server module contains JEMS server implementations. Currently there is just an AMF server implementation.

**Service**

The service modules contains the JEMS services to manage entities. The service layer uses the DAO layer and in turn is used by the server layer.

**Template**

The template module is a Java templating component used by the Code generators. Template files contains Java classes and literal strings are processed into Java classes which can be loaded into a Java debugger - templates often need debugging!