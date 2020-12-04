package io.github.abradat.ps5news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(allowedHeaders = "*", allowCredentials = "*")
@RequestMapping()
public class CnnNewsController {
}
