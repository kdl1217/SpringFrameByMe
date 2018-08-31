package com.kong.service.impl;

import com.kong.service.I18nMessageLocaleSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by Kong on 2018/5/17.
 */
@Service
public class I18nMessageLocaleSourceImpl implements I18nMessageLocaleSource {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, locale);
    }

    @Override
    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }
}
