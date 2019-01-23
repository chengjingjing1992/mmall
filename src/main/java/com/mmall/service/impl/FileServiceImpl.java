package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile multipartFile, String uploadPath){//上传的路径
        String soureFileName=multipartFile.getOriginalFilename();//上传文件的原始文件名
        //获取源文件文件的扩展名
        String fileExtensionName=soureFileName.substring(soureFileName.lastIndexOf(".")+1);
        //对上传之后的文件进行命名
        String uploadFileName= UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:"+soureFileName +"上传的路径:"+ uploadPath+"新文件名:"+uploadFileName);
        File file1=new File(uploadPath);
        if(!file1.exists()){
//            如果上传文件的路径不存在  那就创建它
            file1.setWritable(true);//给一个可以上传文件的权限
            file1.mkdirs();//
        }
        //目标文件对象
        File targetFile=new File(uploadPath,uploadFileName);

        try {
            multipartFile.transferTo(targetFile);
            //文件已经上传成功 文件已经上传到uploadPath 文件夹下了

            //todo 将targetFile上传到我们的FTP服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));

            //上传FTP完后删除upload 下面的文件
//            targetFile.delete();
        } catch (IOException e) {
            logger.info("上传文件异常");
        }


        return targetFile.getName();
    }
}
