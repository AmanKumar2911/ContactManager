package com.first.contactmanager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.first.contactmanager.Entites.User;
import com.first.contactmanager.Repositry.UserRepositry;

import Helper.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserRepositry userRepositry;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home - Smart Contact Manager");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About - Smart Contact Manager");
        return "about";
    }

    @RequestMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("title", "Register - Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }

    // handler for registering user

    @PostMapping("/do_register")
    public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result ,@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model m) {
        try {
            if (!agreement) {
                System.out.println("You have not agreed terms and condition");
                throw new Exception("You have not agreed the terms and conditions");
            }

            if(result.hasErrors()){
                System.out.println("ERROR "+result.toString());
                m.addAttribute("user", user);
                return "signup";
            }

            user.setRole("Role_User");
            user.setEnabled(true);

            System.out.println("Agreement " + agreement);
            System.out.println("User " + user);

            User re = this.userRepositry.save(user);

            m.addAttribute("user", new User());

            // m.setAttribute("message", new Message("Sucessfully Register !!","alert-success"));
            m.addAttribute("message", new Message("Successfully Registered!! ", "alert-success"));

            return "signup";

        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("user", user);
            m.addAttribute("message", new Message("Something went wrong "+e.getMessage(), "alert-danger"));
            return "signup";
        }
        // return "signup";
    }

}
