package com.zzh.crawler.lol;

import java.util.HashSet;

/**
 * 比较云顶之奕英雄与lol全英雄
 * 
 * @author zzh
 *
 */
public class CompareHeros {

	public static void main(String[] args) throws Exception {
		compare();
	}

	private static void compare() throws Exception {
		// 得到全英雄
		HashSet<String> allHeros = GetAllHero.getAllHeros();
		// 得到云顶英雄
		HashSet<String> ydHeros = GetYdHero.getYdHeros();
		int j = 0;//计算未出场的英雄
		for (String allHero : allHeros) {
			int i = 0;
			for (String ydHero : ydHeros) {
				if (allHero.contains(ydHero)) {
//				    System.out.println(allHero); 云顶之奕出场的英雄
					break;
					}
					++i;
				}
			if(i==ydHeros.size()) {
				System.out.println(allHero+ ++j);
			}
			}
		}
	}

