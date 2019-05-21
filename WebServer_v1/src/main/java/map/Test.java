package map;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Haosener
 *
 */
public class Test {

	public static void main(String[] args) {
		/**
		 * 统计当前字符串中每个字符出现的次数
		 */
		String str = "thinking in java! i love java!";
		/*
		 * key:出现的字符
		 * value：出现的次数
		 */
		Map<Character,Integer>map = new HashMap<>();
		for(int i = 0;i<str.length();i++) {
			char c = str.charAt(i);
			if(map.containsKey(c)) {
				int num = map.get(c);
				num++;
				map.put(c, num);
			}else {
				map.put(c, 1);
			}
		}
		System.out.println(map);
	}
}


	/*		    //定义一个char类型数组，并把字符串导入这个数组
		    char[] arr =str.toCharArray();
		    //创建一个引用对象，HashMap集合
		    HashMap<Character,Integer> hm = new HashMap<>();
		    //使用增强for循环把数组内容添加到集合，
		    for (char c : arr) {
		        //再添加的时候，判断是否重复，并对出现次数进行统计
		        hm.put(c, hm.containsKey(c) ? hm.get(c) + 1 : 1);
		    }
		    //输出集合
		    System.out.println(hm);
		    //对这个集合进行遍历
		    StringBuilder sb = new StringBuilder();
		    for (Map.Entry<Character, Integer> en: hm.entrySet()) {
		        char key = en.getKey();
		        int value = en.getValue();
		        sb.append(key).append("出现").append(value).append("次").append("\n");
		    }
		    System.out.println(sb.substring(0, sb.length()-1));
		*/