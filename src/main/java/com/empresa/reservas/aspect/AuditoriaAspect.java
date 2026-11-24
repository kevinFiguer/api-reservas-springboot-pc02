package com.empresa.reservas.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class AuditoriaAspect {

    @Before("execution(* com.empresa.reservas.service.ReservaService.*(..))")
    public void registrarAuditoria(JoinPoint joinPoint) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuario = (auth != null) ? auth.getName() : "anonimo";
        String metodo = joinPoint.getSignature().getName();
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("[AUDITORIA]");
        System.out.println("Usuario: " + usuario);
        System.out.println("Método: " + metodo);
        System.out.println("Fecha: " + fechaHora);
    }
}