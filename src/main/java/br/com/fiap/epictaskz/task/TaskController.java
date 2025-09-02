package br.com.fiap.epictaskz.task;

import br.com.fiap.epictaskz.user.UserService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final MessageSource messageSource;
    private final UserService userService;

    public TaskController(TaskService taskService, MessageSource messageSource, UserService userService) {
        this.taskService = taskService;
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        var tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/form")
    public String form(Task task){
        return "form";
    }

    @PostMapping("/form")
    public String save(@Valid Task task, BindingResult result, RedirectAttributes redirect){
        if(result.hasErrors()) return "form";
        taskService.save(task);
        var message = messageSource.getMessage("task.create.success", null, LocaleContextHolder.getLocale());
        redirect.addFlashAttribute("message", message);
        return "redirect:/task";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        taskService.delete(id);
        var message = messageSource.getMessage("task.delete.success", null, LocaleContextHolder.getLocale());
        redirect.addFlashAttribute("message", message);
        return "redirect:/task";
    }

    @PutMapping("pick/{id}")
    public String pick(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal ){
        taskService.pick(id, userService.register(principal));
        return "redirect:/task";
    }

    @PutMapping("drop/{id}")
    public String drop(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal ){
        taskService.drop(id, userService.register(principal));
        return "redirect:/task";
    }

    @PutMapping("inc/{id}")
    public String increment(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal ){
        taskService.incrementTaskStatus(id, userService.register(principal));
        return "redirect:/task";
    }

    @PutMapping("dec/{id}")
    public String decrement(@PathVariable Long id, @AuthenticationPrincipal OAuth2User principal ){
        taskService.decrementTaskStatus(id, userService.register(principal));
        return "redirect:/task";
    }

}
