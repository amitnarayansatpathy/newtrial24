package com.example.newtrial24;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public String createComment(CommentRequest commentRequest) {
        Post post = postRepository.findByPostID(commentRequest.getPostID());
        if (post == null) {
            return "Post does not exist";
        }
        Comment comment = new Comment();
        comment.setCommentBody(commentRequest.getCommentBody());
        comment.setPost(post);

        commentRepository.save(comment);
        return "Comment created successfully";
    }

    public CommentResponse getCommentDetails(int commentID) {
        Comment comment = commentRepository.findByCommentID(commentID);
        if (comment == null) {
            return null;
        }
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentID(comment.getCommentID());
        commentResponse.setCommentBody(comment.getCommentBody());
       // commentResponse.setDate(comment.getDate());

        //commentResponse.setUserID(comment.getUser().getUserID());
        //commentResponse.setName(comment.getUser().getName());
        return commentResponse;
    }

    public String editComment(int commentID, String newCommentBody) {
        Comment comment = commentRepository.findByCommentID(commentID);
        if (comment == null) {
            return "Comment does not exist";
        }
        comment.setCommentBody(newCommentBody);
        commentRepository.save(comment);
        return "Comment edited successfully";
    }

    public String deleteComment(int commentID) {
        Comment comment = commentRepository.findByCommentID(commentID);
        if (comment == null) {
            return "Comment does not exist";
        }
        commentRepository.delete(comment);
        return "Comment deleted";
    }
}
