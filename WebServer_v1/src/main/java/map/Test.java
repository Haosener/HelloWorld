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
		 * ͳ�Ƶ�ǰ�ַ�����ÿ���ַ����ֵĴ���
		 */
		String str = "thinking in java! i love java!";
		/*
		 * key:���ֵ��ַ�
		 * value�����ֵĴ���
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


	/*		    //����һ��char�������飬�����ַ��������������
		    char[] arr =str.toCharArray();
		    //����һ�����ö���HashMap����
		    HashMap<Character,Integer> hm = new HashMap<>();
		    //ʹ����ǿforѭ��������������ӵ����ϣ�
		    for (char c : arr) {
		        //����ӵ�ʱ���ж��Ƿ��ظ������Գ��ִ�������ͳ��
		        hm.put(c, hm.containsKey(c) ? hm.get(c) + 1 : 1);
		    }
		    //�������
		    System.out.println(hm);
		    //��������Ͻ��б���
		    StringBuilder sb = new StringBuilder();
		    for (Map.Entry<Character, Integer> en: hm.entrySet()) {
		        char key = en.getKey();
		        int value = en.getValue();
		        sb.append(key).append("����").append(value).append("��").append("\n");
		    }
		    System.out.println(sb.substring(0, sb.length()-1));
		*/