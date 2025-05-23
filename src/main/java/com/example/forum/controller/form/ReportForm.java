package com.example.forum.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class ReportForm {

    private int id;

    @NotBlank(message = "投稿内容を入力してください")
    public String content;

    private Date created_date;
    private Date updated_date;

}
