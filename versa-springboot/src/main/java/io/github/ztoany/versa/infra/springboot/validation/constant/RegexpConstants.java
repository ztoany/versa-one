package io.github.ztoany.versa.infra.springboot.validation.constant;

public class RegexpConstants {
    public static final String REGULAR_CHAR = "[\\p{L}\\p{N}\\s&&[^\\p{C}]]+";
    public static final String REGULAR_CHAR_NOT_BLANK = "^(?![\\s]*$)" + REGULAR_CHAR + "$";
    public static final String REGULAR_CHAR_OR_BLANK = "^$|" + REGULAR_CHAR;
}
