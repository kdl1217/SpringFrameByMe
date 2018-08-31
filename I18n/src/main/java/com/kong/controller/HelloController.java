package com.kong.controller;

import com.kong.service.I18nMessageLocaleSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Test Controller
 *
 * @author Kong, created on 2018-08-31T16:13.
 * @since 1.2.0-SNAPSHOT
 *
 * example : http://127.0.0.1:8080/index/hello?locale=en_US
 */
@RestController
@RequestMapping("/index")
public class HelloController {

    @Autowired
    private I18nMessageLocaleSource i18nMessageLocaleSource ;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String test() {
        return i18nMessageLocaleSource.getMessage("hello") ;
    }
}
