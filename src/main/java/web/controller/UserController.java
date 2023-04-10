package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import web.model.User;
import web.service.UserService;

@Controller
public class UserController {
    private UserService userService;

    @Autowired(required = true)
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "user",method = RequestMethod.GET)
    public String listUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listUsers", this.userService.listUsers());
        return "user";
    }

    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    public String addUser (@ModelAttribute("user") User user) {
        if (user.getId() == 0) {
            this.userService.addUser(user);
        } else {
            this.userService.updateUser(user);
        }
        return "redirect:/user";
    }

    @RequestMapping("/user/remove")
    public String removeUser (@ModelAttribute("user") int id) {
        this.userService.removeUser(id);
        return "redirect:/user";
    }

    @RequestMapping("/user/get")
    public String getUser (@ModelAttribute("user") int id, Model model) {
        model.addAttribute("user", this.userService.getUserId(id));
        model.addAttribute("listUsers", this.userService.listUsers());
        return "user";
    }

    @RequestMapping("/userdata/{id}")
    public String UserData (@PathVariable("id") int id, Model model) {
        model.addAttribute("user", this.userService.getUserId(id));
        return "user";
    }
}