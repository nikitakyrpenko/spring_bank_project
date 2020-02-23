package com.epam.bankproject.bankproject.view.data;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;

@Data
public class OperationData {

    @NotNull
    private Integer senderId;
    @NotNull
    private Integer receiverId;

    private String purpose;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal transfer;

    private Date date;



}
