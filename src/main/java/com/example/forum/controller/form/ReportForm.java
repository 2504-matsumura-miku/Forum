package com.example.forum.controller.form;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class ReportForm {

    private int id;
    @NotBlank
    public String content;
    private Date created_date;
    private Date updated_date;

}
