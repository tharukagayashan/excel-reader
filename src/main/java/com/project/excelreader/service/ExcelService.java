package com.project.excelreader.service;

import com.project.excelreader.dao.LdRBankDetailDao;
import com.project.excelreader.dao.LdRBranchDetailDao;
import com.project.excelreader.dto.BankDataDto;
import com.project.excelreader.dto.BranchDataDto;
import com.project.excelreader.model.LdRBankDetail;
import com.project.excelreader.model.LdRBranchDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ExcelService {

    private final LdRBankDetailDao bankDetailDao;
    private final LdRBranchDetailDao branchDetailDao;

    public ExcelService(LdRBankDetailDao bankDetailDao, LdRBranchDetailDao branchDetailDao) {
        this.bankDetailDao = bankDetailDao;
        this.branchDetailDao = branchDetailDao;
    }

    public ResponseEntity<String> uploadBankExcel(MultipartFile file) throws IOException {
        List<BankDataDto> data = readBankExcelData(file);
        log.info("Data from Excel: {}", data.size());

        List<LdRBankDetail> bankDetails = new ArrayList<>();
        for (BankDataDto bankData : data) {
            LdRBankDetail bankDetail = new LdRBankDetail();
            bankDetail.setBankCode(bankData.getBankCode());
            bankDetail.setBankName(bankData.getBankName());
            bankDetail.setContactNo("");
            bankDetail.setDescription(bankData.getBankName());
            bankDetail.setIsActive(true);
            bankDetail.setStatusId(1);

            bankDetails.add(bankDetail);
        }

        // Save data to Postgres database (assuming MyDataService has save method)
        bankDetails = bankDetailDao.saveAll(bankDetails);
        for (LdRBankDetail bankDetail : bankDetails) {
            if (bankDetail.getBankDetailsId() == null) {
                return ResponseEntity.badRequest().body("Failed to save data to database!");
            }
        }
        return ResponseEntity.ok("Excel uploaded and data saved successfully!");
    }

    public ResponseEntity<String> uploadBranchExcel(MultipartFile file) throws IOException {
        List<BranchDataDto> data = readBranchExcelData(file);
        log.info("Data from Excel: {}", data.size());

        List<LdRBranchDetail> branchDetails = new ArrayList<>();
        for (BranchDataDto branch : data) {

            Optional<LdRBankDetail> optBranch = bankDetailDao.findByBankCode(branch.getBankCode());
            if (!optBranch.isPresent()) {
                throw new RuntimeException("Bank not found!. bank code : " + branch.getBankCode());
            }

            LdRBranchDetail branchDetail = new LdRBranchDetail();
            branchDetail.setBranchCode(branch.getBranchCode());
            branchDetail.setBranchName(branch.getBranchName());
            branchDetail.setContactNo(branch.getTelNo1());
            branchDetail.setDescription(branch.getBranchAddress());
            branchDetail.setIsActive(true);
            branchDetail.setStatusId(1);
            branchDetail.setLdRBankDetail(optBranch.get());

            branchDetails.add(branchDetail);
        }

        // Save data to Postgres database (assuming MyDataService has save method)
        branchDetails = branchDetailDao.saveAll(branchDetails);
        for (LdRBranchDetail branchDetail : branchDetails) {
            if (branchDetail.getBranchDetailsId() == null) {
                return ResponseEntity.badRequest().body("Failed to save data to database!");
            }
        }
        return ResponseEntity.ok("Excel uploaded and data saved successfully!");
    }

    private List<BankDataDto> readBankExcelData(MultipartFile file) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);


        List<BankDataDto> data = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            DataFormatter formatter = new DataFormatter();
            String bankCode = formatter.formatCellValue(row.getCell(0));
            String bankName = formatter.formatCellValue(row.getCell(1));
            data.add(new BankDataDto(bankName, bankCode));
        }
        return data;
    }

    private List<BranchDataDto> readBranchExcelData(MultipartFile file) throws IOException {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);


        List<BranchDataDto> data = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            DataFormatter formatter = new DataFormatter();
            String bankCode = formatter.formatCellValue(row.getCell(0));
            String branchCode = formatter.formatCellValue(row.getCell(1));
            String branchName = formatter.formatCellValue(row.getCell(2));
            String branchAddress = formatter.formatCellValue(row.getCell(3));
            String telNo1 = formatter.formatCellValue(row.getCell(4));
            String telNo2 = formatter.formatCellValue(row.getCell(5));
            String telNo3 = formatter.formatCellValue(row.getCell(6));
            String telNo4 = formatter.formatCellValue(row.getCell(7));
            String faxNo = formatter.formatCellValue(row.getCell(8));
            String district = formatter.formatCellValue(row.getCell(9));

            data.add(new BranchDataDto(bankCode, branchCode, branchName, branchAddress, telNo1, telNo2, telNo3, telNo4, faxNo, district));
        }
        return data;
    }

}