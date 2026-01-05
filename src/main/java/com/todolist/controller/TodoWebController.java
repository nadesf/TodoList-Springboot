package com.todolist.controller;

import com.todolist.dto.TodoRequest;
import com.todolist.model.Todo;
import com.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TodoWebController {

    private final TodoService todoService;

    @GetMapping("/")
    public String index(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        model.addAttribute("todos", todos);
        model.addAttribute("todoRequest", new TodoRequest());
        return "index";
    }

    @PostMapping("/todos")
    public String createTodo(@ModelAttribute TodoRequest request) {
        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            todoService.createTodo(request);
        }
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id) {
        todoService.toggleTodoStatus(id);
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/";
    }
}
