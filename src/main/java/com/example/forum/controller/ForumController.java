package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.entity.Comment;
import com.example.forum.service.CommentService;
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
    @Autowired
    CommentService commentService;

    /*
     * 投稿・コメント内容表示処理
     */
    @GetMapping() //トップページ表示のためURLなし
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        // 投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport();
        // コメントを全件取得
        List<CommentForm> commentData = commentService.findAllReport();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        mav.addObject("comments", commentData);
        // mav.addObject("commentForm", new CommentForm());
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
    //  HTTPリクエストのパラメータをJavaメソッドの引数として受け取る
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

    /*
     * コメント投稿処理
     */
    @PostMapping("/comAdd")
    // リクエストのデータが複数(textとmessage_id)あり、それらを1つのFormオブジェクトで扱うので@ModelAttribute
    public ModelAndView addComment(@ModelAttribute("formModel") CommentForm commentForm) {
        // 投稿をテーブルに格納
        commentService.saveComment(commentForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * コメント編集画面表示
     */
    @GetMapping("/comEdit")
    public ModelAndView editComment(@RequestParam("editComId") Integer id) {
        ModelAndView mav = new ModelAndView();
        // 編集コメントのidを取得してFormに格納
        CommentForm commentForm = commentService.editComment(id);
        // 編集投稿をFormにセット
        mav.addObject("formModel", commentForm);
        // 編集画面に遷移
        mav.setViewName("/editComment");
        return mav;
    }

    /*
     * コメント編集処理
     */
    @PostMapping("/updateCom")
    public ModelAndView updateComment(@ModelAttribute("formModel") CommentForm commentForm) {
        // 編集したコメントを更新
        commentService.saveComment(commentForm);
        // トップ画面へリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * コメント削除処理
     */
    @PostMapping("/comDelete") // HTTPのPOSTリクエストを処理するメソッド を定義
    public String deleteComment(@RequestParam("deleteComId") Integer id) {
        // 削除するコメントのIDを渡す
        commentService.deleteComment(id);
        // 一覧ページへリダイレクト
        return "redirect:/";
    }

    /*
     * 絞り込み処理
     */
    @PostMapping("/calendar")
    public ModelAndView calendar(@RequestParam("start") String start,
                                 @RequestParam("end") String end) {

        ModelAndView mav = new ModelAndView();
        List<ReportForm> contentData = reportService.findByDate(start, end);
        List<CommentForm> commentData = commentService.findAllReport();

        mav.setViewName("/top");
        mav.addObject("contents", contentData);
        mav.addObject("comments", commentData);
        mav.addObject("start", start);
        mav.addObject("end", end);
        return mav;
    }
}