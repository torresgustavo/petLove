package fireBaseConfiguration;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fireBaseConfig {
    private static DatabaseReference refenceFireBase;
    private static FirebaseAuth autentication;

    public static DatabaseReference getFireBase(){
        if (refenceFireBase == null){
            refenceFireBase = FirebaseDatabase.getInstance().getReference();
        }
        return refenceFireBase;
    }

    public static FirebaseAuth getFireBaseAutentication(){
        if (autentication == null){
            autentication = FirebaseAuth.getInstance();
        }
        return autentication;
    }
}
