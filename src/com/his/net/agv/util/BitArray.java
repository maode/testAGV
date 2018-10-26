package com.his.net.agv.util;

import java.util.Iterator;
import java.util.function.BiConsumer;


/**  
 * @Title: BitArray.java
 * @Package com.his.comm
 * @Description: 二进制数组工具类
 * @author ZhengMaoDe   
 * @date 2018年4月22日 下午8:51:17 
 */
public class BitArray implements Iterable<Boolean> {
	// 表示1<<n的值，提高效率，不用每次计算
	private final byte[] MASK = new byte[] { 1, 2, 4, 8, 16, 32, 64, (byte) 128 };
	byte[] bits;
	int max = 0;

	/**
	 * 构造一个Bit数组
	 * 
	 * @param max
	 *            最大位数
	 */
	public BitArray(int max) {
		this.max = max;
		int len = max / 8 + 1;

		bits = new byte[len];
	}

	/**
	 * 设置第N位的值
	 * 
	 * @param index
	 *            Bit索引
	 * @param value
	 *            值
	 */
	public void set(int index, boolean value) {
		int i = index / 8;
		int move = index % 8;

		bits[i] = (byte) (bits[i] | MASK[move]);
	}

	/**
	 * 取得第N位的值
	 * 
	 * @param index
	 *            Bit索引
	 * @return
	 */
	public boolean get(int index) {
		int i = index / 8;
		int move = index % 8;

		return (bits[i] & MASK[move]) == MASK[move];
	}

	/**
	 * 显示所有位
	 */
	public void show() {
		for (int i = 0; i < bits.length; i++) {
			byte b = bits[i];
			for (int bitIndex = 0; bitIndex < 8; bitIndex++) {
				System.out.print(((b >> bitIndex) & 1) + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 提供遍历接口
	 */
	public Iterator<Boolean> iterator() {
		return new Iterator<Boolean>() {
			private int i = 0;

			public boolean hasNext() {
				return i <= max;
			}

			public Boolean next() {
				return get(i++);
			}

		};
	}

	/**
	 * 遍历，偷懒用了JAVA8的新接口
	 * 
	 * @param fun
	 */
	public void forEach(BiConsumer<Integer, Boolean> fun) {
		int total = 0;
		for (int i = 0; i < bits.length; i++) {
			byte b = bits[i];
			for (int bitIndex = 0; bitIndex < 8 && total <= max; bitIndex++, total++) {
				fun.accept(total, ((b >> bitIndex) & 1) == 1);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	public static void main( String[] args ) throws Exception
	{
	    BitArray bits = new BitArray(18);//18是长度？
	    bits.set(0, true);
	    bits.set(1,true);
	    bits.set(18,true);
	    System.out.println("position 1 : " + bits.get(1));
	    System.out.println("position 3 : " + bits.get(3));
	    System.out.println("position 18 : " + bits.get(18));
	    System.out.println("--------------------------");
	    
	    //遍历方式 一
	    int i = 0;
	    for(Boolean result : bits)
	        System.out.println(i++ + " : " + result);
	    
	    System.out.println("--------------------------");
	    
	    //遍历方式二
	    BiConsumer<Integer,Boolean> fun = (index, value)->{
	        System.out.println(index + " : " + value);
	    };        
	    bits.forEach(fun);
	}
	
	
	
}