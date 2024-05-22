package rs.raf.student.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Utils {

    public static <Type> Stream<Type> createStream(Iterator<Type> iterator) {
        return StreamSupport.stream(((Iterable<Type>)(() -> iterator)).spliterator(), false);
    }

    public static <Type> List<Type> createList(Iterator<Type> iterator) {
        return createStream(iterator).collect(Collectors.toList());
    }

    public static String encodePassword(String password) {
        return new String(Base64.encodeBase64(DigestUtils.sha256(password)));
    }

}
