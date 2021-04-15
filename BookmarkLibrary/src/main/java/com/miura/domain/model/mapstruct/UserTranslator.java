package com.miura.domain.model.mapstruct;

import org.mapstruct.Mapper;

import com.miura.domain.model.SignupForm;
import com.miura.domain.model.User;

@Mapper(componentModel = "spring")
public interface UserTranslator {

	//登録フォームをユーザエンティティにマッピング
	User toUserEntity(SignupForm signupForm);

	//ユーザエンティティを登録フォームにマッピング
	SignupForm toSignupForm(User user);
}
