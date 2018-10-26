package com.his.net.agv.nettybak.service;

import com.his.net.agv.nettybak.entity.AgvEntity;
import com.his.net.agv.nettybak.entity.NavRoute;

/**  
 * @Package com.his.net.agv.nettybak.service
 * @Description: 调用agv执行任务的接口
 * @author ZhengMaoDe   
 * @date 2018年8月15日 下午3:00:21 
 */
public interface AgvNavigationService<T> {
	/**
	 * 调用agv按导航路线执行任务
	 * @param route 路线
	 * @param service 回调service
	 * @param arg 回调service需要的参数
	 * @param agv 要调用的agv
	 */
	public void navigation(NavRoute route,AgvCallBackService<T> service,T arg,AgvEntity agv);
	
	
	/**
	 * 调用agv按导航路线执行up到down的任务
	 * @param route 路线
	 * @param service 回调service
	 * @param arg 回调service需要的参数
	 * @param agv 要调用的agv
	 */
	public void navigationUpToDown(NavRoute route,AgvCallBackService<T> service,T arg,AgvEntity agv);
}
