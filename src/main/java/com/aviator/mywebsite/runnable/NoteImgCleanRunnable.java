package com.aviator.mywebsite.runnable;

import org.apache.commons.collections.CollectionUtils;

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

    private List<String> removeImgUrls;

    private HttpServletRequest request;

    public NoteImgCleanRunnable(List<String> removeImgUrls, HttpServletRequest request) {
        this.removeImgUrls = removeImgUrls;
        this.request = request;
    }

    @Override
    public void run() {
        if (CollectionUtils.isNotEmpty(removeImgUrls)) {
            for (String removeImgUrl : removeImgUrls) {
                String realPath = request.getServletContext().getRealPath(removeImgUrl);
                File file = new File(realPath);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }
}
