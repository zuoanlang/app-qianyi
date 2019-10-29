//package com.master.qianyi.file;
//
//
//import com.aliyun.oss.*;
//import com.aliyun.oss.model.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.*;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * @author kyang
// * @author kyang
// * @date 2019年10月28日
// * @remark 该类的说明，包括但不限于功能描述、重要算法思想、流程、注意事项等内容
// * @date 2019年10月28日
// * @remark 此次修改的说明，改了哪些东西
// */
//public class MultipartUpload {
//    private static final Logger logger = LoggerFactory.getLogger(MultipartUpload.class);
//
//    private static String endpoint = "**********";
//    private static String accessKeyId = "**********";
//    private static String accessKeySecret = "**********";
//    private static String bucketName = "**********";
//    private static String key = null;
//    private static String localFilePath = "**********";
//    private static OSS client = null;
//
//    // 定长线程池
//    private static ExecutorService threadPool;
//
//    private static List<PartETag> partETags = null;
//    private static final int partSize = 5 * 1024 * 1024;   // 5MB 分片大小
//
//
//    private static String upload(String localPath) throws IOException {
//        long start = System.currentTimeMillis();
//        String[] names = localPath.split("\\.");
//        String fileTypeName = names[names.length - 1];
//        key = localFilePath + UUID.randomUUID() + "." + fileTypeName;
//        ClientConfiguration conf = new ClientConfiguration();
//        conf.setIdleConnectionTime(5000);
//        client = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
//        File sampleFile = null;
//        String fileUrl = null;
//        try {
//            String uploadId = claimUploadId();
//            logger.info("申请一个新的上传id:" + uploadId);
//            // 需上传文件
//            sampleFile = new File(localPath);
//            // 文件大小
//            long fileLength = sampleFile.length();
//            // 分片总数（总共分几个部分）
//            int partCount = (int) (fileLength / partSize);
//            if (fileLength % partSize != 0) {
//                partCount++;
//            }
//            if (partCount > 10000) {
//                logger.warn("partCount总数不应超过10000");
//                return null;
//            } else {
//                logger.info("文件总共分片数:" + partCount);
//            }
//            partETags = Collections.synchronizedList(new ArrayList<PartETag>(partCount));
//            CountDownLatch latch = new CountDownLatch(partCount);
//            logger.info("***************开始准备上传************");
//            threadPool = Constans.getMyThreadPool();
//            for (int i = 0; i < partCount; i++) {
//                long startPos = i * partSize;
//                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
//                threadPool.execute(new PartUploader(sampleFile, startPos, curPartSize, i + 1, uploadId, latch));
//            }
//            latch.await();
//            if (partETags.size() != partCount) {
//                StringBuilder partETagsStr = new StringBuilder("(");
//                for (PartETag item : partETags) {
//                    partETagsStr.append(item.getPartNumber()).append(",");
//                }
//                partETagsStr.append(")");
//                logger.info(String.format("partCount:%s*******,partEtages:%s*******,partETagsSize:%s", partCount, partETagsStr, partETags.size()));
//                throw new IllegalStateException("上传多个部分失败，因为有些部分还没有完成");
//            } else {
//                logger.info("成功地将多个部分合并上传到一个名为的对象中 " + key);
//            }
//            listAllParts(uploadId);
//            completeMultipartUpload(uploadId);
//            logger.info("获取一个对象");
//            long end = System.currentTimeMillis();
//            // 生成文件地址
////            client.getObject(new GetObjectRequest(bucketName, key), new File(localFilePath));
//            boolean isFileExist = client.doesObjectExist(bucketName, key);
//            if (isFileExist) {
//                fileUrl = endpoint.replaceAll("\\/\\/", "//" + bucketName + ".") + key;
//                logger.info(String.format("上传成功*****耗时：%s*****，文件地址：%s", ((end - start) / 1000), fileUrl));
//            } else {
//                throw new Exception("上传失败，文件不存在");
//            }
//        } catch (OSSException oe) {
//            logger.error(oe.getMessage(), oe);
//        } catch (ClientException ce) {
//            logger.error(ce.getErrorMessage(), ce);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        } finally {
//            if (client != null) {
//                client.shutdown();
//            }
//            if (sampleFile != null) {
//                sampleFile.delete();
//            }
//            partETags.clear();
//            partETags = null;
//            return fileUrl;
//        }
//    }
//
//
//    /**
//     * 静态内部类，上传组件
//     */
//    private static class PartUploader implements Runnable {
//        private File localFile;
//        private long partSize;
//        private int partNumber;
//        private String uploadId;
//        private long startPos;
//        private CountDownLatch latch;
//
//        public PartUploader(File localFile, long startPos, long partSize, int partNumber, String uploadId, CountDownLatch latch) {
//            this.localFile = localFile;
//            this.partSize = partSize;
//            this.partNumber = partNumber;
//            this.uploadId = uploadId;
//            this.startPos = startPos;
//            this.latch = latch;
//        }
//
//        @Override
//        public void run() {
//            InputStream instream = null;
//            try {
//                logger.info("Part#" + this.partNumber + " 开始上传\n");
//                instream = new FileInputStream(localFile);
//                instream.skip(startPos);
//                UploadPartRequest uploadPartRequest = new UploadPartRequest();
//                uploadPartRequest.setBucketName(bucketName);
//                uploadPartRequest.setKey(key);
//                uploadPartRequest.setUploadId(this.uploadId);
//                uploadPartRequest.setInputStream(instream);
//                uploadPartRequest.setPartSize(this.partSize);
//                uploadPartRequest.setPartNumber(this.partNumber);
//                UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
//                logger.info("Part#" + this.partNumber + " 完毕\n");
//                synchronized (partETags) {
//                    partETags.add(uploadPartResult.getPartETag());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (instream != null) {
//                    try {
//                        instream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                latch.countDown();
//            }
//        }
//    }
//
//    private static String claimUploadId() {
//        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
//        // 我本地上传mp4视频，大家可根据自己的文件类型，设置不同的响应content-type
//        request.addHeader("Content-Type", "video/mp4");
//        request.addHeader("Cache-Control", "no-cache");
//        InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
//        return result.getUploadId();
//    }
//
//    private static void completeMultipartUpload(String uploadId) {
//        // Make part numbers in ascending order
//        Collections.sort(partETags, new Comparator<PartETag>() {
//
//            @Override
//            public int compare(PartETag p1, PartETag p2) {
//                return p1.getPartNumber() - p2.getPartNumber();
//            }
//        });
//
//        logger.info("Completing to upload multiparts\n");
//        CompleteMultipartUploadRequest completeMultipartUploadRequest =
//                new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
//        client.completeMultipartUpload(completeMultipartUploadRequest);
//    }
//
//    private static void listAllParts(String uploadId) {
//        logger.info("Listing all parts......");
//        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
//        PartListing partListing = client.listParts(listPartsRequest);
//
//        int partCount = partListing.getParts().size();
//        for (int i = 0; i < partCount; i++) {
//            PartSummary partSummary = partListing.getParts().get(i);
//            logger.info("\tPart#" + partSummary.getPartNumber() + ", ETag=" + partSummary.getETag());
//        }
//    }
//
//    /**
//     * 上传执行器
//     */
//    public synchronized static String fileUpload(String localPath) {
//        ReentrantLock lock = new ReentrantLock();
//        lock.lock();
//        long start = System.currentTimeMillis();
//        try {
//            String fileUrl = upload(localPath);
//            long end = System.currentTimeMillis();
//            logger.info("文件上传结束,共耗时" + (end - start) + "ms");
//            return fileUrl;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            lock.unlock();
//        }
//    }
//}
