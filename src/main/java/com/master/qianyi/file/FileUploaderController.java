package com.master.qianyi.file;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.OSSClient;

/**
 * @author kyang
 * @date 2019年10月28日
 * @remark 文件上传类
 */

@Controller
@RequestMapping("/manager")
public class FileUploaderController {

    private static String endpoint = "";//买的阿里云的位置
    private static String accessKeyId = "";//阿里云账号的KeyId
    private static String accessKeySecret = "";//阿里云账号的KeySecret
    private static String bucketName = "";//阿里云上自己桶的名字

    protected static OSSClient client = null;//创建阿里云服务器连接对象

    private static Logger logger = LoggerFactory.getLogger(FileUploaderController.class);

    @RequestMapping(value = "/ossFileUpload", method = {RequestMethod.POST})
    @ResponseBody
    public static String fileUpload(MultipartFile ossFile) {
        Date date = new Date();
        String beginTime = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        // 创建一个可重用固定线程数的线程池。若同一时间线程数大于10，则多余线程会放入队列中依次执行
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 完整的文件名
        String key = ossFile.getOriginalFilename();
        // 创建OSSClient实例
        //从自己的配置文件里面取值
        endpoint = OssFileUploadConfig.endpoint;
        accessKeyId = OssFileUploadConfig.accessKeyId;
        accessKeySecret = OssFileUploadConfig.accessKeySecret;
        bucketName = OssFileUploadConfig.bucketName;
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret);//根据阿里云用户配置创建连接对象实例
        try {
            String uploadId = AliyunOSSUpload.claimUploadId(bucketName, key);// key是文件名 By berton
            // 设置每块为 5M(除最后一个分块以外，其他的分块大小都要大于5MB)
            final long partSize = 5 * 1024 * 1024L;
            // 计算分块数目
            long fileLength = ossFile.getSize();// 文件大小
            int partCount = (int) (fileLength / partSize);// 文件大小%分块大小
            if (fileLength % partSize != 0) {
                partCount++;
            }

            // 分块 号码的范围是1~10000。如果超出这个范围，OSS将返回InvalidArgument的错误码。
            if (partCount > 10000) {
                throw new RuntimeException("文件过大(分块大小不能超过10000)");
            } else {
                logger.info("一共分了 " + partCount + " 块");
            }

            /**
             * 将分好的文件块加入到list集合中
             */
            for (int i = 0; i < partCount; i++) {
                // 起始point
                long startPos = i * partSize;
                // 判断当前partSize的长度 是否最后一块
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                // 线程执行。将分好的文件块加入到list集合中()
                executorService
                        .execute(new AliyunOSSUpload(ossFile, startPos, curPartSize, i + 1, uploadId, key, bucketName));
            }

            /**
             * 等待所有分片完毕
             */
            // 关闭线程池（线程池不马上关闭），执行以前提交的任务，但不接受新任务。
            executorService.shutdown();
            // 如果关闭后所有任务都已完成，则返回 true。
            while (!executorService.isTerminated()) {
                try {
                    // 用于等待子线程结束，再继续执行下面的代码
                    executorService.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /**
             * partETags(上传块的ETag与块编号（PartNumber）的组合) 如果校验与之前计算的分块大小不同，则抛出异常
             */
            System.out.println(AliyunOSSUpload.partETags.size() + " -----   " + partCount);
            if (AliyunOSSUpload.partETags.size() != partCount) {
                throw new IllegalStateException("OSS分块大小与文件所计算的分块大小不一致");
            } else {
                logger.info("将要上传的文件名  " + key + "\n");
            }

            /*
             * 列出文件所有的分块清单并打印到日志中，该方法仅仅作为输出使用
             */
            AliyunOSSUpload.listAllParts(uploadId);

            /*
             * 完成分块上传
             */
            AliyunOSSUpload.completeMultipartUpload(uploadId);

            // 返回上传文件的URL地址
            // return endpoint.replaceFirst("http://","http://"+bucketName+".")+"/"+key;
            Date date2 = new Date();
            String endTime = date2.getHours() + ":" + date2.getMinutes() + ":" + date2.getSeconds();
            return beginTime + "========" + endTime+"   "+endpoint.replaceFirst("http://","http://"+bucketName+".")+"/"+key;

        } catch (Exception e) {
            logger.error("上传失败！", e);
            return "上传失败！";
        } finally {
            AliyunOSSUpload.partETags.clear();
            if (client != null) {
                client.shutdown();
            }
        }
    }
}
