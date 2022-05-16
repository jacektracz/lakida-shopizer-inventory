package com.lkd.portal.xml.jackson.bc.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("attr")
public  class LkdBcAttr {

	@JsonProperty(required = false)
	protected List<LkdBcDetails> detale;

	public List<LkdBcDetails> getDetale() {
		if (detale == null) {
			detale = new ArrayList<LkdBcDetails>();
		}
		return this.detale;
	}

}
