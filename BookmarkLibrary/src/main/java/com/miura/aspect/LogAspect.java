package com.miura.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

	@Before("execution(* *..*.*Controller.*(..))")
	public void startLog(JoinPoint jp) {
		log.info("メソッド開始：{}", jp.getSignature());
	}

	@AfterReturning("execution(* *..*.*Controller.*(..))")
	public void endLog(JoinPoint jp) {
		log.info("メソッド終了：{}", jp.getSignature());
	}

	@AfterThrowing(value = "execution(* *..*.*Controller.*(..))", throwing = "e")
	public void endErrorLog(JoinPoint jp, Throwable e) {
		log.error("メソッド異常終了：{} message:{}", jp.getSignature(), e.getMessage());
		e.printStackTrace();
	}
}
