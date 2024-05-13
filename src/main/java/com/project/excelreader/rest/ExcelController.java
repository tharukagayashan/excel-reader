package com.project.excelreader.rest;

import com.project.excelreader.service.ExcelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    private final ExcelService excelService;

    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/upload-bank-details")
    public ResponseEntity<String> uploadBankExcel(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return excelService.uploadBankExcel(file);
    }

    @PostMapping("/upload-branch-details")
    public ResponseEntity<String> uploadExcel(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return excelService.uploadBranchExcel(file);
    }

}