package com.example.user.glujam;

/**
 * Created by User on 2018-02-25.
 */

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

public class ftpMain {

    private String svrIp;
    private String user;
    private String passwd;
    private String defaultPath;


    private String targetName;

    public ftpMain(String targetName) {
        this.targetName = targetName;
    }


    public ftpMain(String svrIp, String user, String passwd, String defaultPath) {
        this.svrIp = svrIp;
        this.user = user;
        this.passwd = passwd;
        this.defaultPath = defaultPath;
    }

    /**
     * 파일 업로드
     *
     * @param org        원본파일
     * @param targetFile 저장할 파일위치/파일명
     * @throws IOException
     * @throws SocketException
     */

    public boolean upload(File org, String targetFile)
            throws SocketException, IOException, Exception {

        System.out.println(targetFile);
        FileInputStream fis = null;

        org.apache.commons.net.ftp.FTPClient clnt = new org.apache.commons.net.ftp.FTPClient();
        clnt.setControlEncoding("utf-8");

        try {
            clnt.connect(svrIp);
            //clnt.setBufferSize(1024*1024);
            int reply = clnt.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new Exception("ftp connection refused");
            }

            clnt.setSoTimeout(1000 * 10);
            clnt.login(user, passwd);
            clnt.setFileType(FTP.BINARY_FILE_TYPE);
            clnt.enterLocalActiveMode();
            fis = new FileInputStream(org);
            return clnt.storeFile(targetFile, fis);
        } finally {
            if (clnt.isConnected()) {
                clnt.disconnect();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }


    public void download(String downloadFileName) {

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(svrIp);
            ftpClient.login(user, passwd);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // APPROACH #1: using retrieveFile(String, OutputStream)

            String remoteFile1 = defaultPath;
            File downloadFile1 = new File("./" + downloadFileName);
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
            outputStream1.close();

            System.out.println("FTP result : " + success);

        } catch (IOException ex) {

            ex.printStackTrace();

        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


}