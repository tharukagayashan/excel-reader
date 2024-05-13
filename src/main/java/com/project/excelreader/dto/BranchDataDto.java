package com.project.excelreader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchDataDto {
    private String bankCode;
    private String branchCode;
    private String branchName;
    private String branchAddress;
    private String telNo1;
    private String telNo2;
    private String telNo3;
    private String telNo4;
    private String faxNo;
    private String district;
}
