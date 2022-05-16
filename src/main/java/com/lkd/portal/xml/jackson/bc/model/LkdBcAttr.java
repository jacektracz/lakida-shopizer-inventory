package com.lkd.portal.xml.jackson.bc.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

@JsonTypeName("attr")
public  class LkdBcAttr {
		
	@JacksonXmlElementWrapper(useWrapping = false, localName="detaleArr")
	@JsonProperty("detale")
	protected List<LkdBcDetails> details;

	public List<LkdBcDetails> getDetails() {
		if (details == null) {
			details = new ArrayList<LkdBcDetails>();
		}
		return this.details;
	}

}
