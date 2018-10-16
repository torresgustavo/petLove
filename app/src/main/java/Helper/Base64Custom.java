package Helper;

import android.util.Base64;

public class Base64Custom {

    public static String codifiedBase64(String text){
        return Base64.encodeToString(text.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String DecodifiedBase64(String textDecodified){
        return new String(Base64.decode(textDecodified, Base64.DEFAULT));
    }
}
