package com.imxiaomai.bms.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imxiaomai.bms.entity.User;
import com.imxiaomai.bms.entity.UsersBookCollect;
import com.imxiaomai.bms.mapper.UsersBookCollectMapper;
import com.imxiaomai.bms.service.UsersBookCollectService;
import com.imxiaomai.bms.util.BaseAction;
import com.imxiaomai.bms.util.CommonConsts;
import com.imxiaomai.bms.util.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户搜藏
 * Created by hyy on 2018/3/27.
 */
@Service
public class UsersBookCollectServiceImpl implements UsersBookCollectService {

    private static Logger logger = LoggerFactory.getLogger(UsersBookCollectServiceImpl.class);

    @Resource
    private UsersBookCollectMapper usersBookCollectMapper;

    @Override
    @Transactional
    public ResMsg<String> saveUserBookCollect(UsersBookCollect usersBookCollect) throws Exception {
        logger.info("添加用户搜藏,参数 "+ JSON.toJSONString(usersBookCollect));
        ResMsg<String> resMsg = new ResMsg<String>();
        if(!ObjectUtils.isEmpty(usersBookCollect)){
            Integer userId = usersBookCollect.getUserId();
            Integer bookId = usersBookCollect.getBookId();
            Integer type = usersBookCollect.getType();
            if(ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(bookId) || ObjectUtils.isEmpty(type)){
                resMsg.setCode(CommonConsts.PARAM_ERROR);
                resMsg.setDes("参数不全,用户id,书籍id,操作类型必须传递");
                return resMsg;
            }
            UsersBookCollect queryCollect = new UsersBookCollect();
            queryCollect.setUserId(userId);
            queryCollect.setBookId(bookId);
            queryCollect.setYn(CommonConsts.STAT_OK);
            UsersBookCollect resCollect = usersBookCollectMapper.selectOne(queryCollect);
            if(ObjectUtils.isEmpty(resCollect)){
                //添加
                usersBookCollect.setYn(CommonConsts.STAT_OK);
                usersBookCollect.setCreated(new Date());
                usersBookCollectMapper.insertSelective(usersBookCollect);
            }else{
                //修改状态,书籍id和用户id不修改
                UsersBookCollect updateCollect = new UsersBookCollect();
                updateCollect.setId(resCollect.getId());
                updateCollect.setType(type);
                updateCollect.setUpdated(new Date());
                usersBookCollectMapper.updateByPrimaryKeySelective(updateCollect);
            }
            resMsg.setCode(CommonConsts.SUCCESS);
            resMsg.setDes("操作成功");
            return resMsg;
        }
        return null;
    }

    @Override
    @Transactional
    public ResMsg<String> updateByPrimaryKey(UsersBookCollect param) throws Exception {
        logger.info("修改用户搜藏信息,参数 "+JSON.toJSONString(param));
        ResMsg<String> resMsg = new ResMsg<String>();
        if(!ObjectUtils.isEmpty(param)){
            Integer id = param.getId();
            UsersBookCollect resInfo = usersBookCollectMapper.selectByPrimaryKey(id);
            if(ObjectUtils.isEmpty(resInfo)){
                resMsg.setCode(CommonConsts.PARAM_ERROR);
                resMsg.setDes("id:"+id+" 用户搜藏信息不存在");
                return resMsg;
            }
            UsersBookCollect updateCollect = new UsersBookCollect();
            updateCollect.setId(id);
            updateCollect.setType(param.getType());
            updateCollect.setUpdated(new Date());
            usersBookCollectMapper.updateByPrimaryKeySelective(updateCollect);

            resMsg.setCode(CommonConsts.SUCCESS);
            resMsg.setDes("操作成功");
            return resMsg;
        }
        return null;
    }

    @Override
    public ResMsg<PageInfo<UsersBookCollect>> selectPageInfo(UsersBookCollect usersBookCollect, Integer pageNum, Integer pageSize) throws Exception {
        logger.info("分页查询,参数 "+JSON.toJSONString(usersBookCollect)+" pageNum:"+pageNum+" pageSize:"+pageSize);
        ResMsg<PageInfo<UsersBookCollect>> resMsg = new ResMsg<PageInfo<UsersBookCollect>>();
        if(usersBookCollect == null){
            usersBookCollect = new UsersBookCollect();
        }
        usersBookCollect.setYn(CommonConsts.STAT_OK);
        usersBookCollect.setType(1);
        if(pageSize == null || pageSize < 1){
            pageSize = CommonConsts.DEFAULT_PAGE_SIZE;
        }
        if(pageNum == null || pageNum < 1){
            pageNum = 1;
        }
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("created desc");
        List<UsersBookCollect> list = usersBookCollectMapper.select(usersBookCollect);

        PageInfo<UsersBookCollect> pageInfo = new PageInfo<UsersBookCollect>(list);
        resMsg.setCode(CommonConsts.SUCCESS);
        resMsg.setObj(pageInfo);
        return resMsg;
    }

    @Override
    public ResMsg<List<UsersBookCollect>> selectList(UsersBookCollect param) throws Exception {
        logger.info("查询用户搜藏列表,参数 "+JSON.toJSONString(param));
        ResMsg<List<UsersBookCollect>> resMsg = new ResMsg<List<UsersBookCollect>>();
        if(param == null){
            param = new UsersBookCollect();
        }
        param.setYn(CommonConsts.STAT_OK);
        List<UsersBookCollect> list = usersBookCollectMapper.select(param);
        resMsg.setCode(CommonConsts.SUCCESS);
        resMsg.setObj(list);
        return resMsg;
    }

    @Override
    public ResMsg<UsersBookCollect> selectOne(UsersBookCollect param) throws Exception {
        logger.info("查询用户搜藏信息,参数 "+JSON.toJSONString(param));
        ResMsg<UsersBookCollect> resMsg =new ResMsg<UsersBookCollect>();
        if(!ObjectUtils.isEmpty(param)){
            param.setYn(CommonConsts.STAT_OK);
            UsersBookCollect entry = usersBookCollectMapper.selectOne(param);
            resMsg.setCode(CommonConsts.SUCCESS);
            resMsg.setDes("操作成功");
            resMsg.setObj(entry);
            return resMsg;
        }
        return null;
    }
}
