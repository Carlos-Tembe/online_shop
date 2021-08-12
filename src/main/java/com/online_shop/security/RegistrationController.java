package com.online_shop.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.online_shop.models.User;
import com.online_shop.services.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping
	public String register(User user) {
		return "utilizador/register";
	}

	@PostMapping
	public String register(@Valid User user, BindingResult bindResult, Model model) {
		if (bindResult.hasErrors()) {
			return "utilizador/register";
		}
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("passWrong", "As senhas nao combinam!");
			return "utilizador/register";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.salvar(user);
		return "redirect:/login";

	}
}
