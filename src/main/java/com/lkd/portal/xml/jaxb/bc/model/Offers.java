package com.lkd.portal.xml.jaxb.bc.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "offer" })
@XmlRootElement(name = "offers")
public class Offers {

	protected List<Offers.Offer> offer;

	public List<Offers.Offer> getOffer() {
		if (offer == null) {
			offer = new ArrayList<Offers.Offer>();
		}
		return this.offer;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "id", "name", "category", "categoryPath", "erpcode", "erpean", "brutto",
			"bruttosuggest", "url", "images", "manufacturer", "description", "attr", "connected" })
	public static class Offer {

		@XmlElement(required = false)
		protected String id;
		
		@XmlElement(required = false)
		protected String name;
		
		@XmlElement(required = false)
		protected String category;
		
		@XmlElement(required = false)
		protected String categoryPath;
		
		@XmlElement(required = false)
		protected String erpcode;
		
		@XmlElement(required = false)
		protected String erpean;
		
		@XmlElement(required = false)
		protected String brutto;
		
		@XmlElement(required = false)
		protected String bruttosuggest;

		@XmlElement(required = false)
		protected String url;
		
		@XmlElement(required = false)
		protected Offers.Offer.Images images;
		
		@XmlElement(required = false)
		protected String manufacturer;
		
		@XmlElement(required = false)
		protected String description;
		
		@XmlElement(required = false)
		protected Offers.Offer.Attr attr;
		
		@XmlElement(required = false)
		protected Offers.Offer.Connected connected;

		public String getId() {
			return id;
		}

		public void setId(String value) {
			this.id = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String value) {
			this.name = value;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String value) {
			this.category = value;
		}

		public String getCategoryPath() {
			return categoryPath;
		}

		public void setCategoryPath(String value) {
			this.categoryPath = value;
		}

		public String getErpcode() {
			return erpcode;
		}

		public void setErpcode(String value) {
			this.erpcode = value;
		}

		public String getErpean() {
			return erpean;
		}

		public void setErpean(String value) {
			this.erpean = value;
		}

		public String getBrutto() {
			return brutto;
		}

		public void setBrutto(String value) {
			this.brutto = value;
		}

		
		public String getBruttosuggest() {
			return bruttosuggest;
		}

		
		public void setBruttosuggest(String value) {
			this.bruttosuggest = value;
		}

		
		public String getUrl() {
			return url;
		}

		
		public void setUrl(String value) {
			this.url = value;
		}

		
		public Offers.Offer.Images getImages() {
			return images;
		}

		
		public void setImages(Offers.Offer.Images value) {
			this.images = value;
		}

		public String getManufacturer() {
			return manufacturer;
		}

		public void setManufacturer(String value) {
			this.manufacturer = value;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String value) {
			this.description = value;
		}

		public Offers.Offer.Attr getAttr() {
			return attr;
		}

		public void setAttr(Offers.Offer.Attr value) {
			this.attr = value;
		}

		public Offers.Offer.Connected getConnected() {
			return connected;
		}

		public void setConnected(Offers.Offer.Connected value) {
			this.connected = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "detale" })
		public static class Attr {

			@XmlElement(required = false)
			protected List<Offers.Offer.Attr.Detale> detale;

			public List<Offers.Offer.Attr.Detale> getDetale() {
				if (detale == null) {
					detale = new ArrayList<Offers.Offer.Attr.Detale>();
				}
				return this.detale;
			}

			@XmlAccessorType(XmlAccessType.FIELD)
			@XmlType(name = "")
			public static class Detale {

				@XmlAttribute(name = "weight")
				protected String weight;

				@XmlAttribute(name = "color")
				protected String color;
				@XmlAttribute(name = "rozmiar")
				protected String rozmiar;
				@XmlAttribute(name = "onstock")
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

		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "id" })
		public static class Connected {

			@XmlElement(required = false)
			protected String id;

			/**
			 * Gets the value of the id property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getId() {
				return id;
			}

			/**
			 * Sets the value of the id property.
			 * 
			 * @param value allowed object is {@link String }
			 * 
			 */
			public void setId(String value) {
				this.id = value;
			}

		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "image" })
		public static class Images {

			@XmlElement(required = false)
			protected List<String> image;

			public List<String> getImage() {
				if (image == null) {
					image = new ArrayList<String>();
				}
				return this.image;
			}

		}

	}

}
