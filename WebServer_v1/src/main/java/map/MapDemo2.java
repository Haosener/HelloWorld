package map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Map�ı�������
 * Map֧�����ֱ�����ʽ��
 * 1.�������е�key
 * 2.����ÿһ���ֵ��
 * 3.�������е�value����Բ����ã�
 * 
 * 
 * 
 * @author Haosener
 *
 */
public class MapDemo2 {

	public static void main(String[] args) {
		Map<String,Integer> map = new HashMap<>();
		
		map.put("����", 99);
		map.put("��ѧ", 98);
		map.put("Ӣ��", 97);
		map.put("��ѧ", 96);
		map.put("��ʷ", 95);
		System.out.println(map);
		/*
		 * �������е�key
		 * SetkeySet()
		 * ����ǰMap�е�key��һ��Set������ʽ
		 * ���ء�
		 */
		Set<String> keySet = map.keySet();
		for(String key:keySet) {
			System.out.println("key:"+key);
		}
		
		/*
		 * ����ÿһ���ֵ��
		 * Set<Entry> entrySet()
		 * ����ǰMap�����еļ�ֵ�������ɵ�Entry
		 * ʵ����ʽ������һ��Set���Ϻ󷵻�
		 * 
		 * java.util.Map.Eentry
		 * Entryÿ��ʵ�����ڱ�ʾMap�е�һ���ֵ��
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
