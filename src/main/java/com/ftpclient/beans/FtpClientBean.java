package com.ftpclient.beans;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FtpClientBean {

    private String encoding;
    private String hostName;
    private String port;
    private String userName;
    private String password;
    private FTPClient ftp = new FTPClient();


    public FtpClientBean(String hostName, String port, String userName, String password, String encoding) {
        this.encoding = encoding;
        this.hostName = hostName;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public void open() throws IOException {
        ftp = new FTPClient();
        ftp.setControlEncoding(this.encoding);
        ftp.connect(this.hostName, Integer.parseInt(this.port));

        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
        ftp.login(this.userName, this.password);
    }


    public void close() throws IOException {
        ftp.disconnect();
    }


    public boolean isConnected() {
        return ftp.isConnected();
    }


    public String printWorkingDirectory() {
        if(ftp != null) {
            try {
                return ftp.printWorkingDirectory();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }


    public FTPFile[] listingFiles(String path) throws IOException {
        if(ftp == null) {
            return null;
        }
        //FTPFile[] files =
        return ftp.listFiles(path);
        /*return Arrays.stream(files)
                .map(FTPFile::getName)
                .collect(Collectors.toList());*/
    }


    public void changeWorkingDirectory(String dir) {
        if(ftp != null) {
            try {
                ftp.changeWorkingDirectory(dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void downloadFile(File file, FileOutputStream dest) {
        if(ftp != null) {
            try {
                ftp.retrieveFile(file.getPath(), dest);
            } catch (IOException e) {
                System.out.println("Downloading failed.");
                e.getStackTrace();
            }
        }
    }


    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FTPClient getFtp() {
        return ftp;
    }

    public void setFtp(FTPClient ftp) {
        this.ftp = ftp;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}

