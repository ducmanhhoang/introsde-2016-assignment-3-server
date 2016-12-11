package introsde.document.mapping;

import java.util.ArrayList;
import java.util.List;

import introsde.document.bean.MeasureBean;
import introsde.document.bean.PersonBean2;
import introsde.document.model.Person;

public class PersonBeanMapping2 {

//	public static Person mapToPerson(PersonBean personBean) {
//		
//	}

	public static PersonBean2 mapFromPerson(Person person) {
		PersonBean2 pb2 = new PersonBean2();
		pb2.setBirthday(person.getBirthdate());
		List<MeasureBean> currentHealth = new ArrayList<MeasureBean>();
		for (int i = 0; i < person.getLifeStatus().size(); i ++) {
			currentHealth.add(MeasureBeanMapping.mapFromLifeStatus(person.getLifeStatus().get(i)));
		}
		pb2.setCurrentHealth(currentHealth);
		pb2.setFirstname(person.getName());
		pb2.setLastname(person.getLastname());
		pb2.setPersonId(Long.valueOf(person.getIdPerson()));
		return pb2;
	}
}
