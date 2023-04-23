package com.khoders.entities.files;

import com.khoders.entities.Ref;
import com.khoders.entities.constants.FileType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "files")
public class Files extends Ref {
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

    @Column(name = "description")
    @Lob
    private String description;

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

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
