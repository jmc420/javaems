**Maven**

It is best to use Maven to integrate JEMS into your system. For Flex applications you can use the Flexmojos project to make use of the JEMS Flex client in your projects.

**Download files**

There is no plan to issue download files. Please check out the project, go to
the top level of the trunk and type maven install.

**Maven Properties**

You will need to set a maven property to define the JEMS version. See the top level
pom.xml for the version number:

<jems.version>1.0</jems.version>

**Maven JEMS server dependencies**

To use the JEMS AMF Server in your server side Java application, include the following dependencies:

```
<dependency>
 <groupId>org.jems</groupId>
 <artifactId>jems</artifactId>
 <type>pom</type>
 <version>${jems.version}</version>
</dependency>
 <dependency>
 <groupId>org.jems</groupId>
 <artifactId>jems-server</artifactId>
 <type>pom</type>
 <version>${jems.version}</version>
</dependency>
<dependency>
 <groupId>org.jems</groupId>
 <artifactId>jems-server-amf</artifactId>
 <version>${jems.version}</version>
</dependency>
```
**Maven JEMS client dependencies**

To use the JEMS Flex client library in your client side Flex application, include the following dependencies:

```
<dependency>
 <groupId>org.jems</groupId>
 <artifactId>jems</artifactId>
 <type>pom</type>
 <version>${jems.version}</version>
</dependency>
<dependency>
 <groupId>org.jems</groupId>
 <artifactId>jems-client</artifactId>
 <version>${jems.version}</version>
 <type>pom</type>
</dependency>
<dependency>
 <groupId>org.jems</groupId>
 <artifactId>jems-client-flex</artifactId>
 <version>${jems.version}</version>
 <type>swc</type>
</dependency>
```
For more detail, look at the [Example](Example.md) module which builds sample JEMS client and server applications.