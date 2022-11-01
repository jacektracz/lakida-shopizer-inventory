package com.shopizer.inventory.entity.in.product.services;

public class ProductImportSettings {
	/**
	 * where to find csv
	 */
	public static String getImportFileBaseDir() {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = sF + "C://lkd//ht//apps_java8_in_action//app//src//";
			sF = sF + "shopizer-inventory-csv//src//main//resources//";
			sF = sF + "data-import//product//product-json//";
			sF = sF + "";
			sF = sF + "";
		}
		return sF;
	}

	public static String getDebugJsonFileName(String idx) {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = getImportFileBaseDir() + "product-loader-" + idx + ".json";
		}
		return sF;
	}

	public static String getImportJsonFileName(String idx) {
		int ii = 0;
		String sF = "";
		if (ii == 0) {
			sF = getImportFileBaseDir() + "product-loader-" + idx + ".json";
		}
		return sF;
	}
}
