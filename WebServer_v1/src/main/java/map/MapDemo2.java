package map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Map的便利操作
 * Map支持三种便利方式：
 * 1.便利所有的key
 * 2.便利每一组键值对
 * 3.便利所有的value（相对不常用）
 * 
 * 
 * 
 * @author Haosener
 *
 */
public class MapDemo2 {

	public static void main(String[] args) {
		Map<String,Integer> map = new HashMap<>();
		
		map.put("语文", 99);
		map.put("数学", 98);
		map.put("英语", 97);
		map.put("化学", 96);
		map.put("历史", 95);
		System.out.println(map);
		/*
		 * 遍历所有的key
		 * SetkeySet()
		 * 将当前Map中的key以一个Set集合形式
		 * 返回。
		 */
		Set<String> keySet = map.keySet();
		for(String key:keySet) {
			System.out.println("key:"+key);
		}
		
		/*
		 * 遍历每一组键值对
		 * Set<Entry> entrySet()
		 * 将当前Map中所有的键值对以若干的Entry
		 * 实例形式保存在一个Set集合后返回
		 * 
		 * java.util.Map.Eentry
		 * Entry每个实例用于表示Map中的一组键值对
		 */
		Set<Entry<String,Integer>> entrySet = map.entrySet();
		
		for(Entry<String,Integer> e:entrySet) {
			String key = e.getKey();
			Integer value = e.getValue();
			System.out.println(key+":"+value);
		}
		Collection <Integer>values = map.values();
		for(Integer value : values) {
			System.out.println("value:"+value);
		}
		
 	} 

}
