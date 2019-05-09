package com.aviator.mywebsite.service;

import com.aviator.mywebsite.controller.UploadServlet;
import com.aviator.mywebsite.entity.Result;
import com.aviator.mywebsite.enums.ResultEnums;
import com.aviator.mywebsite.util.ResultUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @Description TODO
 * @ClassName UploadService
 * @Author aviator_ls
 * @Date 2019/4/30 10:00
 */
public class UploadService {

    private static final Logger log = LoggerFactory.getLogger(UploadServlet.class);

    // 上传单个文件最大大小
    private static final long FILE_MAX_SIZE = 1024 * 1024 * 3;

    // 上传多个文件最大总大小
    private static final long MULTI_FILE_MAX_SIZE = 1024 * 1024 * 20;

    private static final String UPLOAD_PATH = "/fileupload";

    private static final String UPLOAD_TEMP_PATH = "/fileupload/temp";

    private static String savePath;

    public Result upload(String uploadType, HttpServletRequest request) {
        return upload(uploadType, false, true, request);
    }

    public Result upload(String uploadType, boolean remakeFilePath, boolean remakeFileName, HttpServletRequest request) {
        savePath = request.getServletContext().getRealPath(UPLOAD_PATH);
        String tempPath = request.getServletContext().getRealPath(UPLOAD_TEMP_PATH);
        File tempFile = new File(tempPath);
        if (!tempFile.exists()) {
            tempFile.mkdir();
        }
        String filePath = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 设置缓冲区大小为100kb，默认10kb，超出缓冲区会将临时文件放入临时文件夹
            factory.setSizeThreshold(1024 * 100);
            // 设置临时文件夹路径
            factory.setRepository(tempFile);
            // 创建文件上传解析器
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            // 监听上传进度
            fileUpload.setProgressListener(new ProgressListener() {
                @Override
                public void update(long pBytesRead, long pContentLength, int arg2) {
                    log.info("上传文件大小：{}， 当前已处理：{}", pContentLength, pBytesRead);
                }
            });
            // 设置编码
            fileUpload.setHeaderEncoding("UTF-8");
            // 判断是否为表单数据
            if (!ServletFileUpload.isMultipartContent(request)) {
                log.error("upload file is not multipartContent");
                return null;
            }
            // 设置上传单个文件最大大小
            fileUpload.setFileSizeMax(FILE_MAX_SIZE);
            // 设置上传文件总量最大值
            fileUpload.setSizeMax(MULTI_FILE_MAX_SIZE);
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = fileUpload.parseRequest(request);
            for (FileItem item : list) {
                try {
                    //如果fileitem中封装的是普通输入项的数据
                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        //解决普通输入项的数据的中文乱码问题
                        String value = item.getString("UTF-8");
                        System.out.println(name + "=" + value);
                    } else {//如果fileitem中封装的是上传文件
                        //得到上传的文件名称，
                        String filename = item.getName();
                        log.info("upload filename: {}", filename);
                        if (StringUtils.isBlank(filename)) {
                            continue;
                        }
                        //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                        //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                        filename = filename.indexOf(File.separator) < 0 ? filename : StringUtils.substringAfterLast(filename, File.separator);
                        //得到上传文件的扩展名
                        String fileExtName = filename.substring(filename.lastIndexOf("."));
                        //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                        log.info("upload fileExtName: {}", fileExtName);
                        InputStream in = null;
                        FileOutputStream out = null;
                        try {
                            //获取item中的上传文件的输入流
                            in = item.getInputStream();
                            //得到文件保存的名称
                            String saveFilename = filename;
                            if (remakeFileName) {
                                saveFilename = makeFileName(filename);
                            }
                            //得到文件的保存目录
                            String realSavePath = savePath + File.separator + uploadType;
                            if (remakeFilePath) {
                                realSavePath = makePath(saveFilename, savePath, uploadType);
                            }
                            //如果目录不存在
                            File realSavePathFile = new File(realSavePath);
                            if (!realSavePathFile.exists()) {
                                //创建目录
                                realSavePathFile.mkdirs();
                            }
                            filePath = realSavePath + File.separator + saveFilename;
                            //创建一个文件输出流
                            out = new FileOutputStream(filePath);
                            //创建一个缓冲区
                            byte buffer[] = new byte[1024];
                            //判断输入流中的数据是否已经读完的标识
                            int len;
                            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                            while ((len = in.read(buffer)) > 0) {
                                //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                                out.write(buffer, 0, len);
                            }
                        } finally {
                            //关闭输入流
                            IOUtils.closeQuietly(in);
                            //关闭输出流
                            IOUtils.closeQuietly(out);
                        }
                    }
                } finally {
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                }
            }
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            return ResultUtils.buildResult(ResultEnums.UPLOAD_LIMIT_FILE_MAX_SIZE);
        } catch (FileUploadBase.SizeLimitExceededException e) {
            return ResultUtils.buildResult(ResultEnums.UPLOAD_LIMIT_MULT_FILE_MAX_SIZE);
        } catch (Exception e) {
            return ResultUtils.buildResult(ResultEnums.UPLOAD_ERROR);
        }
        return ResultUtils.buildSuccessResult(StringUtils.isBlank(filePath) ? null : filePath.substring(filePath.indexOf(UPLOAD_PATH.replace("/", File.separator))).replace(File.separator, "/"));
    }

    public static String getUploadPath(String uploadType) {
        return savePath + File.separator + uploadType;
    }

    /**
     * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
     *
     * @param filename 文件名，要根据文件名生成存储目录
     * @param savePath 文件存储路径
     * @return 新的存储目录
     * @Method: makePath
     * @Description:
     * @Anthor:孤傲苍狼
     */
    private String makePath(String filename, String savePath, String uploadType) {
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        int dir1 = hashcode & 0xf;  //0--15
        int dir2 = (hashcode & 0xf0) >> 4;  //0-15
        //构造新的保存目录
        String dir = savePath + File.separator + uploadType + File.separator + dir1 + File.separator + dir2;  //upload\2\3  upload\3\5
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if (!file.exists()) {
            //创建目录
            file.mkdirs();
        }
        return dir;
    }

    /**
     * @param filename 文件的原始名称
     * @return uuid+"_"+文件的原始名称
     * @Method: makeFileName
     * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
     * @Anthor:孤傲苍狼
     */
    private String makeFileName(String filename) {  //2.jpg
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
    }
}
