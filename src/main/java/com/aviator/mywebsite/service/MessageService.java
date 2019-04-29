package com.aviator.mywebsite.service;

import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.cond.MessageCond;
import com.aviator.mywebsite.entity.dto.req.MessageReq;
import com.aviator.mywebsite.entity.dto.req.PageReq;
import com.aviator.mywebsite.entity.dto.resp.MessageResp;
import com.aviator.mywebsite.entity.dto.resp.UserInfoResp;
import com.aviator.mywebsite.entity.po.Message;
import com.aviator.mywebsite.entity.po.Page;
import com.aviator.mywebsite.entity.po.User;
import com.aviator.mywebsite.entity.po.UserInfo;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.exception.ServiceException;
import com.aviator.mywebsite.util.ResultUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @ClassName MessageService
 * @Author aviator_ls
 * @Date 2019/4/28 18:56
 */
public class MessageService extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    public Result insertMessage(long authorId, MessageReq messageReq) {
        Result result = checkMessage(messageReq, true);
        if (result != null) {
            return result;
        }
        User dbUser = userDao.getUserById(authorId);
        if (dbUser == null) {
            return ResultUtils.buildResult(ResultEnums.USER_NOT_EXIST);
        }
        Message message = new Message();
        message.setAuthorId(authorId);
        message.setContent(messageReq.getContent());
        message.setCreateTime(new Date());
        message.setUpdateTime(new Date());
        long id = messageDao.insertMessage(message);
        return ResultUtils.buildResult(ResultEnums.SUCCESS, id);
    }

    public Result updateMessageById(MessageReq messageReq) {
        Result result = checkMessage(messageReq, false);
        if (result != null) {
            return result;
        }
        long id = messageReq.getId();
        Message dbMessage = messageDao.getMessageById(id);
        if (dbMessage == null) {
            return ResultUtils.buildResult(ResultEnums.MESSAGE_NOT_EXIST);
        }
        Message message = new Message();
        message.setId(id);
        message.setAuthorId(dbMessage.getAuthorId());
        message.setContent(messageReq.getContent());
        message.setCreateTime(dbMessage.getCreateTime());
        message.setUpdateTime(new Date());
        Map<String, Object> condMap = Maps.newHashMap();
        condMap.put("id", id);
        messageDao.updateMessageById(message, condMap);
        return ResultUtils.buildResult(ResultEnums.SUCCESS, id);
    }

    public Result deleteMessageById(long id) {
        Message dbMessage = messageDao.getMessageById(id);
        if (dbMessage == null) {
            return ResultUtils.buildResult(ResultEnums.MESSAGE_NOT_EXIST);
        }
        messageDao.deleteMessageById(id);
        return ResultUtils.buildResult(ResultEnums.SUCCESS, id);
    }

    public Result findMessagePage(PageReq pageReq) {
        try {
            MessageCond cond = new MessageCond();
            if (pageReq != null) {
                BeanUtils.copyProperties(cond, pageReq);
            }
            // 按创建时间降序
            cond.setOrderBy("createTime");
            cond.setAsc(false);
            Page page = messageDao.findMessagePage(cond);
            List<Message> messages = page.getData();
            List<MessageResp> messageResps = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(messages)) {
                for (Message message : messages) {
                    MessageResp messageResp = new MessageResp();
                    BeanUtils.copyProperties(messageResp, message);
                    User user = userDao.getUserById(message.getAuthorId());
                    if (user != null) {
                        UserInfo userInfo = userInfoDao.getUserInfoByUserId(user.getId());
                        if (userInfo != null) {
                            UserInfoResp userInfoResp = new UserInfoResp();
                            BeanUtils.copyProperties(userInfoResp, userInfo);
                            messageResp.setAuthorInfo(userInfoResp);
                        }
                    }
                    messageResps.add(messageResp);
                }
            }
            page.setData(messageResps);
            return ResultUtils.buildResult(ResultEnums.SUCCESS, page);
        } catch (Exception e) {
            log.error("findMessagePage error", e);
            throw new ServiceException("findMessagePage error", e);
        }
    }

    private Result checkMessage(MessageReq messageReq, boolean isInsert) {
        Result result;
        if (isInsert) {
            result = checkParams(messageReq, messageReq.getContent());
        } else {
            result = checkParams(messageReq, messageReq.getId(), messageReq.getContent());
        }
        if (result != null) {
            return result;
        }
        return null;
    }
}
