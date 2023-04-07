package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.Tasks;
import org.springframework.stereotype.Component;

@Component
public class PgTasks implements Tasks {

    @Override
    public Task task(Long id) {
        return null;
    }

    @Override
    public Iterable<Task> iterate(String username) {
        return null;
    }

    @Override
    public Iterable<Task> byEmail(String email) {
        return null;
    }

    @Override
    public Iterable<Task> iterate(int priority) {
        return null;
    }

    @Override
    public Iterable<Task> with(String status) {
        return null;
    }

    @Override
    public Iterable<Task> iterate() {
        return null;
    }
}