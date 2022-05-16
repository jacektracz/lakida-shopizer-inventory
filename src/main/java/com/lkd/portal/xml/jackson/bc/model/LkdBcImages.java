package com.lkd.portal.xml.jackson.bc.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("images")
public  class LkdBcImages {

	@JsonProperty(required = false)
	protected List<String> image;

	public List<String> getImage() {
		if (image == null) {
			image = new ArrayList<String>();
		}
		return this.image;
	}

}
