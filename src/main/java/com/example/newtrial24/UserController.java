package com.example.newtrial24;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        String response = userService.login(loginRequest);
        if (response.startsWith("User does not exist")||response.startsWith("Username/Password Incorrect")) {
            return ResponseEntity.ok(new ErrorResponse(response));
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping(path = "/signup", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> signup(@RequestBody SignupRequest signupRequest) {
        String response = userService.signup(signupRequest);
         if (response.startsWith("Forbidden, Account already exists")) {
            return ResponseEntity.ok(new ErrorResponse(response));
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping(path = "/user", produces = "application/json")
    public ResponseEntity<Object> getUserDetails(@RequestParam("userID") int userID) {
        ResponseWrapper<UserResponse> response = userService.getUserDetails(userID);
        if (response.getError() != null) {
            return ResponseEntity.ok(response.getError());
        } else {
            return ResponseEntity.ok(response.getData());
        }
    }

    @PatchMapping(path = "/post", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> editPost(@RequestBody EditPostRequest request) {
        String response = userService.editPost(request.getPostBody(), request.getPostID());
        if (response.startsWith("Post does not exist")) {
            return ResponseEntity.ok(new ErrorResponse(response));
        } else {
            return ResponseEntity.ok(response);
        }
    }



    @DeleteMapping(path = "/post", produces = "application/json")
    public ResponseEntity<Object> deletePost(@RequestParam("postID") int postID) {
        String response = userService.deletePost(postID);
        if (response.startsWith("Post does not exist")) {
            return ResponseEntity.ok(new ErrorResponse(response));
        } else {
            return ResponseEntity.ok(response);
        }
    }



    @GetMapping(path = "/post", produces = "application/json")
    public ResponseEntity<Object> getPostDetails(@RequestParam("postID") int postID) {
        ResponseWrapper<PostResponse> response = userService.getPostDetails(postID);
        if (response.getError() != null) {
            return ResponseEntity.ok(response.getError());
        } else {
            return ResponseEntity.ok(response.getData());
        }
    }

    @PostMapping(path = "/post", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> createPost(@RequestBody PostRequest postRequest) {
        String response = userService.createPost(postRequest);
        if (response.startsWith("User does not exist")) {
            return ResponseEntity.ok(new ErrorResponse(response));
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @PatchMapping(path = "/comment", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> editComment(@RequestBody CommentEditRequest request) {
        String response = userService.editComment(request.getCommentBody(), request.getCommentID());
         if (response.startsWith("Comment does not exist")) {
            return ResponseEntity.ok(new ErrorResponse(response));
        } else {
            return ResponseEntity.ok(response);
        }
    }



    @DeleteMapping(path = "/comment", produces = "application/json")
    public ResponseEntity<Object> deleteComment(@RequestParam("commentID") int commentID) {
        String response = userService.deleteComment(commentID);
        if (response.startsWith("Comment does not exist")) {
            return ResponseEntity.ok(new ErrorResponse(response));
        } else {
            return ResponseEntity.ok(response);
        }
    }


    @GetMapping(path = "/comment", produces = "application/json")
    public ResponseEntity<Object> getCommentDetails(@RequestParam("commentID") int commentID) {
        ResponseWrapper<CommentResponse> response = userService.getCommentDetails(commentID);
        if (response.getError() != null) {
            return ResponseEntity.ok(response.getError());
        } else {
            return ResponseEntity.ok(response.getData());
        }
    }

    @PostMapping(path = "/comment", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> createComment(@RequestBody CommentRequest commentRequest) {
        String response = userService.createComment(commentRequest);
        if (response.startsWith("User does not exist")||response.startsWith("Post does not exist")) {
            return ResponseEntity.ok(new ErrorResponse(response));
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<Object> getUserFeed() {
        List<PostResponse> response = userService.getUserFeed();

            return ResponseEntity.ok(response);

    }

    @GetMapping(path = "/users", produces = "application/json")
    public ResponseEntity<Object> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();

            return ResponseEntity.ok(response);

    }
}


