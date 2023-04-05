package com.taskviewer.api.model;

public interface Comments {

  Comment comment(Long id);

  Iterable<Comment> iterate(Long user);

  Iterable<Comment> iterate(Long user, Long task);

  Iterable<Comment> with(Long task);
}
