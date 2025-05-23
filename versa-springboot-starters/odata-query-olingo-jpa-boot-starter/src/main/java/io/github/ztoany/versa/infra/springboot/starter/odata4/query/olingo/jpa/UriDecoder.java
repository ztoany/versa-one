package io.github.ztoany.versa.infra.springboot.starter.odata4.query.olingo.jpa;

import org.apache.olingo.commons.core.Decoder;
import org.apache.olingo.server.api.uri.queryoption.QueryOption;
import org.apache.olingo.server.core.uri.parser.UriParserSyntaxException;
import org.apache.olingo.server.core.uri.queryoption.CustomQueryOptionImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UriDecoder {

    private static final String ACCEPT_FORM_ENCODING = "odata-accept-forms-encoding";
    private static boolean formEncoding = false;

    public static boolean isFormEncoding() {
        return formEncoding;
    }

    /** Splits the path string at '/' characters and percent-decodes the resulting path segments. */
    protected static List<String> splitAndDecodePath(final String path) throws UriParserSyntaxException {
        List<String> pathSegmentsDecoded = new ArrayList<>();
        for (final String segment : split(path, '/')) {
            pathSegmentsDecoded.add(decode(segment));
        }
        return pathSegmentsDecoded;
    }

    /**
     * Splits the query-option string at '&' characters, the resulting parts at '=' characters,
     * and separately percent-decodes names and values of the resulting name-value pairs.
     * If there is no '=' character in an option, the whole option is considered as name.
     */
    public static List<QueryOption> splitAndDecodeOptions(final String queryOptionString)
            throws UriParserSyntaxException {
        List<QueryOption> queryOptions = new ArrayList<>();
        formEncoding = false;
        for (final String option : split(queryOptionString, '&')) {
            final int pos = option.indexOf('=');
            final String name = pos >= 0 ? option.substring(0, pos)  : option;
            final String text = pos >= 0 ? option.substring(pos + 1) : "";
            //OLINGO-846 We trim the query option text to be more lenient to wrong uri constructors
            if(ACCEPT_FORM_ENCODING.equals(name)){
                formEncoding = Boolean.parseBoolean(text);
            }
            queryOptions.add(new CustomQueryOptionImpl()
                    .setName(decode(name).trim())
                    .setText(decode(text).trim()));
        }
        return queryOptions;
    }

    /**
     * Splits the input string at the given character.
     * @param input string to split
     * @param c character at which to split
     * @return list of elements (can be empty)
     */
    private static List<String> split(final String input, final char c) {
        List<String> list = new LinkedList<>();

        int start = 0;
        int end;
        while ((end = input.indexOf(c, start)) >= 0) {
            list.add(input.substring(start, end));
            start = end + 1;
        }

        list.add(input.substring(start));

        return list;
    }

    public static String decode(final String encoded) throws UriParserSyntaxException {
        try {
            return Decoder.decode(encoded);
        } catch (final IllegalArgumentException e) {
            throw new UriParserSyntaxException("Wrong percent encoding!", e, UriParserSyntaxException.MessageKeys.SYNTAX);
        }
    }
}
