package com.vojtechruzicka.javafxweaverexample.exception;

import com.vojtechruzicka.javafxweaverexample.FrontClientController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolationException;


@Aspect
@Component
public class ExceptionHandler {
    private final FrontClientController controller;

    @Autowired
    public ExceptionHandler(FrontClientController controller) {
        this.controller = controller;
    }

    @Pointcut("execution(* com.vojtechruzicka.javafxweaverexample.service..*.*(..))")
    public void onlyServiceClasses() {
    }

    @Around("onlyServiceClasses()")
    public Object intercept(ProceedingJoinPoint thisJoinPoint) {
        Object obj = null;
        try {
            obj = thisJoinPoint.proceed();
        } catch (ConstraintViolationException e) {
            controller.getError().setText(String.valueOf(e.getConstraintViolations()));
        } catch (TransactionSystemException e) {
            controller.getError().setText(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            controller.getError().setText(e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }
}