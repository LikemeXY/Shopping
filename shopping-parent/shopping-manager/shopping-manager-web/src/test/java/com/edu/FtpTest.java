package com.edu;

import com.edu.common.util.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class FtpTest {
    @Test
    public void text2() throws Exception{
        InputStream local = new FileInputStream("C:\\Users\\11833\\Pictures\\Saved Pictures\\123.jpg");
        FtpUtil ftpUtil = new FtpUtil();
        ftpUtil.uploadFile("192.168.220.129",21,"ftpuser","ftpuser","/home/ftpuser/www/img",
            "2020/9/21","hello2.jpg",local);
    }

    @Test
    public void test() throws Exception{
        FTPClient client = new FTPClient();
        client.connect("192.168.220.129",21);
        client.user("ftpuser");
        client.pass("ftpuser");
        client.setFileType(FTP.BINARY_FILE_TYPE);
        client.changeWorkingDirectory("/home/ftpuser/www/img");
        InputStream local = new FileInputStream("C:\\Users\\11833\\Pictures\\Saved Pictures\\123.jpg");
        client.storeFile("hello1.jpg",local);
        client.logout();
    }
}
