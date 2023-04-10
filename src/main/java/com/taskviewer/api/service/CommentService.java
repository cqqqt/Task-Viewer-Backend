package com.taskviewer.api.service;

import com.taskviewer.api.model.Comment;
import java.util.List;

public interface CommentService {

  Comment with(Comment comment);

  Comment update(Comment comment);

  void delete(Long id);

  Comment byId(Long id);

  List<Comment> iterate(CommentSearchCriteria criteria);
}
