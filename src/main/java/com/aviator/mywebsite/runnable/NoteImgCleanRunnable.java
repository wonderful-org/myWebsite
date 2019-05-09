package com.aviator.mywebsite.runnable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @Description TODO
 * @ClassName NoteImgCleanRunnable
 * @Author aviator_ls
 * @Date 2019/5/3 17:47
 */
public class NoteImgCleanRunnable implements Runnable {

    private HttpServletRequest request;

    private List<String> removeImgUrls;

    public NoteImgCleanRunnable(HttpServletRequest request, List<String> removeImgUrls) {
        this.request = request;
        this.removeImgUrls = removeImgUrls;
    }

    @Override
    public void run() {
        if (CollectionUtils.isNotEmpty(removeImgUrls)) {
            for (String removeImgFilePath : removeImgUrls) {
                if (StringUtils.isNotBlank(removeImgFilePath)) {
                    String realPath = request.getServletContext().getRealPath(removeImgFilePath);
                    File file = new File(realPath);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }
    }

}
