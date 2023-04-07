package com.taskviewer.api.model;

import java.util.List;
import java.util.Optional;

public interface Comments {

  Long add(Comment comment);

  void delete(Long id);

  void update(Comment comment);

  Optional<Comment> byId(Long id);

  List<Comment> byUser(Long user);

  List<Comment> byTask(Long task);

  List<Comment> iterate(Long user, Long task);
}
