**Software**

To build the JEMS software, you will need Java (1.6), Maven (2.2.1) and MySQL(5) installed on your computer.

If you skip the maven tests during build, you can avoid setting up a MySQL database.

**Database**

Create a database called jems in MySQL.

**Environment variables**

Create an environment variable callsed JEMS\_HOME which should point to where you checked out the source. JEMS\_HOME is used to find the etc directory that contains the properties file used by JEMS.

**Properties**

The file etc.properties contains the database connection details. You can use the software with another database if you change the jdbc connection details and hibernate dialect in the properties file.

**Building the software**

use mvn install to build and install the system into your maven repository.