package com.taskviewer.api.service;

import com.taskviewer.api.model.Task;

public interface TaskService {

  Task assign(Long id, Long user);
}
