package com.smilegate.auth.controller;

import com.smilegate.auth.dto.ResultResponse;
import com.smilegate.auth.exceptions.AuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;


@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/entrypoint")
    public EntityResponse<ResultResponse> entrypointException() {
        throw new AuthenticationEntryPointException();
    }

    @GetMapping("/accessdenied")
    public EntityResponse<ResultResponse> accessdeniedException() {
        throw new AccessDeniedException("");
    }

}
