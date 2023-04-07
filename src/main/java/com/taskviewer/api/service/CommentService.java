package com.taskviewer.api.service;

import com.taskviewer.api.model.Comment;
import java.util.List;

public interface CommentService {

  Comment with(Comment comment);

  Comment update(Comment comment);

  Comment byId(Long id);

  List<Comment> iterate(String username);

  List<Comment> iterate(Long task);

  List<Comment> iterate(Long user, Long task);
}
