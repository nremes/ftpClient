package com.ftpclient.controllers;

import com.ftpclient.beans.FtpClientBean;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

enum FileMode {
    FILE,
    DIRECTORY
}

@Controller
public class HomeController {
    private final String ENCODING = "UTF-8";
    FtpClientBean ftpClientBean;

    @GetMapping(value = "/")
    public String home(Model model) {
        if(ftpClientBean != null) {
            model.addAttribute("ftpClientIsConnected", ftpClientBean.isConnected());
        }
        return "home";
    }


    @PostMapping(value = "/createConnection")
    public String createConnection(FtpClientBean ftpClient1, Model model) {
        if(ftpClient1.getPort().equals("")) {
            ftpClient1.setPort("21");
        }
        if(ftpClient1.getEncoding().equals("")) {
            ftpClient1.setEncoding(ENCODING);
        }
        ftpClientBean = new FtpClientBean(ftpClient1.getHostName(), ftpClient1.getPort(),
                ftpClient1.getUserName(), ftpClient1.getPassword(), ftpClient1.getEncoding());
        try {
            ftpClientBean.open();
        } catch (IOException e) {
            System.out.println("Creating connection was not successful. Try it again.");
            e.getStackTrace();
        }
        model.addAttribute("ftpClientIsConnected", ftpClientBean.isConnected());
        return "home";
    }


    @GetMapping(value = "/filesList")
    public String filesList(Model model) {
        FTPFile[] files;
        if(ftpClientBean != null) {
            model.addAttribute("currentWorkingDirectory", ftpClientBean.printWorkingDirectory());
            try {
                files = ftpClientBean.listingFiles("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return "home";
        }
        if(files != null) {
            model.addAttribute("files", files);
        }
        return "filesListing";
    }


    @GetMapping(value = "/closeConnection")
    public String closeConnection() {
        if(ftpClientBean.isConnected()) {
            try {
                ftpClientBean.close();
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
        System.out.println("Connection was closed.");
        return "home";
    }


    @GetMapping(value = "/changeRemoteDirectory")
    public String changeRemoteDirectory(@RequestParam(name = "dir") String dir, Model model) {
        if(ftpClientBean != null) {
            ftpClientBean.changeWorkingDirectory(dir);
        } else {
            System.out.println("Change of directory was not successful.");
        }
        filesList(model);
        return "filesListing";
    }


    @GetMapping(value = "/downloadFile")
    public String downloadFile(@RequestParam(name = "file") File file, Model model) {
        FileOutputStream fileOutputStream;
        File dest = chooseFileFromJFileChooser(FileMode.FILE);
        try {
            fileOutputStream = new FileOutputStream(dest + "/" + file.getName());
        } catch (FileNotFoundException e) {
            System.out.println("Downloading failed");
            throw new RuntimeException(e);
        }
        boolean success = ftpClientBean.downloadFile(file, fileOutputStream);
        model.addAttribute("downloadingSuccess", success);
        model.addAttribute("ftpClientIsConnected", ftpClientBean.isConnected());
        return "home";
    }


    private File chooseFileFromJFileChooser(FileMode mode) {
        System.setProperty("java.awt.headless", "false"); //Exception turns off
        File selectedFile = null;
        JFileChooser jFileChooser = new JFileChooser("C:");
        if(mode.name().equals("FILE")) {
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }
        int retValue = jFileChooser.showOpenDialog(null);
        if(retValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jFileChooser.getSelectedFile();
        }
        return selectedFile;
    }

}

