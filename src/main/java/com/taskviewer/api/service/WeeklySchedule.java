package com.taskviewer.api.service;

import com.taskviewer.api.model.Task;
import java.util.List;

public interface WeeklySchedule {

  List<Task> tasks(Long user);
}
