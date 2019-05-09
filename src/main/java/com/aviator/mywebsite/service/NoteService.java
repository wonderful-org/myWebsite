package com.aviator.mywebsite.service;

import com.alibaba.fastjson.JSON;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.cond.NoteCond;
import com.aviator.mywebsite.entity.dto.req.NotePageReq;
import com.aviator.mywebsite.entity.dto.req.NoteReq;
import com.aviator.mywebsite.entity.dto.resp.NoteResp;
import com.aviator.mywebsite.entity.dto.resp.UserInfoResp;
import com.aviator.mywebsite.entity.dto.resp.UserResp;
import com.aviator.mywebsite.entity.po.*;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.exception.ServiceException;
import com.aviator.mywebsite.runnable.NoteImgCleanRunnable;
import com.aviator.mywebsite.util.ResultUtils;
import com.aviator.mywebsite.util.SecurityUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @ClassName NoteService
 * @Author aviator_ls
 * @Date 2019/5/1 19:23
 */
public class NoteService extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(NoteService.class);

    public Result insertNote(HttpServletRequest request, NoteReq noteReq) {
        Result result = checkNote(noteReq, true);
        if (result != null) {
            return result;
        }
        Note note = convertFromDTO(noteReq, Note.class);
        Date currentDate = new Date();
        note.setCreateTime(currentDate);
        note.setUpdateTime(currentDate);
        if (CollectionUtils.isNotEmpty(noteReq.getImgUrls())) {
            // 文章中存在的图片链接
            List<String> imgUrls = Lists.newArrayList();
            // 文章中不存在的图片链接(可能用户编辑时插入后删除了)
            List<String> removeImgUrls = Lists.newArrayList();
            for (String url : noteReq.getImgUrls()) {
                if (!noteReq.getContent().contains(url)) {
                    removeImgUrls.add(url);
                } else {
                    imgUrls.add(url);
                }
            }
            note.setImgUrls(JSON.toJSONString(imgUrls));
            if (CollectionUtils.isNotEmpty(removeImgUrls)) {
                new Thread(new NoteImgCleanRunnable(request, removeImgUrls)).start();
            }
        }
        long id = noteDao.insert(note, false);
        return ResultUtils.buildResult(ResultEnums.SUCCESS, id);
    }

    public Result deleteNote(HttpServletRequest request, long id) {
        Note dbNote = noteDao.getById(id, Note.class);
        if (dbNote == null) {
            return ResultUtils.buildResult(ResultEnums.NOTE_NOT_EXIST);
        }
        UserResp currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return ResultUtils.buildResult(ResultEnums.USER_NOT_LOGIN);
        }
        if (currentUser.getId() != dbNote.getAuthorId()) {
            return ResultUtils.buildResult(ResultEnums.USER_HAS_NOT_PERMISSION);
        }
        noteDao.deleteById(id);
        String imgUrlsStr = dbNote.getImgUrls();
        if (StringUtils.isNotBlank(imgUrlsStr)) {
            new Thread(new NoteImgCleanRunnable(request, JSON.parseArray(imgUrlsStr, String.class))).start();
        }
        return ResultUtils.buildResult(ResultEnums.SUCCESS, id);
    }

    public Result updateNote(HttpServletRequest request, NoteReq noteReq) {
        Result result = checkNote(noteReq, false);
        if (result != null) {
            return result;
        }
        Note dbNote = noteDao.getById(noteReq.getId(), Note.class);
        if (dbNote == null) {
            return ResultUtils.buildResult(ResultEnums.NOTE_NOT_EXIST);
        }
        if (dbNote.getAuthorId() != noteReq.getAuthorId()) {
            return ResultUtils.buildResult(ResultEnums.USER_HAS_NOT_PERMISSION);
        }
        // 先删
        noteDao.deleteById(dbNote.getId());
        List<String> imgUrls = noteReq.getImgUrls() == null ? Lists.newArrayList() : noteReq.getImgUrls();
        String dbImgUrlsStr = dbNote.getImgUrls();
        if (StringUtils.isNotBlank(dbImgUrlsStr)) {
            imgUrls.addAll(JSON.parseArray(dbImgUrlsStr, String.class));
            noteReq.setImgUrls(imgUrls);
        }
        // 再增
        return insertNote(request, noteReq);
    }

    public Result getNoteById(long id) {
        Note note = noteDao.getById(id, Note.class);
        if (note != null) {
            try {
                NoteResp noteResp = new NoteResp();
                BeanUtils.copyProperties(noteResp, note);
                User user = userDao.getUserById(note.getAuthorId());
                if (user != null) {
                    UserResp userResp = new UserResp();
                    BeanUtils.copyProperties(userResp, user);
                    noteResp.setAuthor(userResp);
                    UserInfo userInfo = userInfoDao.getUserInfoByUserId(user.getId());
                    if (userInfo != null) {
                        UserInfoResp userInfoResp = new UserInfoResp();
                        BeanUtils.copyProperties(userInfoResp, userInfo);
                        noteResp.setAuthorInfo(userInfoResp);
                    }
                }
                return ResultUtils.buildResult(ResultEnums.SUCCESS, noteResp);
            } catch (Exception e) {
                log.error("getNoteById error", e);
                throw new ServiceException("getNoteById error", e);
            }
        }
        return ResultUtils.buildResult(ResultEnums.NOTE_NOT_EXIST);
    }

    public Result findNotePage(NotePageReq pageReq) {
        try {
            NoteCond cond = new NoteCond();
            if (pageReq != null) {
                BeanUtils.copyProperties(cond, pageReq);
                // 处理归档时间条件
                String betweenDate = pageReq.getBetweenDate();
                if (StringUtils.isNotBlank(betweenDate)) {
                    String datePattern = "yyyy年MM月";
                    List params = Lists.newArrayList();
                    params.add(DateUtils.parseDate(betweenDate, datePattern));
                    params.add(DateUtils.addMonths(DateUtils.parseDate(betweenDate, datePattern), 1));
                    cond.setCreateTime(params);
                }
            }
            // 按创建时间降序
            cond.setOrderBy("createTime");
            cond.setAsc(false);
            // 只展示公开的文章
            cond.setOpen(0);
            Page page = noteDao.findPage(cond, Note.class);
            List<Note> notes = page.getData();
            List<NoteResp> noteResps = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(notes)) {
                for (Note note : notes) {
                    NoteResp noteResp = new NoteResp();
                    BeanUtils.copyProperties(noteResp, note);
                    User user = userDao.getUserById(note.getAuthorId());
                    if (user != null) {
                        UserResp userResp = new UserResp();
                        BeanUtils.copyProperties(userResp, user);
                        noteResp.setAuthor(userResp);
                        UserInfo userInfo = userInfoDao.getUserInfoByUserId(user.getId());
                        if (userInfo != null) {
                            UserInfoResp userInfoResp = new UserInfoResp();
                            BeanUtils.copyProperties(userInfoResp, userInfo);
                            noteResp.setAuthorInfo(userInfoResp);
                        }
                    }
                    String content = noteResp.getContent();
                    if (StringUtils.isNotBlank(content)) {
                        String contentText = Jsoup.parse(content).text();
                        if (StringUtils.isNotBlank(contentText)) {
                            contentText = StringUtils.replace(contentText, " ", "");
                        }
                        noteResp.setContentText(contentText);
                    }
                    noteResps.add(noteResp);
                }
            }
            page.setData(noteResps);
            return ResultUtils.buildResult(ResultEnums.SUCCESS, page);
        } catch (Exception e) {
            log.error("findNotePage error", e);
            throw new ServiceException("findNotePage error", e);
        }
    }

    public Result findNotesByFolderId(long folderId) {
        Folder folder = folderDao.getById(folderId, Folder.class);
        if (folder == null) {
            return ResultUtils.buildResult(ResultEnums.FOLDER_NOT_EXIST);
        }
        List<Note> notes = noteDao.findNoteByFolderId(folderId);
        List<NoteResp> noteResps = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(notes)) {
            for (Note note : notes) {
                NoteResp noteResp = convertToDTO(note, NoteResp.class);
                String content = noteResp.getContent();
                if (StringUtils.isNotBlank(content)) {
                    String contentText = Jsoup.parse(content).text();
                    if (StringUtils.isNotBlank(contentText)) {
                        contentText = StringUtils.replace(contentText, " ", "");
                    }
                    noteResp.setContentText(contentText);
                }
                noteResps.add(noteResp);
            }
        }
        return ResultUtils.buildResult(ResultEnums.SUCCESS, noteResps);
    }

    public Result findNotesByFolderId(Long folderId, long userId) {
        User dbUser = userDao.getUserById(userId);
        if (dbUser == null) {
            return ResultUtils.buildResult(ResultEnums.USER_NOT_EXIST);
        }
        NoteCond cond = new NoteCond();
        cond.setAuthorId(userId);
        cond.setFolderId(folderId);
        List<Note> notes = noteDao.findList(cond, Note.class);
        List<NoteResp> noteResps = Lists.newArrayList();
        for (Note note : notes) {
            NoteResp noteResp = convertToDTO(note, NoteResp.class);
            String content = noteResp.getContent();
            if (StringUtils.isNotBlank(content)) {
                String contentText = Jsoup.parse(content).text();
                if (StringUtils.isNotBlank(contentText)) {
                    contentText = StringUtils.replace(contentText, " ", "");
                }
                noteResp.setContentText(contentText);
            }
            noteResps.add(noteResp);
        }
        return ResultUtils.buildResult(ResultEnums.SUCCESS, noteResps);
    }

    public Result getArchives() {
        // 结果
        Map<String, Object> resultMap = Maps.newHashMap();
        // 查询最早一条记录，得到最晚创建文章时间
        NotePageReq firstPageReq = new NotePageReq();
        Page<NoteResp> firstPage = (Page<NoteResp>) findNotePage(firstPageReq).getData();
        List<NoteResp> notes = firstPage.getData();
        if (CollectionUtils.isEmpty(notes)) {
            return ResultUtils.buildSuccessResult(null);
        }
        NoteResp firstNote = notes.get(0);
        Date lastDate = firstNote.getCreateTime();
        // 查询最晚一条记录，得到最早创建文章时间
        NotePageReq lastPageReq = new NotePageReq();
        lastPageReq.setPageNum((int) firstPage.getPageCount());
        Page<NoteResp> lastPage = (Page<NoteResp>) findNotePage(lastPageReq).getData();
        List<NoteResp> lastNotes = lastPage.getData();
        NoteResp lastNote = lastNotes.get(lastNotes.size() - 1);
        Date firstDate = lastNote.getCreateTime();
        // 处理时间格式
        String datePattern = "yyyy年MM月";
        try {
            firstDate = DateUtils.parseDate(DateFormatUtils.format(firstDate, datePattern), datePattern);
            lastDate = DateUtils.parseDate(DateFormatUtils.format(DateUtils.addMonths(lastDate, 1), datePattern), datePattern);
            if (DateUtils.isSameDay(DateUtils.addMonths(firstDate, 1), lastDate)) {
                resultMap.put(DateFormatUtils.format(firstDate, datePattern), firstPage.getTotalCount());
                return ResultUtils.buildSuccessResult(resultMap);
            }
        } catch (Exception e) {
            log.error("getArchives parseDate error", e);
            throw new ServiceException("getArchives parseDate error", e);
        }
        // 遍历
        Date startDate = firstDate;
        Date endDate = DateUtils.addMonths(firstDate, 1);
        while (!DateUtils.isSameDay(endDate, DateUtils.addMonths(lastDate, 1))) {
            List list = Lists.newArrayList();
            list.add(startDate);
            list.add(endDate);
            NoteCond cond = new NoteCond();
            cond.setOpen(0);
            cond.setCreateTime(list);
            long betweenMonthCount = noteDao.getCount(cond);
            if (betweenMonthCount > 0) {
                resultMap.put(DateFormatUtils.format(startDate, datePattern), betweenMonthCount);
            }
            startDate = DateUtils.addMonths(startDate, 1);
            endDate = DateUtils.addMonths(endDate, 1);
        }
        return ResultUtils.buildSuccessResult(resultMap);
    }

    public Result unload(HttpServletRequest request, NoteReq noteReq) {
        List<String> removeImgUrls = noteReq.getImgUrls();
        if (CollectionUtils.isNotEmpty(removeImgUrls)) {
            new Thread(new NoteImgCleanRunnable(request, removeImgUrls)).start();
        }
        return ResultUtils.buildSuccessResult();
    }

    private Result checkNote(NoteReq noteReq, boolean isInsert) {
        Result result = checkParams(noteReq);
        if (result != null) {
            return result;
        }
        if (isInsert) {
            result = checkParams(noteReq, noteReq.getTitle(), noteReq.getContent());
        } else {
            result = checkParams(noteReq, noteReq.getId(), noteReq.getTitle(), noteReq.getContent());
        }
        if (result != null) {
            return result;
        }
        return null;
    }

    public static void main(String[] args) {
        String datePattern = "yyyy-MM-dd";
        try {
            System.out.println(DateUtils.parseDate(DateFormatUtils.format(new Date(), datePattern), datePattern));
            System.out.println(DateUtils.parseDate(DateFormatUtils.format(DateUtils.addMonths(new Date(), 1), datePattern), datePattern));
        } catch (ParseException e) {
            log.error("getArchives parseDate error", e);
            throw new ServiceException("getArchives parseDate error", e);
        }
    }
}
