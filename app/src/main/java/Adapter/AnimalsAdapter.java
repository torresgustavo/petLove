package Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gustavo.petlov.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Entities.Animals;

import static com.example.gustavo.petlov.R.layout.list_animals;

public class AnimalsAdapter extends ArrayAdapter<Animals> {

    private ArrayList<Animals> pet;
    private Context context;



    public AnimalsAdapter(Context c, ArrayList<Animals> objects) {
        super(c, 0,objects);
        this.context = c;
        this.pet = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (pet != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(list_animals, parent, false);

            TextView txtName = view.findViewById(R.id.txb_PetName);
            TextView txtAge = view.findViewById(R.id.txb_PetAge);
            TextView txtWeight = view.findViewById(R.id.txb_PetWeight);
            TextView txtRace = view.findViewById(R.id.txb_PetRace);
            ImageView imgPet = view.findViewById(R.id.img_Pet);

            Animals animals  = pet.get(position);


            txtName.append(animals.getName());
            txtAge.append(animals.getAge());
            txtRace.append(animals.getRace());
            txtWeight.append(animals.getWeight());
            Picasso.get().load(Uri.parse(animals.getPhotoPerfil())).into(imgPet);
            imgPet.setMaxWidth(100);
            imgPet.setMaxHeight(100);

        }
        return view;
    }
}
