package introsde.document.mapping;

import java.util.ArrayList;
import java.util.List;

import introsde.document.bean.MeasureBean;
import introsde.document.bean.PersonBean;
import introsde.document.model.HealthMeasureHistory;
import introsde.document.model.LifeStatus;
import introsde.document.model.MeasureDefinition;
import introsde.document.model.Person;

public class PersonBeanMapping {

	public static Person mapToPerson(PersonBean personBean) {
		Person person = new Person();
		person.setBirthdate(personBean.getBirthday());
		person.setEmail(null);
		person.setIdPerson(personBean.getPersonId().intValue());
		person.setLastname(personBean.getLastname());
		
		List<MeasureBean> mbs = personBean.getCurrentHealth();
		List<LifeStatus> lfs = new ArrayList<LifeStatus>();
		if(mbs != null) {
			for (int i = 0; i < mbs.size(); i ++) {
				LifeStatus lf = new LifeStatus();
				lf.setIdMeasure(mbs.get(i).getMid().intValue());
				List<MeasureDefinition> mds = MeasureDefinition.getAll();
				for (int j = 0; j < mds.size(); j ++)
					if (mds.get(j).getMeasureName().equalsIgnoreCase(mbs.get(i).getMeasureType()))
						lf.setMeasureDefinition(mds.get(j));
				lf.setPerson(person);
				lf.setValue(mbs.get(i).getMeasureValue());
				lfs.add(lf);
			}
		}
		person.setLifeStatus(lfs);
		
		person.setName(personBean.getFirstname());
		person.setUsername(null);
		return person;
	}

	public static PersonBean mapFromPerson(Person person, List<HealthMeasureHistory> hmhs) {
		PersonBean personBean = new PersonBean();
		personBean.setBirthday(person.getBirthdate());
		List<MeasureBean> currentHealth = new ArrayList<MeasureBean>();
		for (int i = 0; i < person.getLifeStatus().size(); i ++) {
			currentHealth.add(MeasureBeanMapping.mapFromLifeStatus(person.getLifeStatus().get(i)));
		}
		personBean.setCurrentHealth(currentHealth);
		
		List<MeasureBean> healthHistory = new ArrayList<MeasureBean>();
		for (int i = 0; i < hmhs.size(); i ++) {
			if (hmhs.get(i).getPerson().getIdPerson() == person.getIdPerson()) {
				healthHistory.add(MeasureBeanMapping.mapFromHealthMeasureHistory(hmhs.get(i)));
			}
		}
		personBean.setHealthHistory(healthHistory);
		personBean.setFirstname(person.getName());
		personBean.setLastname(person.getLastname());
		personBean.setPersonId(Long.valueOf(person.getIdPerson()));
		return personBean;
	}
}
