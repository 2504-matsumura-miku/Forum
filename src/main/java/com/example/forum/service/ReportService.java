package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.micrometer.common.util.StringUtils.isBlank;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport() {
        List<Report> results = reportRepository.findAllByOrderByUpdatedDateDesc();
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<ReportForm> setReportForm(List<Report> results) {
        List<ReportForm> reports = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            ReportForm report = new ReportForm();
            Report result = results.get(i);
            report.setId(result.getId());
            report.setContent(result.getContent());
            report.setCreated_date(result.getCreatedDate());
            reports.add(report);
        }
        return reports;
    }

    /*
     * レコード追加、更新
     */
    public void saveReport(ReportForm reqReport) {
        Report saveReport = setReportEntity(reqReport);
        // id が既に存在するかどうかを DB から確認して、
        // id存在していればupdate、なければinsertが実行
        reportRepository.save(saveReport);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Report setReportEntity(ReportForm reqReport) {
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());
        report.setCreatedDate(reqReport.getCreated_date());
        report.setUpdatedDate(reqReport.getUpdated_date());
        return report;
    }

    /*
     * レコード削除
     */
    public void deleteReport(Integer id) {
        // reportRepositoryにはCRUD操作の為の基本的なメソッドが備わっており、
        // 今回はその中のうち、キーに該当するレコードを削除する「deleteById()」を使用する。
        reportRepository.deleteById(id);
    }

    /*
     * 編集用レコードの取得
     */
    public ReportForm editReport(Integer id) {
        List<Report> results = new ArrayList<>();
        results.add((Report) reportRepository.findById(id).orElse(null));
        List<ReportForm> reports = setReportForm(results);
        return reports.get(0);
    }

    /*
     * 開始日と終了日の取得
     */
    public List<ReportForm> findByDate(String start, String end) {
        Date startDate;
        Date endDate;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 開始日が未入力の場合、デフォルト値
        if (isBlank(start)) {
            try {
                startDate = simpleDateFormat.parse("2020-01-01 00:00:00");
            }  catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            start += " 00:00:00";
            try {
                startDate = simpleDateFormat.parse(start);
            }  catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        // 終了日が未入力の場合、現在日時
        if (isBlank(end)) {
            endDate = new Date();
        } else {
            end += " 23:59:59";
            try {
                // String型のendを"yyyy-MM-dd HH:mm:ss"の形式にしたDate型に変換
                endDate = simpleDateFormat.parse(end);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        List<Report> results = reportRepository.findByCreatedDateBetween(startDate, endDate);
        return setReportForm(results);
    }
}
