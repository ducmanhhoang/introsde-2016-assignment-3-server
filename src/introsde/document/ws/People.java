package introsde.document.ws;

import introsde.document.bean.MeasureBean;
import introsde.document.bean.PersonBean;
import introsde.document.bean.PersonBean2;
import introsde.document.model.LifeStatus;
import introsde.document.model.MeasureDefinition;
import introsde.document.model.Person;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL) // optional
public interface People {

	/*
	 * Method #1: readPersonList() => List | should list all the people in the
	 * database (see below Person model to know what data to return here) in
	 * your database
	 */
	@WebMethod(operationName = "readPersonList")
	@WebResult(name = "peopleResult")
	public List<PersonBean> readPersonList();

	/*
	 * Method #2: readPerson(Long id) => Person | should give all the Personal
	 * information plus current measures of one Person identified by {id} (e.g.,
	 * current measures means current healthProfile)
	 */
	@WebMethod(operationName = "readPerson")
	@WebResult(name = "personResult")
	public PersonBean2 readPerson(@WebParam(name = "personId") int id);

	/*
	 * Method #3: updatePerson(Person p) => Person | should update the Personal
	 * information of the Person identified by {id} (e.g., only the Person's
	 * information, not the measures of the health profile)
	 */
	@WebMethod(operationName = "updatePerson")
	@WebResult(name = "personIdResult")
	public int updatePerson(@WebParam(name = "personBean") PersonBean personBean);

	/*
	 * Method #4: createPerson(Person p) => Person | should create a new Person
	 * and return the newly created Person with its assigned id (if a health
	 * profile is included, create also those measurements for the new Person).
	 */
	@WebMethod(operationName = "createPerson")
	@WebResult(name = "personResult")
	public PersonBean createPerson(@WebParam(name = "personBean") PersonBean personBean);

	/*
	 * Method #5: deletePerson(Long id) should delete the Person identified by
	 * {id} from the system
	 */
	@WebMethod(operationName = "deletePerson")
	@WebResult(name = "personIdResult")
	public int deletePerson(@WebParam(name = "personId") int id);

	/*
	 * Method #6: readPersonHistory(Long id, String measureType) => List should
	 * return the list of values (the history) of {measureType} (e.g. weight)
	 * for Person identified by {id}
	 */
	@WebMethod(operationName = "readPersonHistory")
	@WebResult(name = "healthHistoryResult")
	public List<MeasureBean> readPersonHistory(@WebParam(name = "personId") Long id,
			@WebParam(name = "measureType") String measureType);

	/*
	 * Method #7: readMeasureTypes() => List should return the list of measures 
	 */
	@WebMethod(operationName = "readMeasureTypes")
	@WebResult(name = "measureDefinitionResult")
	public List<String> readMeasureTypes();
	
	/*
	 * Method #8: readPersonMeasure(Long id, String measureType, Long mid)
	 * => Measure should return the value of {measureType} (e.g. weight)
	 * identified by {mid} for Person identified by {id}
	 */
	@WebMethod(operationName = "readPersonMeasure")
	@WebResult(name = "measureResult")
	public MeasureBean readPersonMeasure(@WebParam(name = "personId") Long id, @WebParam(name = "measureType") String measureType, @WebParam(name = "mid") Long mid);
	
	/*
	 * Method #9: savePersonMeasure(Long id, Measure m) =>should save a
	 * new measure object {m} (e.g. weight) of Person identified by {id} and
	 * archive the old value in the history
	 */
	@WebMethod(operationName = "savePersonMeasure")
	@WebResult(name = "measureResult")
	public MeasureBean savePersonMeasure(@WebParam(name = "personId") Long id, @WebParam(name = "measureBean") MeasureBean measureBean);
	
	/*
	 * Method #10: updatePersonMeasure(Long id, Measure m) => Measure
	 * | should update the measure identified with {m.mid}, related to the
	 * Person identified by {id}
	 * 
	 */
	@WebMethod(operationName = "updatePersonMeasure")
	@WebResult(name = "measureResult")
	public MeasureBean updatePersonMeasure(@WebParam(name = "personId") Long id, @WebParam(name = "measureBean") MeasureBean measureBean);

	/* 
	 * Create database
	 */
	@WebMethod(operationName = "createDatabase")
	@WebResult(name = "stringResult")
	public String createDatabase();
	
}