package map;

import java.util.HashMap;
import java.util.Map;

/**
 * java.util.Map查找表
 * 
 * Map体现的样子是一个多行两列的表格，其中左列称为key右列称为value
 * Map总是根据key来获取对应的value，所以可以将查询条件作为key
 * 将查找内容作为value保存，以便以后获取
 * 
 * 常用实现类：java.util.HashMap 散列表 哈希表 d
 * 			java.util.TreeMap二叉树实现的Map
 * 散列标是当今查询速度最快的数据结构
 * @author Haosener
 *
 */
public class MapDemo {
	public static void main(String[] args) {
		Map<String,Integer> map = new HashMap<>();
		/*
		 * V put(K k,V v)
		 * 将给定的key-value对保存到Map中
		 * Map有一个要求：key不允许重复（equals比较）
		 * 若使用Map中已经存在的key存放一个人Value
		 * 则是替换Value操作，那么put方法的返回值就是
		 * 被替换的Value，若给定的key当前map中不存在
		 * 则返回值为null.
		 */
		map.put("语文", 99);
		map.put("数学", 98);
		map.put("英语", 97);
		map.put("化学", 96);
		map.put("历史", 95);
		//替换Value
		Integer num = map.put("数学", 60);
		System.out.println(num);
		System.out.println(map);
		/*
		 * V get (Object key)
		 * 根据给定的key获取对应的value
		 * 若给定的key在Map中不存在，则返回值为null
		 */
		num = map.get("化学");
		System.out.println("化学:"+num);
		
		num = map.get("体育");
		System.out.println(num);
		
		int size = map.size();
		System.out.println("size:"+size);
		
		boolean isEmpty = map.isEmpty();
		System.out.println("isEmpty:"+isEmpty);
		
		/*
		 * V remove(Object key)
		 * 删除给定key所对应的这组键值对，返回值为该kep对应的value
		 */
		num = map.remove("英语");
		System.out.println(map);
		System.out.println(num);
		
		boolean ck = map.containsKey("数学");
		System.out.println("包含key:"+ck);
		
		boolean cv = map.containsValue(66);
		System.out.println("包含valu："+cv);
	}

}
