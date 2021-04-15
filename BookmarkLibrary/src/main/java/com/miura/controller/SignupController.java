package com.miura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.miura.domain.model.SignupForm;
import com.miura.domain.model.User;
import com.miura.domain.model.mapstruct.UserTranslator;
import com.miura.domain.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SignupController {

	@Autowired
	UserService userService;

	@Autowired
	UserTranslator userTranslator;

	//登録ページに遷移
	@GetMapping("/signup")
	public String getSignup(@ModelAttribute SignupForm form) {
		return "signup";
	}

	//登録
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute @Validated SignupForm form,
		BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return getSignup(form);
		}

		User user = userTranslator.toUserEntity(form);
		user.setRole("ROLE_ADMIN");

		boolean result = userService.insert(user);
		if(result == true) {
			log.debug("登録成功");
		}else {
			log.debug("登録失敗");
		}

		return "redirect:/login";
	}
}
