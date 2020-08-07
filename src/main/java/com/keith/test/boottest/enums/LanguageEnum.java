package com.keith.test.boottest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LanguageEnum {
    zh,
    en,
    other;

    public static LanguageEnum toLanguageEnum(String language) {
        try {
            return valueOf(language.toLowerCase());
        } catch (Exception var2) {
            return other;
        }
    }

    public static boolean contains(String lang) {
        LanguageEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            LanguageEnum langEnum = var1[var3];
            if (langEnum.name().equals(lang)) {
                return true;
            }
        }

        return false;
    }
}
