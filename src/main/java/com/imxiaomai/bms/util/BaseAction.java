/**   
* @Title: BaseAction.java 
* @Package com.imxiaomai.mall.scan.code.web 
* @Description: TODO(用一句话描述该文件做什么) 
* @author guoqingcun   
* @date 2017年1月16日 下午3:15:27 
* @version V1.0   
*/
package com.imxiaomai.bms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: BaseAction
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lvsheng
 * @date 2018年1月16日 下午3:15:27
 * 
 */
public class BaseAction {

	private final Logger logger = LoggerFactory.getLogger(BaseAction.class);

	public final Integer SUCCESS = 200;// 成功

	public final Integer PARAM_ERROR = 400;// 参数有问题

	public final Integer DATA_NOT_FOUND = 404;// 数据不存在
	
	public final Integer DATA_NOT_ENOUGH = 405;// 数据不足

	public final Integer SERVER_ERROR = 500;// 服务器异常

	public final Integer SERVER_FAIL = 506;//业务处理失败

	public static final Integer NO_AUTHENTICATION = 406;//未认证

	public final String SESSION_USER = "SESSION_USER";

	public  final String SAVE_SUCCESS = "保存成功";
	public  final String SAVE_FAIL = "保存失败";
	public  final String MODIFY_SUCCESS = "修改成功";
	public  final String MODIFY_FAIL = "修改失败";
	public  final String DELETE_SUCCESS = "删除成功";
	public  final String DELETE_FAIL = "删除失败";
	public  final String OP_SUCCESS = "操作成功";
	public  final String OP_FAIL = "操作失败";

	@ModelAttribute
	public void setContextPath(ModelMap modelMap, HttpServletRequest request){
		modelMap.put("globalPath",request.getContextPath());
	}

	protected ResMsg<String> error(String msg, Exception e) {
		ResMsg resMsg = new ResMsg();
		resMsg.setCode(SERVER_ERROR);
		resMsg.setDes(msg);
		logger.error(msg, e);
		return resMsg;
	}

	protected <T> ResMsg<T> success(T t) {
		ResMsg<T> resMsg = new ResMsg();
		resMsg.setCode(SUCCESS);
		resMsg.setDes("SUCCESS");
		resMsg.setObj(t);
		return resMsg;
	}

	protected <T> ResMsg<T> fail(Integer code, String msg, T t) {
		ResMsg<T> resMsg = new ResMsg();
		resMsg.setCode(code);
		resMsg.setDes(msg);
		resMsg.setObj(t);
		logger.info(msg);
		return resMsg;
	}
}
