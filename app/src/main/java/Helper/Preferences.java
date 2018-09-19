package Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private Context context;
    private SharedPreferences preferences;
    private String name_archive = ("PetLov.Preferences");
    private int mode = 0;
    private SharedPreferences.Editor editor;

    private final String key_identifier = "identifiedUserLogged";
    private final String key_name = "nameUserLogged";

    public Preferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(name_archive, mode);

        editor = preferences.edit();
    }

    public void saveUserPreferences(String identifierUser, String nameUser){
        editor.putString(key_identifier, identifierUser);
        editor.putString(key_name, nameUser);
        editor.commit();
    }

    public String getIdentifier (){
        return preferences.getString(key_identifier, null);
    }

    public String getName (){
        return preferences.getString(key_name, null);
    }
}
