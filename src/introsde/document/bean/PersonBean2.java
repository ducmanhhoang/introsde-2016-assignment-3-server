package introsde.document.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import introsde.document.mapping.MeasureBeanMapping;
import introsde.document.model.HealthMeasureHistory;

@XmlRootElement(name = "person")
@XmlType(propOrder = {"personId", "firstname", "lastname", "birthday", "currentHealth"})
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonBean2 implements Serializable {

	private static final long serialVersionUID = 3166894122446393096L;

	@XmlElement(name = "personId")
	private Long personId;

	@XmlElement(name = "firstname")
	private String firstname;

	@XmlElement(name = "lastname")
	private String lastname;

	@XmlElement(name = "birthday")
	private String birthday;

	@XmlElement(name = "currentHealth")
	private List<MeasureBean> currentHealth;

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setCurrentHealth(List<MeasureBean> currentHealth) {
		this.currentHealth = currentHealth;
	}

	public List<MeasureBean> getCurrentHealth() {
		return currentHealth;
	}

	@Override
	public String toString() {
		return "PersonBean [personId=" + personId + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", birthday=" + birthday + ", currentHealth=" + currentHealth
				+ "]";
	}
}
