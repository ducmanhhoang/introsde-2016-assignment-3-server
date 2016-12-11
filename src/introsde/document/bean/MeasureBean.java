package introsde.document.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "measure")
@XmlType(propOrder = {"mid", "dateRegistered", "measureType", "measureValue", "measureValueType"})
@XmlAccessorType(XmlAccessType.FIELD)
public class MeasureBean implements Serializable {

	private static final long serialVersionUID = 3114667130740699756L;

	@XmlElement(name = "mid")
	private Long mid;

	@XmlElement(name = "dateRegistered")
	private String dateRegistered;

	@XmlElement(name = "measureType")
	private String measureType;

	@XmlElement(name = "measureValue")
	private String measureValue;

	@XmlElement(name = "measureValueType")
	private String measureValueType; // string, integer, real

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getDateRegistered() {
		return dateRegistered;
	}

	public void setDateRegistered(String dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public String getMeasureType() {
		return measureType;
	}

	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}

	public String getMeasureValue() {
		return measureValue;
	}

	public void setMeasureValue(String measureValue) {
		this.measureValue = measureValue;
	}

	public String getMeasureValueType() {
		return measureValueType;
	}

	public void setMeasureValueType(String measureValueType) {
		this.measureValueType = measureValueType;
	}

	@Override
	public String toString() {
		return "MeasureBean [mid=" + mid + ", dateRegistered=" + dateRegistered + ", measureType=" + measureType
				+ ", measureValue=" + measureValue + ", measureValueType=" + measureValueType + "]";
	}

}
