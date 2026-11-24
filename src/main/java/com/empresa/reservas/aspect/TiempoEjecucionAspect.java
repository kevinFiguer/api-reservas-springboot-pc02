package com.empresa.reservas.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TiempoEjecucionAspect {

    @Around("execution(* com.empresa.reservas.service.ReservaService.*(..))")
    public Object medirTiempo(ProceedingJoinPoint joinPoint) throws Throwable {
        long inicio = System.currentTimeMillis();

        Object resultado = joinPoint.proceed();

        long tiempoTotal = System.currentTimeMillis() - inicio;
        String metodo = joinPoint.getSignature().getName();

        System.out.println("Método: " + metodo);
        System.out.println("Tiempo: " + tiempoTotal + " ms");

        return resultado;
    }
}