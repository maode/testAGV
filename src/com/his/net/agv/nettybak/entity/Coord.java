package com.his.net.agv.nettybak.entity;

import lombok.Getter;
import lombok.Setter;

/**  
 * @Package com.his.net.agv.nettybak.entity
 * @Description: agv坐标实体类
 * @author ZhengMaoDe   
 * @date 2018年8月14日 下午5:40:23 
 */
@Getter
@Setter
public class Coord {

	private Long id;
	
	private Integer q600x;
	
	private Integer q600y;
	
	private Integer pgvTag;
}
