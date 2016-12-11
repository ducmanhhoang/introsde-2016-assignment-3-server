Assignment 03: SOAP Web Services

**Introduction to Service Design and Engineering | University of Trento |** 

**Name:** DUC MANH HOANG (worked alone)
**ID No.:** MAT.180387

**Description:**

In this assignment is implemented a server and a client calling this server. 
The server was deployed on Heroku **https://assignment03.herokuapp.com/ws/people?wsdl**.
Instead the client was implemented to call Heroku server. 




**Code:**


* **introsde.document.bean:** Contain the objects to represent the data of presentation layer. They are different from the schema of database (the minimal model supported by SOAP service).
* **introsde.document.dao:** Contain the classe DAO "data access object".
* **introsde.document.endpoint** Contain the standalone HTTP Server that deploys the SOAP service on local of project.
* **introsde.document.mapping** Contain the classes that implement some utility functions to map from the database schema to the structures of minimal model.
* **introsde.document.model:** Contain the classes to represent the data model, according to the tables in the databse and their relationships. They support some functions to query the database.
* **introsde.document.utils:** Contain the class with some utility functions to generate random data that support for entering data.
* **introsde.document.ws** Contain the interface of the SOAP service and implementation of this interface.



**Server tasks:**



* **Method#1:** readPersonList() => List | should list all the people in the database (see below Person model to know what data to return here) in your database
* **Method#2:** readPerson(Long id) => Person | should give all the Personal information plus current measures of one Person identified by {id} (e.g., current measures means current healthProfile)
* **Method#3:** updatePerson(Person p) => Person | should update the Personal information of the Person identified by {id} (e.g., only the Person's information, not the measures of the health profile)
* **Method#4:** createPerson(Person p) => Person | should create a new Person and return the newly created Person with its assigned id (if a health profile is included, create also those measurements for the new Person).
* **Method#5:** deletePerson(Long id) should delete the Person identified by {id} from the system
* **Method#6:** readPersonHistory(Long id, String measureType) => List should return the list of values (the history) of {measureType} (e.g. weight) for Person identified by {id}
* **Method#7:** readMeasureTypes() => List should return the list of measures
* **Method#8:** readPersonMeasure(Long id, String measureType, Long mid) => Measure should return the value of {measureType} (e.g. weight) identified by {mid} for Person identified by {id}
* **Method#9:** savePersonMeasure(Long id, Measure m) =>should save a new measure object {m} (e.g. weight) of Person identified by {id} and archive the old value in the history
* **Method#10:** updatePersonMeasure(Long id, Measure m) => Measure | should update the measure identified with {m.mid}, related to the Person identified by {id}



**How to run:**

Running the project requires java and ant.

Ant source file build.xml is annotated. 
* Main target is: 
* **start:** To start the local server.





**References:**

https://sites.google.com/a/unitn.it/introsde_2016-17/lab-sessions/assignments/assignment-3

