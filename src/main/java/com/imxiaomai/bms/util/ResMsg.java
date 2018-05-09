/**   
* @Title: ResMsg.java 
* @Package com.imxiaomai.mall.scan.code.domain.aid 
* @Description: TODO(用一句话描述该文件做什么) 
* @author guoqingcun   
* @date 2017年1月16日 下午3:12:42 
* @version V1.0   
*/
package com.imxiaomai.bms.util;

import java.io.Serializable;

/**
 * @author lvsheng
 * @Description:
 * @date 2018/1/23 13:52
 */
public class ResMsg<T> implements Serializable {

	private static final long serialVersionUID = 5672931283338623527L;

	private Integer code;//200:成功, 400:参数有问题,500服务器异常
	private String des;//对问题的描述
	private T obj;//数据

	public ResMsg(Integer code, String des, T obj) {
		this.code = code;
		this.des = des;
		this.obj = obj;
	}

	public ResMsg() {
	}

	/**
	* @return code 
	*/
	public Integer getCode() {
		return code;
	}
	/** 
	* @param code 要设置的 code 
	*/
	public void setCode(Integer code) {
		this.code = code;
	}
	/** 
	* @return des 
	*/
	public String getDes() {
		return des;
	}
	/** 
	* @param des 要设置的 des 
	*/
	public void setDes(String des) {
		this.des = des;
	}
	/** 
	* @return obj 
	*/
	public T getObj() {
		return obj;
	}
	/** 
	* @param obj 要设置的 obj 
	*/
	public void setObj(T obj) {
		this.obj = obj;
	}
	
	
	
}
