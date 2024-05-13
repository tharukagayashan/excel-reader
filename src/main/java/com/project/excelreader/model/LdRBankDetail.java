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
@Table(name = "LD_R_BANK_DETAILS")
public class LdRBankDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANK_DETAILS_ID", nullable = false, length = 8)
    private Integer bankDetailsId;

    @Column(name = "BANK_CODE", nullable = false, length = 10)
    private String bankCode;

    @Column(name = "BANK_NAME", length = 50)
    private String bankName;

    @Column(name = "CONTACT_NO", length = 15)
    private String contactNo;

    @Column(name = "DESCRIPTION", length = 150)
    private String description;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "STATUS_ID")
    private Integer statusId;

}