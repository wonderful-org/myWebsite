package com.aviator.mywebsite.service;

import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.entity.cond.FolderCond;
import com.aviator.mywebsite.entity.dto.req.FolderReq;
import com.aviator.mywebsite.entity.dto.resp.FolderResp;
import com.aviator.mywebsite.entity.dto.resp.UserResp;
import com.aviator.mywebsite.entity.po.Folder;
import com.aviator.mywebsite.entity.po.Note;
import com.aviator.mywebsite.entity.po.User;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.runnable.NoteImgCleanRunnable;
import com.aviator.mywebsite.util.ResultUtils;
import com.aviator.mywebsite.util.SecurityUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @ClassName FolderService
 * @Author aviator_ls
 * @Date 2019/5/8 21:15
 */
public class FolderService extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(FolderService.class);

    public Result insertFolder(FolderReq folderReq) {
        Result result = checkParams(folderReq.getFolderName(), folderReq.getAuthorId());
        if (result != null) {
            return result;
        }
        Folder folder = convertFromDTO(folderReq, Folder.class);
        Date currentDate = new Date();
        folder.setCreateTime(currentDate);
        folder.setUpdateTime(currentDate);
        long id = folderDao.insert(folder, false);
        return ResultUtils.buildResult(ResultEnums.SUCCESS, id);
    }

    public Result updateFolder(FolderReq folderReq) {
        Result result = checkParams(folderReq.getFolderName(), folderReq.getAuthorId());
        if (result != null) {
            return result;
        }
        Folder dbFolder = folderDao.getById(folderReq.getId(), Folder.class);
        if (dbFolder == null) {
            return ResultUtils.buildResult(ResultEnums.FOLDER_NOT_EXIST);
        }
        if(folderReq.getAuthorId() != dbFolder.getAuthorId()){
            return ResultUtils.buildResult(ResultEnums.USER_HAS_NOT_PERMISSION);
        }
        Folder folder = convertFromDTO(folderReq, Folder.class);
        folder.setAuthorId(dbFolder.getAuthorId());
        folder.setParentFolderId(dbFolder.getParentFolderId());
        folder.setUpdateTime(new Date());
        Map<String, Object> params = Maps.newHashMap();
        params.put("id", folder.getId());
        folderDao.update(folderReq, params);
        return ResultUtils.buildResult(ResultEnums.SUCCESS);
    }

    public Result getFolderById(long id) {
        Folder folder = folderDao.getById(id, Folder.class);
        FolderResp folderResp = null;
        if (folder != null) {
            folderResp = convertToDTO(folder, FolderResp.class);
        }
        return ResultUtils.buildResult(ResultEnums.SUCCESS, folderResp);
    }

    public Result deleteFolder(HttpServletRequest request, long folderId) {
        Folder dbFolder = folderDao.getById(folderId, Folder.class);
        UserResp currentUser = SecurityUtils.getCurrentUser(request);
        if (currentUser == null) {
            return ResultUtils.buildResult(ResultEnums.USER_NOT_LOGIN);
        }
        if (dbFolder == null) {
            return ResultUtils.buildResult(ResultEnums.FOLDER_NOT_EXIST);
        }
        if (currentUser.getId() != dbFolder.getAuthorId()) {
            return ResultUtils.buildResult(ResultEnums.USER_HAS_NOT_PERMISSION);
        }
        int result = folderDao.deleteById(folderId);
        if (result > 0) {
            List<Note> notes = noteDao.findNoteByFolderId(folderId);
            List<String> imgFilePaths = Lists.newArrayList();
            List<Long> noteIds = Lists.newArrayList();
            if (CollectionUtils.isNotEmpty(notes)) {
                for (Note note : notes) {
                    noteIds.add(note.getId());
                    String imgFilePath = note.getImgUrls();
                    imgFilePaths.add(imgFilePath);
                }
                noteDao.deleteByIds(noteIds);
                new Thread(new NoteImgCleanRunnable(request, imgFilePaths)).start();
            }
        }
        return ResultUtils.buildResult(ResultEnums.SUCCESS, dbFolder.getParentFolderId());
    }

    public Result findFoldersByFolderId(long folderId, long userId) {
        User dbUser = userDao.getUserById(userId);
        if (dbUser == null) {
            return ResultUtils.buildResult(ResultEnums.USER_NOT_EXIST);
        }
        FolderCond cond = new FolderCond();
        cond.setAuthorId(userId);
        cond.setParentFolderId(folderId);
        List<Folder> folders = folderDao.findList(cond, Folder.class);
        List<FolderResp> folderResps = Lists.newArrayList();
        for (Folder folder : folders) {
            folderResps.add(convertToDTO(folder, FolderResp.class));
        }
        return ResultUtils.buildResult(ResultEnums.SUCCESS, folderResps);
    }

    public Result findFoldersByUserId(long userId) {
        User dbUser = userDao.getUserById(userId);
        if (dbUser == null) {
            return ResultUtils.buildResult(ResultEnums.USER_NOT_EXIST);
        }
        List<Folder> folders = folderDao.findFoldersByUserId(userId);
        List<FolderResp> folderResps = Lists.newArrayList();
        for (Folder folder : folders) {
            folderResps.add(convertToDTO(folder, FolderResp.class));
        }
        return ResultUtils.buildResult(ResultEnums.SUCCESS, folderResps);
    }
}
