package introsde.document.ws;

import introsde.document.bean.MeasureBean;
import introsde.document.bean.PersonBean;
import introsde.document.bean.PersonBean2;
import introsde.document.mapping.MeasureBeanMapping;
import introsde.document.mapping.PersonBeanMapping;
import introsde.document.mapping.PersonBeanMapping2;
import introsde.document.model.HealthMeasureHistory;
import introsde.document.model.LifeStatus;
import introsde.document.model.Person;
import introsde.document.utils.RandomData;
import introsde.document.model.MeasureDefinition;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

//Service Implementation

@WebService(endpointInterface = "introsde.document.ws.People", serviceName = "PeopleService")
public class PeopleImpl implements People {

	/*
	 * (non-Javadoc)
	 * 
	 * @see introsde.document.ws.People#getPersonBean()
	 * 
	 * Method #1: readPersonList() => List | should list all the people in the
	 * database (see below Person model to know what data to return here) in
	 * your database
	 */
	@Override
	public List<PersonBean> readPersonList() {
		List<PersonBean> lpb = new ArrayList<PersonBean>();
		List<Person> lp = Person.getAll();
		PersonBean pb = null;
		for (int i = 0; i < lp.size(); i++) {
			List<HealthMeasureHistory> hmhs = HealthMeasureHistory.getAll();
			pb = PersonBeanMapping.mapFromPerson(lp.get(i), hmhs);
			lpb.add(pb);
		}
		return lpb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see introsde.document.ws.People#readPerson(int)
	 * 
	 * Method #2: readPerson(Long id) => Person | should give all the Personal
	 * information plus current measures of one Person identified by {id} (e.g.,
	 * current measures means current healthProfile)
	 */

	@Override
	public PersonBean2 readPerson(int id) {
		Person p = Person.getPersonById(id);
		PersonBean2 pb2 = PersonBeanMapping2.mapFromPerson(p);
		return pb2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see introsde.document.ws.People#updatePerson(introsde.document.bean.
	 * PersonBean)
	 * 
	 * Method #3: updatePerson(Person p) => Person | should update the Personal
	 * information of the Person identified by {id} (e.g., only the Person's
	 * information, not the measures of the health profile)
	 */

	@Override
	public int updatePerson(PersonBean personBean) {
		Person person = PersonBeanMapping.mapToPerson(personBean);
		person = Person.updatePerson(person);
		return person.getIdPerson();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see introsde.document.ws.People#createPerson(introsde.document.bean.
	 * PersonBean)
	 * 
	 * Method #4: createPerson(Person p) => Person | should create a new Person
	 * and return the newly created Person with its assigned id (if a health
	 * profile is included, create also those measurements for the new Person).
	 */
	@Override
	public PersonBean createPerson(PersonBean personBean) {
		Person person = PersonBeanMapping.mapToPerson(personBean);
		person = Person.savePerson(person);
		List<HealthMeasureHistory> hmhs = HealthMeasureHistory.getAll();
		personBean = PersonBeanMapping.mapFromPerson(person, hmhs);
		return personBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see introsde.document.ws.People#deletePerson(int)
	 * 
	 * Method #5: deletePerson(Long id) should delete the Person identified by
	 * {id} from the system
	 */

	@Override
	public int deletePerson(int id) {
		Person p = Person.getPersonById(id);
		if (p != null) {
			Person.removePerson(p);
			return id;
		} else {
			return 0;
		}
	}

	/*
	 * Method #6: readPersonHistory(Long id, String measureType) => List should
	 * return the list of values (the history) of {measureType} (e.g. weight)
	 * for Person identified by {id}
	 */

	@Override
	public List<MeasureBean> readPersonHistory(Long id, String measureType) {
		List<HealthMeasureHistory> hmhs = HealthMeasureHistory.getAll();
		List<MeasureBean> mbs = new ArrayList<MeasureBean>();
		for (HealthMeasureHistory hmh : hmhs) {
			if (hmh.getPerson().getIdPerson() == id) {
				MeasureBean mb = MeasureBeanMapping.mapFromHealthMeasureHistory(hmh);
				mbs.add(mb);
			}
		}
		return mbs;
	}
	
	/*
	 * Method #7: readMeasureTypes() => List should return the list of measures
	 */

	@Override
	public List<String> readMeasureTypes() {
		List<MeasureDefinition> mds = MeasureDefinition.getAll();
		List<String> mmts = new ArrayList<String>();
		for (MeasureDefinition md : mds) {
			mmts.add(md.getMeasureName());
		}
		return mmts;
	}
	
	
	
	

	/*
	 * Method #8: readPersonMeasure(Long id, String measureType, Long mid)
	 * => Measure should return the value of {measureType} (e.g. weight)
	 * identified by {mid} for Person identified by {id}
	 */

	@Override
	public MeasureBean readPersonMeasure(Long id, String measureType, Long mid) {
		List<HealthMeasureHistory> hmhs = HealthMeasureHistory.getAll();
		MeasureBean mb = null;
		for (HealthMeasureHistory hmh : hmhs) {
			if (hmh.getPerson().getIdPerson() == id && hmh.getIdMeasureHistory() == mid) {
				mb = MeasureBeanMapping.mapFromHealthMeasureHistory(hmh);
				return mb;
			}
		}
		return null;
	}

	/*
	 * Method #9: savePersonMeasure(Long id, Measure m) =>should save a new
	 * measure object {m} (e.g. weight) of Person identified by {id} and archive
	 * the old value in the history
	 */

	@Override
	public MeasureBean savePersonMeasure(Long id, MeasureBean measureBean) {
		HealthMeasureHistory hmh = new HealthMeasureHistory();
		// Get id of measure type in measure definition table
		List<MeasureDefinition> mds = MeasureDefinition.getAll();
		int idMD = 0;
		for (MeasureDefinition md : mds) {
			if (md.getMeasureName().equalsIgnoreCase(measureBean.getMeasureType())) {
				idMD = md.getIdMeasureDef();
				break;
			}
		}

		MeasureDefinition md = MeasureDefinition.getMeasureDefinitionById(idMD);
		hmh.setMeasureDefinition(md);

		Person person = Person.getPersonById(id.intValue());
		hmh.setPerson(person);

		for (LifeStatus ls : person.getLifeStatus()) {
			if (ls.getMeasureDefinition().getIdMeasureDef() == idMD) {
				hmh.setValue(ls.getValue());
			}
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String created = dateFormat.format(new Date());
		hmh.setTimestamp(created);

		if (hmh.getValue() != null)
			HealthMeasureHistory.saveHealthMeasureHistory(hmh);

		LifeStatus ls = MeasureBeanMapping.mapToLifeStatus(measureBean, id);
		ls = LifeStatus.saveLifeStatus(ls);
		return MeasureBeanMapping.mapFromLifeStatus(ls);
	}

	/*
	 * Method #10: updatePersonMeasure(Long id, Measure m) => Measure
	 * | should update the measure identified with {m.mid}, related to the
	 * Person identified by {id}
	 * 
	 */

	@Override
	public MeasureBean updatePersonMeasure(Long id, MeasureBean measureBean) {
		LifeStatus ls = MeasureBeanMapping.mapToLifeStatus(measureBean, id);
		measureBean = MeasureBeanMapping.mapFromLifeStatus(LifeStatus.updateLifeStatus(ls));
		return measureBean;
	}
	
	/*
	 * (non-Javadoc)
	 * @see introsde.document.ws.People#createDatabase()
	 *
	 * Create database.
	 */

	@Override
	public String createDatabase() {
	    Connection c = null;
	    Statement stmt = null;
	    try {
	    	File file = new File("lifecoach.sqlite");
	    	if (file.exists()) {
	    		file.delete();
	    	}
	    	
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:lifecoach.sqlite");
	      System.out.println("Opened database successfully");
	      
	      RandomData rd = new RandomData();
	      
	      
	      String insertPerson = "";
	      for (int i = 1; i <= 10;  i ++) {
	    	  String firstname = rd.getRandomFirstName();
	    	  String lastname = rd.getRandomLastName();
	    	  String username = rd.getRandomUsername(firstname, lastname);
	    	  insertPerson = insertPerson + "INSERT INTO \"Person\" VALUES(" + i + ",'" + firstname + "','" + lastname + "','" + rd.getRandomBirthdate() + "','" + rd.getRandomEmail(username) + "','" + username + "');";
	      }
	      
	      String insertLifeStatus = "";
	      int j = 0;
	      for (int i = 1; i <= 10; i ++) {
	    		  insertLifeStatus = insertLifeStatus + "INSERT INTO \"LifeStatus\" VALUES(" + (j = j + 1) + ",1," + i + ",'" + rd.getRandomWeight() +"');";
	    		  insertLifeStatus = insertLifeStatus + "INSERT INTO \"LifeStatus\" VALUES(" + (j = j + 1) + ",2," + i + ",'" + rd.getRandomHeight() + "');";
	    		  insertLifeStatus = insertLifeStatus + "INSERT INTO \"LifeStatus\" VALUES(" + (j = j + 1) + ",3," + i + ",'" + rd.getRandomWeight() + "');";
	    		  insertLifeStatus = insertLifeStatus + "INSERT INTO \"LifeStatus\" VALUES(" + (j = j + 1) + ",4," + i + ",'" + rd.getRandomWeight() + "');";
	    		  insertLifeStatus = insertLifeStatus + "INSERT INTO \"LifeStatus\" VALUES(" + (j = j + 1) + ",5," + i + ",'" + rd.getRandomWeight() + "');";
	    		  insertLifeStatus = insertLifeStatus + "INSERT INTO \"LifeStatus\" VALUES(" + (j = j + 1) + ",6," + i + ",'" + rd.getRandomWeight() + "');";
	      }
	      
	      
	      String insertHealthMeasureHistory = "";
	      j = 0;
	      for (int i = 1; i <= 10; i ++)
	    	  for (int k = 1; k <= 6; k ++) {
	    		  if (k == 2) {
	    			  insertHealthMeasureHistory = insertHealthMeasureHistory + "INSERT INTO \"HealthMeasureHistory\" VALUES(" + (j = j + 1) + "," + i + "," + k + ",'" + rd.getRandomHeight() + "','" + rd.getRandomTimestamp() + "'," + k + ");";
		    		  insertHealthMeasureHistory = insertHealthMeasureHistory + "INSERT INTO \"HealthMeasureHistory\" VALUES(" + (j = j + 1) + "," + i + "," + k + ",'" + rd.getRandomHeight() + "','" + rd.getRandomTimestamp() + "'," + k + ");";
		    		  insertHealthMeasureHistory = insertHealthMeasureHistory + "INSERT INTO \"HealthMeasureHistory\" VALUES(" + (j = j + 1) + "," + i + "," + k + ",'" + rd.getRandomHeight() + "','" + rd.getRandomTimestamp() + "'," + k + ");";
	    		  } else {
	    			  insertHealthMeasureHistory = insertHealthMeasureHistory + "INSERT INTO \"HealthMeasureHistory\" VALUES(" + (j = j + 1) + "," + i + "," + k + ",'" + rd.getRandomWeight() + "','" + rd.getRandomTimestamp() + "'," + k + ");";
	    			  insertHealthMeasureHistory = insertHealthMeasureHistory + "INSERT INTO \"HealthMeasureHistory\" VALUES(" + (j = j + 1) + "," + i + "," + k + ",'" + rd.getRandomWeight() + "','" + rd.getRandomTimestamp() + "'," + k + ");";
	    			  insertHealthMeasureHistory = insertHealthMeasureHistory + "INSERT INTO \"HealthMeasureHistory\" VALUES(" + (j = j + 1) + "," + i + "," + k + ",'" + rd.getRandomWeight() + "','" + rd.getRandomTimestamp() + "'," + k + ");";
	    		  }
	    	  }
	      
	      
	      

	      stmt = c.createStatement();
	      String sql = "PRAGMA foreign_keys=OFF;"
	      		+ "BEGIN TRANSACTION;"
	      		+ "CREATE TABLE Person ( "
	      		+ "    idPerson  INTEGER PRIMARY KEY AUTOINCREMENT,"
	      		+ "    name      TEXT  DEFAULT 'NULL',"
	      		+ "    lastname  TEXT  DEFAULT 'NULL',"
	      		+ "    birthdate DATETIME        DEFAULT 'NULL',"
	      		+ "    email     TEXT,"
	      		+ "    username  TEXT   DEFAULT 'NULL'"
	      		+ ");"
	      		+ insertPerson
	      		+ "CREATE TABLE MeasureDefinition ( "
	      		+ "    idMeasureDef INTEGER PRIMARY KEY AUTOINCREMENT,"
	      		+ "    measureName  TEXT  DEFAULT 'NULL',"
	      		+ "    measureType  TEXT  DEFAULT 'NULL'"
	      		+ ");"
	      		+ "INSERT INTO \"MeasureDefinition\" VALUES(1,'weight','double');"
	      		+ "INSERT INTO \"MeasureDefinition\" VALUES(2,'height','double');"
	      		+ "INSERT INTO \"MeasureDefinition\" VALUES(3,'steps','integer');"
	      		+ "INSERT INTO \"MeasureDefinition\" VALUES(4,'blood pressure','double');"
	      		+ "INSERT INTO \"MeasureDefinition\" VALUES(5,'heart rate','integer');"
	      		+ "INSERT INTO \"MeasureDefinition\" VALUES(6,'bmi','double');"
	      		+ "CREATE TABLE LifeStatus ( "
	      		+ "    idMeasure  INTEGER PRIMARY KEY AUTOINCREMENT,"
	      		+ "    idMeasureDef INTEGER       DEFAULT 'NULL',"
	      		+ "    idPerson     INTEGER       DEFAULT 'NULL',"
	      		+ "    value        TEXT,"
	      		+ "    FOREIGN KEY ( idMeasureDef ) REFERENCES MeasureDefinition ( idMeasureDef ) ON DELETE NO ACTION"
	      		+ "                                                                                   ON UPDATE NO ACTION,"
	      		+ "    CONSTRAINT 'fk_LifeStatus_Person1' FOREIGN KEY ( idPerson ) REFERENCES Person ( idPerson ) ON DELETE NO ACTION"
	      		+ "                                                                                                   ON UPDATE NO ACTION "
	      		+ ");"
	      		+ insertLifeStatus
	      		+ "CREATE TABLE MeasureDefaultRange ( "
	      		+ "    idRange       INTEGER PRIMARY KEY AUTOINCREMENT,"
	      		+ "    idMeasureDef INTEGER       DEFAULT 'NULL',"
	      		+ "    rangeName    TEXT   DEFAULT 'NULL',"
	      		+ "    startValue   TEXT,"
	      		+ "    endValue     TEXT,"
	      		+ "    alarmLevel   INTEGER        DEFAULT 'NULL',"
	      		+ "    FOREIGN KEY ( idMeasureDef ) REFERENCES MeasureDefinition ( idMeasureDef ) ON DELETE NO ACTION"
	      		+ "                                                                                   ON UPDATE NO ACTION "
	      		+ ");"
	      		+ "INSERT INTO \"MeasureDefaultRange\" VALUES(1,6,'Overweight','25.01','30',1);"
	      		+ "INSERT INTO \"MeasureDefaultRange\" VALUES(2,6,'Obesity','30.01',NULL,2);"
	      		+ "INSERT INTO \"MeasureDefaultRange\" VALUES(3,6,'Normal weight','20.01','25',0);"
	      		+ "INSERT INTO \"MeasureDefaultRange\" VALUES(4,6,'Underweight',NULL,'20',1);"
	      		+ "CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT NUMBER(19), PRIMARY KEY (SEQ_NAME));"
	      		+ "INSERT INTO \"SEQUENCE\" VALUES('SEQ_GEN',1900);"
	      		+ "CREATE TABLE \"HealthMeasureHistory\" (\"idMeasureHistory\" INTEGER PRIMARY KEY ,\"idPerson\" INTEGER DEFAULT ('NULL') ,\"idMeasureDefinition\" INTEGER DEFAULT ('NULL') ,\"value\" TEXT,\"timestamp\" TEXT DEFAULT ('NULL') ,\"idMeasureDef\" NUMBER(10));"
	      		+ insertHealthMeasureHistory
	      		+ "DELETE FROM sqlite_sequence;"
	      		+ "INSERT INTO \"sqlite_sequence\" VALUES('Person',1852);"
	      		+ "INSERT INTO \"sqlite_sequence\" VALUES('MeasureDefinition',6);"
	      		+ "INSERT INTO \"sqlite_sequence\" VALUES('LifeStatus',2356);"
	      		+ "INSERT INTO \"sqlite_sequence\" VALUES('MeasureDefaultRange',4);"
	      		+ "INSERT INTO \"sqlite_sequence\" VALUES('HealthMeasureHistory',550);"
	      		+ "CREATE INDEX LifeStatus_fk_HealthMeasure_MeasureDefinition_idx ON LifeStatus ( "
	      		+ "    idMeasureDef "
	      		+ ");"
	      		+ "CREATE INDEX LifeStatus_fk_LifeStatus_Person1_idx ON LifeStatus ( "
	      		+ "    idPerson "
	      		+ ");"
	      		+ "CREATE INDEX MeasureDefaultRange_fk_MeasureDefaultRange_MeasureDefinition1_idx ON MeasureDefaultRange ( "
	      		+ "    idMeasureDef "
	      		+ ");"
	      		+ "COMMIT;"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      return e.getClass().getName() + ": " + e.getMessage();
	    }
	    return "Database created successfully";
	}
	
	

}
