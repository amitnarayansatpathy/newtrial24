package com.example.newtrial24;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByDateDesc();
    List<Post> findAllByUserOrderByDateDesc(User user);

    Post findByPostID(int postID);

    List<Post> findAllByOrderByDateDescPostIDDesc();
}
