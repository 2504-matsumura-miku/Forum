package com.example.forum.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "report")
@Getter
@Setter
public class Report {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String content;

    @CreationTimestamp // レコード作成時に自動で現在時刻を設定
    @Column(name = "created_date",updatable = false)
    private Date createdDate;

    @UpdateTimestamp // レコード更新時に自動で現在時刻を設定
    @Column(name = "updated_date")
    private Date updatedDate;

}