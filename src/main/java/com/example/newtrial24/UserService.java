
package com.example.newtrial24;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;


    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            return "User does not exist";
        } else if (!user.getPassword().equals(loginRequest.getPassword())) {
            return "Username/Password Incorrect";
        } else {
            return "Login Successful";
        }
    }

    /*
    public String signup(SignupRequest signupRequest) {
        User existingUserByEmail = userRepository.getUserByEmail(signupRequest.getEmail());
        User existingUserByName = userRepository.getUserByName(signupRequest.getName());
        if ((existingUserByEmail != null) || (existingUserByName != null)) {
            return "Forbidden, Account already exists";
        }
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(signupRequest.getPassword());
        userRepository.save(user);
        return "Account Creation Successful";
    }

     */

    public String signup(SignupRequest signupRequest) {
        User existingUserByEmail = userRepository.getUserByEmail(signupRequest.getEmail());
        if (existingUserByEmail != null) {
            return "Forbidden, Account already exists";
        }
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(signupRequest.getPassword());
        userRepository.save(user);
        return "Account Creation Successful";
    }


    public ResponseWrapper<UserResponse> getUserDetails(int userID) {
        User user = userRepository.findByUserID(userID);
        if (user == null) {
            return new ResponseWrapper<>(new ErrorResponse("User does not exist"));
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setName(user.getName());
        userResponse.setUserID(user.getUserID());
        userResponse.setEmail(user.getEmail());
        return new ResponseWrapper<>(userResponse);
    }

    public String editPost(String postBody, int postID) {
        Post post = postRepository.findByPostID(postID);
        if (post == null) {
            return "Post does not exist";
        }
        post.setPostBody(postBody);
        postRepository.save(post);
        return "Post edited successfully";
    }

    /*
    public String deletePost(int postID) {
        Post post = postRepository.findByPostID(postID);
        if (post == null) {
            return "Post does not exist";
        }
        List<Comment> comments = post.getComments();
        for (Comment comment : comments) {
            commentRepository.delete(comment);
        }
        postRepository.delete(post);
        return "Post deleted";
    }

     */

    public String deletePost(int postID) {
        Post post = postRepository.findByPostID(postID);
        if (post == null) {
            return "Post does not exist";
        }
        List<Comment> comments = post.getComments();
        for (Comment comment : comments) {
            commentRepository.delete(comment);
        }
        postRepository.delete(post);
        return "Post deleted";
    }


    public ResponseWrapper<PostResponse> getPostDetails(int postID) {
        Post post = postRepository.findByPostID(postID);
        if (post == null) {
            return new ResponseWrapper<>(new ErrorResponse("Post does not exist"));
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setPostID(post.getPostID());
        postResponse.setPostBody(post.getPostBody());
        postResponse.setDate(post.getDate());

        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : post.getComments()) {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setCommentID(comment.getCommentID());
            commentResponse.setCommentBody(comment.getCommentBody());

            User commentCreator = comment.getUser();
            if (commentCreator != null) {
                CommentCreatorResponse creatorResponse = new CommentCreatorResponse();
                creatorResponse.setUserID(commentCreator.getUserID());
                creatorResponse.setName(commentCreator.getName());
                commentResponse.setCommentCreator(creatorResponse);
            }
            commentResponses.add(commentResponse);
        }
        postResponse.setComments(commentResponses);
        //return postResponse;

        return new ResponseWrapper<>(postResponse);
    }

    /*
    public String createPost(PostRequest postRequest) {
        User user = userRepository.findByUserID(postRequest.getUserID());
        if (user == null) {
            return "User does not exist";
        }
        Post post = new Post();
        post.setPostBody(postRequest.getPostBody());
        post.setUser(user);
        post.setDate(LocalDate.now());
        postRepository.save(post);
        return "Post created successfully";
    }

     */

    public String createPost(PostRequest postRequest) {
        User user = userRepository.findByUserID(postRequest.getUserID());
        if (user == null) {
            return "User does not exist";
        }
        Post post = new Post();
        post.setPostBody(postRequest.getPostBody());
        post.setUser(user);
        post.setDate(new Date()); // Set the current date
        postRepository.save(post);
        return "Post created successfully";
    }

    public String editComment(String commentBody, int commentID) {
        Comment comment = commentRepository.findByCommentID(commentID);
        if (comment == null) {
            return "Comment does not exist";
        }
        comment.setCommentBody(commentBody);
        commentRepository.save(comment);
        return "Comment edited successfully";
    }

    /*
    public String deleteComment(int commentID) {
        Comment comment = commentRepository.findByCommentID(commentID);
        if (comment == null) {
            return "Comment does not exist";
        }
        commentRepository.delete(comment);
        return "Comment deleted";
    }

     */

    public String deleteComment(int commentID) {
        Comment comment = commentRepository.findByCommentID(commentID);
        if (comment == null) {
            return "Comment does not exist";
        }
        commentRepository.delete(comment);
        return "Comment deleted";
    }


    public ResponseWrapper<CommentResponse> getCommentDetails(int commentID) {
        Comment comment = commentRepository.findByCommentID(commentID);
        if (comment == null) {
            return new ResponseWrapper<>(new ErrorResponse("Comment does not exist"));
        }
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentID(comment.getCommentID());
        commentResponse.setCommentBody(comment.getCommentBody());

        User commentCreator = comment.getUser();
        if (commentCreator != null) {
            CommentCreatorResponse creatorResponse = new CommentCreatorResponse();
            creatorResponse.setUserID(commentCreator.getUserID());
            creatorResponse.setName(commentCreator.getName());
            commentResponse.setCommentCreator(creatorResponse);
        }

        return new ResponseWrapper<>(commentResponse);
    }

    public String createComment(CommentRequest commentRequest) {
        User user = userRepository.findByUserID(commentRequest.getUserID());
        if (user == null) {
            return "User does not exist";
        }
        Post post = postRepository.findByPostID(commentRequest.getPostID());
        if (post == null) {
            return "Post does not exist";
        }
        Comment comment = new Comment();
        comment.setCommentBody(commentRequest.getCommentBody());
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        return "Comment created successfully";
    }

    public List<PostResponse> getUserFeed() {
        List<Post> posts = postRepository.findAllByOrderByDateDescPostIDDesc();
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            PostResponse postResponse = new PostResponse();
            postResponse.setPostID(post.getPostID());
            postResponse.setPostBody(post.getPostBody());
            postResponse.setDate(post.getDate());
            List<CommentResponse> commentResponses = new ArrayList<>();
            for (Comment comment : post.getComments()) {
                CommentResponse commentResponse = new CommentResponse();
                commentResponse.setCommentID(comment.getCommentID());
                commentResponse.setCommentBody(comment.getCommentBody());
                //commentResponse.setDate(comment.getDate());
                User commentCreator = comment.getUser();
                if (commentCreator != null) {
                    CommentCreatorResponse creatorResponse = new CommentCreatorResponse();
                    creatorResponse.setUserID(commentCreator.getUserID());
                    creatorResponse.setName(commentCreator.getName());
                    commentResponse.setCommentCreator(creatorResponse);
                }
                commentResponses.add(commentResponse);
            }
            postResponse.setComments(commentResponses);

            postResponses.add(postResponse);
        }
        return postResponses;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            userResponse.setName(user.getName());
            userResponse.setUserID(user.getUserID());
            userResponse.setEmail(user.getEmail());
            userResponses.add(userResponse);
        }
        return userResponses;
    }
}