package com.example.newtrial24;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public String createPost(PostRequest postRequest) {
        User user = userRepository.findByUserID(postRequest.getUserID());
        if (user == null) {
            return "User does not exist";
        }
        Post post = new Post();
        post.setPostBody(postRequest.getPostBody());
        post.setUser(user);

        postRepository.save(post);
        return "Post created successfully";
    }

    public PostResponse getPostDetails(int postID) {
        Post post = postRepository.findByPostID(postID);
        if (post == null) {
            return null;
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setPostID(post.getPostID());
        postResponse.setPostBody(post.getPostBody());
       // postResponse.setDate(post.getDate());

        List<Comment> comments = post.getComments();
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setCommentID(comment.getCommentID());
            commentResponse.setCommentBody(comment.getCommentBody());
            //commentResponse.setDate(comment.getDate());
            //commentResponse.setUserID(comment.getUser().getUserID());
            //commentResponse.setName(comment.getUser().getName());
            commentResponses.add(commentResponse);
        }
        postResponse.setComments(commentResponses);
        return postResponse;
    }

    public String editPost(int postID, String newPostBody) {
        Post post = postRepository.findByPostID(postID);
        if (post == null) {
            return "Post does not exist";
        }
        post.setPostBody(newPostBody);
        postRepository.save(post);
        return "Post edited successfully";
    }

    public String deletePost(int postID) {
        Post post = postRepository.findByPostID(postID);
        if (post == null) {
            return "Post does not exist";
        }
        postRepository.delete(post);
        return "Post deleted";
    }
}
