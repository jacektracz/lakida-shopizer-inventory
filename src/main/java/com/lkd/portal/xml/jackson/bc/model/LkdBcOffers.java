package com.lkd.portal.xml.jackson.bc.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("offers")
public class LkdBcOffers {

	protected List<LkdBcOffer> offer;

	public List<LkdBcOffer> getOffer() {
		if (offer == null) {
			offer = new ArrayList<LkdBcOffer>();
		}
		return this.offer;
	}


}
