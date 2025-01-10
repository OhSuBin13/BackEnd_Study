package com.study.Boardify.service;

import com.study.Boardify.domain.Board;
import com.study.Boardify.domain.UploadImage;
import com.study.Boardify.repository.BoardRepository;
import com.study.Boardify.repository.UploadImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadImageService {

    private final UploadImageRepository uploadImageRepository;
    private final BoardRepository boardRepository;
    private final String rootPath = System.getProperty("user.dir");
    private final String fileDir = rootPath + "/src/main/resources/static/upload-images/";

    public String getFullPath(String fileName){
        return fileDir + fileName;
    }

    public UploadImage saveImage(MultipartFile multipartFile, Board board) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String savedFilename = UUID.randomUUID() + "." + extractExt(originalFilename);

        multipartFile.transferTo(new File(getFullPath(savedFilename)));

        return uploadImageRepository.save(UploadImage.builder()
                .originalFilename(originalFilename)
                .savedFilename(savedFilename)
                .board(board)
                .build());
    }

    public void deleteImage(UploadImage uploadImage) throws IOException {
        uploadImageRepository.delete(uploadImage);
        Files.deleteIfExists(Paths.get(getFullPath(uploadImage.getSavedFilename())));
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos+1);
    }

    public ResponseEntity<UrlResource> downloadImage(Long boardId) throws MalformedURLException {
        Board board = boardRepository.findById(boardId).get();
        if(board == null || board.getUploadImage() == null){
            return null;
        }

        UrlResource urlResource = new UrlResource("file:" + getFullPath(board.getUploadImage().getSavedFilename()));

        String encodedUploadFileName = UriUtils.encode(board.getUploadImage().getOriginalFilename(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
