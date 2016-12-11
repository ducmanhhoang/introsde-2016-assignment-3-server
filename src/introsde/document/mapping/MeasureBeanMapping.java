package introsde.document.mapping;

import java.util.List;

import introsde.document.bean.MeasureBean;
import introsde.document.model.HealthMeasureHistory;
import introsde.document.model.LifeStatus;
import introsde.document.model.MeasureDefinition;
import introsde.document.model.Person;

public class MeasureBeanMapping {

	public static LifeStatus mapToLifeStatus(MeasureBean mb, Long personId) {
		LifeStatus ls = new LifeStatus();
		ls.setIdMeasure(mb.getMid().intValue());
		List<MeasureDefinition> mds = MeasureDefinition.getAll();
		for (MeasureDefinition md : mds) {
			if (md.getMeasureName().equalsIgnoreCase(mb.getMeasureType())) {
				ls.setMeasureDefinition(md);
			}
		}
		ls.setPerson(Person.getPersonById(personId.intValue()));
		ls.setValue(mb.getMeasureValue());
		return ls;
	}
	
	public static MeasureBean mapFromLifeStatus(LifeStatus ls) {
		MeasureBean mb = new MeasureBean();
		mb.setDateRegistered("");
		mb.setMeasureType(ls.getMeasureDefinition().getMeasureName());
		mb.setMeasureValue(ls.getValue());
		mb.setMeasureValueType(ls.getMeasureDefinition().getMeasureType());
		mb.setMid(Long.valueOf(ls.getIdMeasure()));
		return mb;
	}
//	
//	public static HealthMeasureHistory mapToHealthMeasureHistory(MeasureBean measure) {
//
//		DozerBeanMapper mapper = new DozerBeanMapper();
//		mapper.setMappingFiles(myMappingFiles);
//		return (HealthMeasureHistory) mapper.map(measure, HealthMeasureHistory.class);
//	}
//	
	public static MeasureBean mapFromHealthMeasureHistory(HealthMeasureHistory hmh) {
		MeasureBean mb = new MeasureBean();
		mb.setDateRegistered(hmh.getTimestamp());
		mb.setMeasureType(hmh.getMeasureDefinition().getMeasureName());
		mb.setMeasureValue(hmh.getValue());
		mb.setMeasureValueType(hmh.getMeasureDefinition().getMeasureType());
		mb.setMid(Long.valueOf(hmh.getIdMeasureHistory()));
		return mb;
	}

}
