package com.taskviewer.api.model;

import com.taskviewer.api.web.rq.RqComment;

public interface Comments {

  void add(RqComment request);

  Comment comment(Long id);

  Iterable<Comment> byUser(Long user);

  Iterable<Comment> byTask(Long task);

  Iterable<Comment> iterate(Long user, Long task);
}
