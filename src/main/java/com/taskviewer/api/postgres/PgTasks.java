package com.taskviewer.api.postgres;

import com.taskviewer.api.model.Task;
import com.taskviewer.api.model.Tasks;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PgTasks implements Tasks {

    @Override
    public Optional<Task> byId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Task> byUsername(String username) {
        return null;
    }

    @Override
    public List<Task> byEmail(String email) {
        return null;
    }

    @Override
    public List<Task> withPriority(int priority) {
        return null;
    }

    @Override
    public List<Task> withStatus(String status) {
        return null;
    }

    @Override
    public List<Task> all() {
        return null;
    }

    @Override
    public void add(Task task) {

    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void assign(Long id, Long user) {

    }

}