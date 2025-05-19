package com.example.forum.controller;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ForumController {
    @Autowired
    ReportService reportService;

    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        // 投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") ReportForm reportForm) {
        // 投稿をテーブルに格納
        reportService.saveReport(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿削除処理
     */
    @PostMapping("/delete")
    public String deleteContent(@RequestParam("deleteId") Integer id) {
        // 削除する投稿のIDを渡す
        reportService.deleteReport(id);
        // 一覧ページへリダイレクト
        return "redirect:/";
    }

    /*
     * 投稿編集画面表示
     */
    @GetMapping("/edit")
    public ModelAndView editContent(@RequestParam("editId") Integer id) {
        ModelAndView mav = new ModelAndView();
        // 編集投稿のidを取得してFormに格納
        ReportForm reportForm = reportService.editReport(id);
        // 編集投稿をFormにセット
        mav.addObject("formModel", reportForm);
        // 編集画面に遷移
        mav.setViewName("/edit");
        return mav;
    }

    /*
     * 投稿編集処理
     */
    @PostMapping("/update")
    public ModelAndView updateContent(@ModelAttribute("formModel") ReportForm reportForm) {
        // 編集した投稿を更新
        reportService.saveReport(reportForm);
        // トップ画面へリダイレクト
        return new ModelAndView("redirect:/");
    }
}