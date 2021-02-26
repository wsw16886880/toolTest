package com.test.starter.test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author: xq
 * @Date: 2021/02/22 16:55
 */
public class ZipTest {

    /**
     * 压缩文件的入口方法
     * @param zipFileName 压缩文件名
     * @param inputFile 要压缩的文件或文件目录
     * @throws IOException
     */
    public void zip(String zipFileName, File inputFile) throws IOException {
        // 1.获取zip输出流
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
        // 2.开始压缩
        zip(zos, inputFile, "压缩文件");
        // 关闭流
        zos.close();
    }

    /**
     * 压缩文件的执行方法
     * @param zos 压缩输出流
     * @param inputFile 要压缩的文件或文件目录
     * @param base 压缩文件的内部基础路径，可不填
     * @throws IOException
     */
    public void zip(ZipOutputStream zos, File inputFile, String base) throws IOException {
        // 3.判断要压缩的文件是不是目录
        if (inputFile.isDirectory()) { // 如果是目录，则遍历目录，将里面的文件一一压缩
            // 3.1 获取该目录下的所有文件
            File[] inFiles = inputFile.listFiles();
            // 3.2 设置base路径
            base = base.matches("[\\/\\\\]$") ? base + inputFile.getName() + "/" : base + "/" + inputFile.getName() + "/";
            for (File inFile : inFiles) {
                // 3.3 递归：将目录里面的文件压缩进上面创建的压缩目录里
                zip(zos, inFile, base);
            }
        } else { // 4.文件不是目录
            // 4.1 创建文件流
            BufferedInputStream bi = new BufferedInputStream(new FileInputStream(inputFile));
            // 4.2 创建压缩文件
            zos.putNextEntry(new ZipEntry(base + inputFile.getName()));
            // 4.3 将文件内容写入到压缩流
            int size = 0;
            byte[] buffer = new byte[1024];
            while ((size = bi.read(buffer)) > 0) {
                zos.write(buffer, 0, size);
            }
            // 4.4 关闭流
            bi.close();
        }
    }

    public static void main(String[] args) throws IOException {
        ZipTest zipTest = new ZipTest();
        zipTest.zip("D:\\testZip.zip", new File("D:\\123"));
    }
}
