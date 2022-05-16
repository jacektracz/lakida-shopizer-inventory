package com.lkd.portal.xml.jackson.bc.model;



import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonTypeName("detaleClazz")
public  class LkdBcDetails {

	@JacksonXmlProperty( isAttribute = true,localName = "weight")
	protected String weight;

	@JacksonXmlProperty( isAttribute = true,localName = "color")
	protected String color;
	
	@JacksonXmlProperty( isAttribute = true,localName = "rozmiar")
	protected String rozmiar;
	
	@JacksonXmlProperty( isAttribute = true,localName = "onstock")
	protected String onstock;

	/**
	 * Gets the value of the color property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Sets the value of the color property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setColor(String value) {
		this.color = value;
	}

	/**
	 * Gets the value of the rozmiar property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRozmiar() {
		return rozmiar;
	}

	/**
	 * Sets the value of the rozmiar property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setRozmiar(String value) {
		this.rozmiar = value;
	}

	/**
	 * Gets the value of the onstock property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOnstock() {
		return onstock;
	}

	/**
	 * Sets the value of the onstock property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setOnstock(String value) {
		this.onstock = value;
	}

	public String getWeight() {
		return weight;
	}

	/**
	 * Sets the value of the rozmiar property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setWeight(String value) {
		this.weight = value;
	}

}
