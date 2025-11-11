package com.railway.helloworld;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;

@SpringBootApplication
@RestController
@Slf4j
@RequestMapping("/v1/public")
public class HelloWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
	}

	@GetMapping()
    public String hello() {
      return String.format("Hola Jhona Java Spring Boot!");
    }

    @PostMapping(value = "/webhooks/google-wallet")
    public void googleWallet(@RequestBody WalletWebHookEvent request,
            @RequestHeader(value = "User-Agent") String userAgent,
            HttpServletRequest servletRequest) {

        log.info("***********************");
        log.info("user-agent : {}", userAgent);
        log.info("signedMessage : {}", request.getSignedMessage());

        if (!userAgent.contains("Googlebot")) {
            log.error("Google-webhook Not valid User-Agent={}", userAgent);
        }

        // Log request method and URI
        log.info("Request: {} {}", servletRequest.getMethod(), servletRequest.getRequestURI());

        // Log all headers
        Enumeration<String> headerNames = servletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = servletRequest.getHeader(headerName);
            log.info("Header: {} = {}", headerName, headerValue);
        }

    }

}
