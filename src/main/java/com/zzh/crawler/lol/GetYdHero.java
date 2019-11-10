package com.zzh.crawler.lol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 得到云顶所有英雄的数据
 *
 */
public class GetYdHero {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		getYdHeros();
	}
	@SuppressWarnings("unchecked")
	public static HashSet<String> getYdHeros() throws IOException{
		HashSet<String> heroSet = new HashSet<String>();
		File allYdHeroJson = new File("D:\\Users\\zzh\\eclipse-workspace\\crawler\\resources/allYdHero.json");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(allYdHeroJson));
		String line = null;
		StringBuilder result = new StringBuilder();
		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}
		HashMap<String, Object> herosMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		herosMap = (HashMap<String, Object>) mapper.readValue(result.toString(),Map.class);
		
		
		for (Entry<String, Object> e : herosMap.entrySet()) {
			StringBuilder sb = new StringBuilder();
			Map<String, Object> heroData = (Map<String, Object>) e.getValue();
			for (Entry<String, Object> a : heroData.entrySet()) {
				if("hero_tittle".equals(a.getKey())) {
					sb.append(a.getValue());
					heroSet.add(sb.toString().trim());
				}
			}
		}
		return heroSet;
	}

}
