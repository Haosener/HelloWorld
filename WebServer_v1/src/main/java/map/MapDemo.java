package map;

import java.util.HashMap;
import java.util.Map;

/**
 * java.util.Map���ұ�
 * 
 * Map���ֵ�������һ���������еı���������г�Ϊkey���г�Ϊvalue
 * Map���Ǹ���key����ȡ��Ӧ��value�����Կ��Խ���ѯ������Ϊkey
 * ������������Ϊvalue���棬�Ա��Ժ��ȡ
 * 
 * ����ʵ���ࣺjava.util.HashMap ɢ�б� ��ϣ�� d
 * 			java.util.TreeMap������ʵ�ֵ�Map
 * ɢ�б��ǵ����ѯ�ٶ��������ݽṹ
 * @author Haosener
 *
 */
public class MapDemo {
	public static void main(String[] args) {
		Map<String,Integer> map = new HashMap<>();
		/*
		 * V put(K k,V v)
		 * ��������key-value�Ա��浽Map��
		 * Map��һ��Ҫ��key�������ظ���equals�Ƚϣ�
		 * ��ʹ��Map���Ѿ����ڵ�key���һ����Value
		 * �����滻Value��������ôput�����ķ���ֵ����
		 * ���滻��Value����������key��ǰmap�в�����
		 * �򷵻�ֵΪnull.
		 */
		map.put("����", 99);
		map.put("��ѧ", 98);
		map.put("Ӣ��", 97);
		map.put("��ѧ", 96);
		map.put("��ʷ", 95);
		//�滻Value
		Integer num = map.put("��ѧ", 60);
		System.out.println(num);
		System.out.println(map);
		/*
		 * V get (Object key)
		 * ���ݸ�����key��ȡ��Ӧ��value
		 * ��������key��Map�в����ڣ��򷵻�ֵΪnull
		 */
		num = map.get("��ѧ");
		System.out.println("��ѧ:"+num);
		
		num = map.get("����");
		System.out.println(num);
		
		int size = map.size();
		System.out.println("size:"+size);
		
		boolean isEmpty = map.isEmpty();
		System.out.println("isEmpty:"+isEmpty);
		
		/*
		 * V remove(Object key)
		 * ɾ������key����Ӧ�������ֵ�ԣ�����ֵΪ��kep��Ӧ��value
		 */
		num = map.remove("Ӣ��");
		System.out.println(map);
		System.out.println(num);
		
		boolean ck = map.containsKey("��ѧ");
		System.out.println("����key:"+ck);
		
		boolean cv = map.containsValue(66);
		System.out.println("����valu��"+cv);
	}

}
