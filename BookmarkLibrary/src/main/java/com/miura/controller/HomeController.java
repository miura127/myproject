package com.miura.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miura.domain.model.Bookmark;
import com.miura.domain.model.BookmarkForm;
import com.miura.domain.model.SignupForm;
import com.miura.domain.model.User;
import com.miura.domain.model.mapstruct.BookmarkTranslator;
import com.miura.domain.model.mapstruct.UserTranslator;
import com.miura.domain.service.BookmarkService;
import com.miura.domain.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	@Autowired
	BookmarkService bookmarkService;

	@Autowired
	UserService userService;

	@Autowired
	UserTranslator userTranslator;

	@Autowired
	BookmarkTranslator bookmarkTranslator;

	//セレクトボックスの選択肢
	public Map<String, String> getCategorys() {
		Map<String, String> categoryMap = new LinkedHashMap<String, String>();
		categoryMap.put("key1", "SpringBoot");
		categoryMap.put("key2", "Mybatis");
		categoryMap.put("key3", "Git");
		categoryMap.put("key4", "Bootstrap");
		categoryMap.put("key5", "eclipse");
		categoryMap.put("key6", "VSCode");
		categoryMap.put("key7", "SQL");
		categoryMap.put("key8", "その他");
		return categoryMap;
	}

	@GetMapping("/home")
	public String getHome(Model model) {
		model.addAttribute("contents", "bookmarkList::bookmarkList_contents");
		model.addAttribute("selectCategorys", getCategorys());
		List<Bookmark> bookmarkList = bookmarkService.selectAll();
		model.addAttribute("bookmarkList", bookmarkList);

		int count = bookmarkService.count();
		model.addAttribute("bookmarkListCount", count);

		return "homeLayout";
	}

	@GetMapping("/bookmarkList")
	public String getBookmarkList(Model model) {
		return getHome(model);
	}

	@PostMapping("/bookmarkSelectByCategory")
	public String postBookmarkSelectByCategory(@RequestParam("category") String category , Model model) {
		//debug
		log.debug("category:" + category);

		model.addAttribute("contents", "bookmarkList::bookmarkList_contents");
		model.addAttribute("selectCategorys", getCategorys());
		List<Bookmark> bookmarkListByCategory = bookmarkService.selectByCategory(category);
		model.addAttribute("bookmarkList", bookmarkListByCategory);

		int count = bookmarkService.countByCategory(category);
		model.addAttribute("bookmarkListCount", count);

		return "homeLayout";
	}

	@PostMapping("/bookmarkSelectByKeyword")
	public String postBookmarkSelectByKeyword(@RequestParam("keyword") String keyword, Model model) {
		//debug
		log.debug("keyword:" + keyword);

		model.addAttribute("contents", "bookmarkList::bookmarkList_contents");
		model.addAttribute("selectCategorys", getCategorys());
		List<Bookmark> bookmarkListByKeyword = bookmarkService.selectByKeyword(keyword);
		model.addAttribute("bookmarkList", bookmarkListByKeyword);

		int count = bookmarkService.countByKeyword(keyword);
		model.addAttribute("bookmarkListCount", count);

		return "homeLayout";
	}

	@GetMapping("/bookmarkInsert")
	public String getBookmarkInsert(@ModelAttribute BookmarkForm bookmarkForm, Model model) {
		model.addAttribute("contents", "bookmarkInsert::bookmarkInsert_contents");
		model.addAttribute("selectCategorys", getCategorys());
		return "homeLayout";
	}

	@PostMapping("/bookmarkInsert")
	public String postBookmarkInsert(@ModelAttribute @Validated BookmarkForm bookmarkForm,
		BindingResult bindingResult,
		Model model,
		@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {

		if(bindingResult.hasErrors()) {
			return getBookmarkInsert(bookmarkForm, model);
		}

		/*Bookmark bookmark = new Bookmark();
		bookmark.setCategory(bookmarkForm.getCategory());
		bookmark.setUrl(bookmarkForm.getUrl());
		bookmark.setComment(bookmarkForm.getComment());*/

		//MapStruct
		Bookmark bookmark = bookmarkTranslator.toBookmarkEntity(bookmarkForm);

		//ログインユーザー情報を取得
		bookmark.setRegisterUser(user.getUsername());

		boolean result = bookmarkService.insert(bookmark);

		if(result == true) {
			log.debug("ブックマーク登録成功");
		}else {
			log.debug("ブックマーク登録失敗");
		}

		return getHome(model);
	}


	@GetMapping("/bookmarkDetail/{id}")
	public String getBookmarkDetail(@ModelAttribute BookmarkForm bookmarkForm,
		Model model,
		@PathVariable("id") int id) {

		//debug
		log.debug("id:" + id);

		model.addAttribute("contents", "bookmarkDetail::bookmarkDetail_contents");
		model.addAttribute("selectCategorys", getCategorys());

		if(id >= 0) {
			Bookmark bookmark = bookmarkService.selectOne(id);

			/*bookmarkForm.setCategory(bookmark.getCategory());
			bookmarkForm.setUrl(bookmark.getUrl());
			bookmarkForm.setComment(bookmark.getComment());*/

			//MapStruct
			bookmarkForm = bookmarkTranslator.toBookmarkForm(bookmark);

			model.addAttribute("bookmark", bookmark);
			model.addAttribute("bookmarkForm", bookmarkForm);
		}
		return "homeLayout";
	}

	@PostMapping(value="/bookmarkDetail", params="update")
	public String postUpdate(@ModelAttribute BookmarkForm bookmarkForm, Model model) {
		/*Bookmark bookmark = new Bookmark();
		bookmark.setId(bookmarkForm.getId());
		bookmark.setCategory(bookmarkForm.getCategory());
		bookmark.setUrl(bookmarkForm.getUrl());
		bookmark.setComment(bookmarkForm.getComment());*/

		//MapStruct
		Bookmark bookmark = bookmarkTranslator.toBookmarkEntity(bookmarkForm);

		boolean result = bookmarkService.updateOne(bookmark);

		if(result == true) {
			log.debug("更新成功");
		}else {
			log.debug("更新失敗");
		}

		return getHome(model);
	}

	@PostMapping(value="/bookmarkDetail", params="delete")
	public String postDelete(@ModelAttribute BookmarkForm bookmarkForm, Model model) {

		boolean result = bookmarkService.deleteOne(bookmarkForm.getId());

		if(result = true) {
			log.debug("削除成功");
		}else {
			log.debug("削除失敗");
		}
		return getHome(model);
	}

	@GetMapping("/userList")
	public String getUserList(Model model) {
		model.addAttribute("contents", "userList::userList_contents");
		List<User> userList = userService.selectAll();
		model.addAttribute("userList", userList);
		return "homeLayout";
	}

	@GetMapping("/userDetail/{id}")
	public String getUserDetail(@ModelAttribute SignupForm form,
		Model model,
		@PathVariable("id") String userId) {
			model.addAttribute("contents", "userDetail::userDetail_contents");
			if(userId != null && userId.length() > 0) {
				User user = userService.selectOne(userId);

				/*form.setUserName(user.getUserName());
				form.setPassword(user.getPassword());*/

				//MapStruct
				form = userTranslator.toSignupForm(user);

				model.addAttribute("signupForm", form);
			}
			return "homeLayout";
	}

	@PostMapping(value="/userDetail",params="update")
	public String postUserDetailUpdate(@ModelAttribute SignupForm form, Model model) {
		/*User user = new User();
		user.setUserName(form.getUserName());
		user.setPassword(form.getPassword());*/

		//MapStruct
		User user = userTranslator.toUserEntity(form);

		boolean result = userService.updateOne(user);
		if(result ==true) {
			log.debug("ユーザー更新成功");
		}else {
			log.debug("ユーザー更新失敗");
		}
		return getUserList(model);
	}

	@PostMapping(value="/userDetail", params="delete")
	public String postUserDetailDelete(@ModelAttribute SignupForm form, Model model) {
		boolean result = userService.deleteOne(form.getUserId());
		if(result == true) {
			log.debug("ユーザー削除成功");
		}else {
			log.debug("ユーザー削除失敗");
		}
		return getUserList(model);
	}


	@GetMapping("/logout")
	public String getLogout() {
		return "redirect:/login";
	}
}
