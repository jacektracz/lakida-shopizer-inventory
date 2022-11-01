package com.shopizer.inventory.entity.in.cat.services;

import java.util.HashMap;
import java.util.Map;

import com.shopizer.inventory.entity.in.cat.model.CategoryRequestEntityData;

public class CategoryImportLocalStorage {

	private Map<String,CategoryRequestEntityData> categoriesMap = new HashMap<>();
	
	public void addCat( CategoryRequestEntityData item) {
		categoriesMap.putIfAbsent(item.getCode(), item);
	}
	
	public CategoryRequestEntityData getCat( String code) {
		return categoriesMap.getOrDefault(code, null);
	}
		
	public Map<String,CategoryRequestEntityData> getCategoriesMap(){
		return 	categoriesMap;
	}
}
