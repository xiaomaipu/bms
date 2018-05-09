package com.imxiaomai.bms.service;

import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.UsersBookCollect;
import com.imxiaomai.bms.util.ResMsg;

import java.util.List;

/**
 * 用户搜藏
 * Created by hyy on 2018/3/27.
 */
public interface UsersBookCollectService {

    /**
     * 添加用户搜藏
     * @param usersBookCollect
     * @return
     */
    ResMsg<String> saveUserBookCollect(UsersBookCollect usersBookCollect) throws Exception;

    /**
     * 修改用户搜藏(只提供修改状态)
     * @param param
     * @return
     * @throws Exception
     */
    ResMsg<String> updateByPrimaryKey(UsersBookCollect param) throws Exception;

    /**
     * 分页查询用户搜藏
     * @param usersBookCollect
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    ResMsg<PageInfo<UsersBookCollect>> selectPageInfo(UsersBookCollect usersBookCollect,Integer pageNum,Integer pageSize)throws Exception;

    /**
     * 查询用户搜藏列表
     * @param param
     * @return
     * @throws Exception
     */
    ResMsg<List<UsersBookCollect>> selectList(UsersBookCollect param) throws Exception;

    /**
     * 查询用户搜藏信息
     * @param param
     * @return
     * @throws Exception
     */
    ResMsg<UsersBookCollect> selectOne(UsersBookCollect param) throws Exception;
}
