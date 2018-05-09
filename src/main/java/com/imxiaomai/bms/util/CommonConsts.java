package com.imxiaomai.bms.util;

import java.io.Serializable;

/**
 * Created by hyy on 2018/1/24.
 */
public class CommonConsts implements Serializable{

    private static final long serialVersionUID = 8673555429511086920L;

    /**
     * 默认每页显示记录数 10 条
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;


    //----------状态码------------
    public static final Integer SUCCESS = 200;// 成功

    public static final Integer PARAM_ERROR = 400;// 参数有问题

    public static final Integer DATA_NOT_FOUND = 404;// 数据不存在

    public static final Integer DATA_NOT_ENOUGH = 405;// 数据不足

    public static final Integer SERVER_ERROR = 500;// 服务器异常

    public static final Integer SERVER_FAIL = 506;//业务处理失败

    /**
     * 1 有效
     */
    public static final Integer STAT_OK = 1;

    /**
     * 0 无效
     */
    public static final Integer STAT_NO = 0;


}
