package com.epam.bankproject.bankproject.contoller.util;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class OperationData {

    @NotNull
    private Integer senderId;
    @NotNull
    private Integer receiverId;

    private String purpose;

    @NotNull
    private Double transfer;

    private Date date;



}
