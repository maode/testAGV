package com.his.net.agv.nettybak.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**  
 * @Package com.his.net.agv.nettybak.entity
 * @Description: 导航路线
 * @author ZhengMaoDe   
 * @date 2018年8月15日 下午2:15:45 
 */
@Getter
@Setter
public class NavRoute {

	private Coord upPoint;
	
	private Coord downPoint;
	
	private List<Coord> toUpRoute;
	
	private List<Coord> toDownRoute;
}
