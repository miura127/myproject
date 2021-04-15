package com.miura.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GrobalControllAdvice {

	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e, Model model) {
		model.addAttribute("error", "内部サーバーエラー（DB）");
		model.addAttribute("message", "DataAccessExceptionが発生しました");
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		return "error";
	}

	@ExceptionHandler(Exception.class)
	public String ExceptionHandler(Exception e, Model model) {
		model.addAttribute("error", "内部サーバーエラー");
		model.addAttribute("message", "Exceptionが発生しました");
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
		return "error";
	}
}
