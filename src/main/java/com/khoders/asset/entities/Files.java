package com.khoders.asset.entities;

import com.khoders.asset.entities.constants.FileType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "files")
public class Files extends Ref{
    @Column(name = "file_name")
    @Lob
    private String fileName;

    @Column(name = "file_size")
    private double fileSize;

    @Column(name = "upload_date")
    private LocalDate uploadDate;

    @Column(name = "file_location")
    @Lob
    private String fileLocation;

    @Column(name = "file_type")
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
