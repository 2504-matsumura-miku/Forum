package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.repository.CommentRepository;
import com.example.forum.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    /*
     * レコード全件取得処理
     */
    public List<CommentForm> findAllReport() {
        List<Comment> results = commentRepository.findAllByOrderByIdDesc();
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
        List<CommentForm> Comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            CommentForm Comment = new CommentForm();
            Comment result = results.get(i);
            Comment.setId(result.getId());
            Comment.setText(result.getText());
            Comment.setMessage_id(result.getMessage_id());
            Comment.setCreated_date(result.getCreated_date());
            Comment.setUpdated_date(result.getUpdated_date());

            Comments.add(Comment);
        }
        return Comments;
    }

    /*
     * レコード追加、更新
     */
    public void saveComment(CommentForm reqComment) {
        Comment saveComment = setCommentEntity(reqComment);
        // id が既に存在するかどうかを DB から確認して、
        // id存在していればupdate、なければinsertが実行
        commentRepository.save(saveComment);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqComment) {
        Comment Comment = new Comment();
        Comment.setId(reqComment.getId());
        Comment.setText(reqComment.getText());
        Comment.setMessage_id(reqComment.getMessage_id());
//        Comment.setCreated_date(reqComment.getCreated_date());
//        Comment.setUpdated_date(reqComment.getUpdated_date());

        return Comment;
    }

    public CommentForm editComment(Integer id) {
        List<Comment> results = new ArrayList<>();
        results.add((Comment) commentRepository.findById(id).orElse(null));
        List<CommentForm> Comments = setCommentForm(results);
        return Comments.get(0);
    }

    /*
     * レコード削除
     */
    public void deleteComment(Integer id) {
            commentRepository.deleteById(id);
    }

}
