package com.his.net.agv.nettybak.util;

import org.apache.commons.lang3.StringUtils;

import com.his.net.agv.nettybak.netty.AgvConstants;

/**  
 * @Title: Q600ConditionUtils.java
 * @Package com.his.net.agv.nettybak.util
 * @Description: Q600指令条件工具类
 * @author ZhengMaoDe   
 * @date 2018年7月23日 上午11:55:04 
 */
public class Q600ConditionUtils {
	/**
	 * 判断Q600返回的坐标和当前指令条件坐标是否相同
	 * 
	 * @param cmdCondition 格式如："x,y"
	 * @param q600Result	格式如："P 5 x y 21011 21"
	 * @return
	 */
	public static boolean q600PositionConditionEquals(String cmdCondition, String q600Result) {
		boolean isEquals = false;
		if (StringUtils.isNotBlank(q600Result)) {
			String[] resultArray = q600Result.split(" ");
			if (resultArray.length != 7) {
				return false;
			}

			String[] conditionArray = cmdCondition.split(",");
			if (conditionArray.length != 2) {
				return false;
			}

			try {
				int rx = Integer.parseInt(resultArray[2]);
				int ry = Integer.parseInt(resultArray[3]);
				int cx = (int) (Float.parseFloat(conditionArray[0]) * 1000);
				int cy = (int) (Float.parseFloat(conditionArray[1]) * 1000);
				if (rx >= cx - AgvConstants.Q600_DEFAULT_ERROR && rx <= cx + AgvConstants.Q600_DEFAULT_ERROR
						&& ry >= cy - AgvConstants.Q600_DEFAULT_ERROR && ry <= cy + AgvConstants.Q600_DEFAULT_ERROR) {
					isEquals = true;
				}
			} catch (Exception e) {
				return false;
			}

		}

		return isEquals;
	}
}
