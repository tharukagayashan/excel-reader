package com.project.excelreader.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "LD_R_BRANCH_DETAILS")
public class LdRBranchDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BRANCH_DETAILS_ID", nullable = false, length = 8)
    private Integer branchDetailsId;

    @Column(name = "BRANCH_CODE", nullable = false, length = 10)
    private String branchCode;

    @Column(name = "BRANCH_NAME", length = 50)
    private String branchName;

    @Column(name = "CONTACT_NO", length = 15)
    private String contactNo;

    @Column(name = "DESCRIPTION", length = 150)
    private String description;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "STATUS_ID")
    private Integer statusId;

    @ManyToOne
    @JoinColumn(name = "BANK_DETAILS_ID", referencedColumnName = "BANK_DETAILS_ID", nullable = false)
    private LdRBankDetail ldRBankDetail;

}