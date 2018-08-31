package com.kong.service;


import java.util.Locale;

/**
 * 国际化接口
 *
 * @author Aaric, created on 2018-05-17T12:55.
 * @since 1.1.1-SNAPSHOT
 */
public interface I18nMessageLocaleSource {


    /**
     * 获得国际化字符串
     *
     * @param code i18n编码
     * @return
     */
    String getMessage(String code);
    /**
     * 获得国际化字符串
     *
     * @param code i18n编码
     * @param code i18n语言环境
     * @return
     */
    String getMessage(String code, Locale locale);
}
