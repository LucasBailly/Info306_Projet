package com.example.info306;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Activity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button buttonSearch;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private LinearLayout pokemonLayout;
    private LinearLayout layoutFixe;
    private LinearLayout layoutModifiable;
    private DatabaseManager databaseManager;
    private String type;
    private Spinner spinner ;
    private String textSelected ;
    private String capture = "Capturés" ;
    private String tous = "Tous" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        layoutFixe = (LinearLayout) findViewById(R.id.PokemonLayoutFixed);

        layoutModifiable = (LinearLayout) findViewById(R.id.PokemonLayoutUnfixed);


        databaseManager = new DatabaseManager(this);
//        databaseManager.insertPokemon("Pikachu", "Electrique", 1);
//        databaseManager.insertPokemon("Dracaufeu", "Feu",0);
//        databaseManager.insertPokemon("Tortank", "Eau", 1);
//        databaseManager.insertPokemon("Torterra", "Plante",0);

        tv1 = new TextView(this);
        tv1.setText("N°Pokemon");
        tv2 = new TextView(this);
        tv2.setText("Nom");
        tv3 = new TextView(this);
        tv3.setText("Type");

        pokemonLayout = new LinearLayout(this);

        pokemonLayout.addView(tv1,400,200);
        pokemonLayout.addView(tv2,400,200);
        pokemonLayout.addView(tv3,400,200);

        layoutFixe.addView(pokemonLayout);

        List<PokemonData> pokemons = databaseManager.readPokemons();
        for(PokemonData pokemon : pokemons){

                tv1 = new TextView(this);
                tv1.setText("\t" + pokemon.getIdString());
                tv1.setTextColor(Color.parseColor("#D69C38"));

                tv2 = new TextView(this);
                tv2.setText(pokemon.getName());

                type = pokemon.getType();
                tv3 = new TextView(this);
                tv3.setText(type);
                switch (type) {
                    case "Electrique":
                        tv3.setTextColor(Color.parseColor("#D4D300"));
                        break;
                    case "Plante":
                        tv3.setTextColor(Color.parseColor("#0AFF12"));
                        break;
                    case "Feu":
                        tv3.setTextColor(Color.parseColor("#FF0000"));
                        break;
                    case "Eau":
                        tv3.setTextColor(Color.parseColor("#0099FF"));
                        break;
                }

                pokemonLayout = new LinearLayout(this);

                pokemonLayout.addView(tv1,400, 200);
                pokemonLayout.addView(tv2,400, 200);
                pokemonLayout.addView(tv3,400, 200);

                layoutModifiable.addView(pokemonLayout);
        }


        databaseManager.close();

        buttonSearch = (Button) findViewById(R.id.search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Fonction RemoveAll()
                layoutModifiable.removeAllViews();

                //Fonction AfficherPar(string)
                if(textSelected.equals("Tous")){
                    for (PokemonData pokemon : pokemons) {
                        newTextView();

                        tv1.setText("\t" + pokemon.getIdString());
                        tv1.setTextColor(Color.parseColor("#D69C38"));

                        tv2.setText(pokemon.getName());

                        type = pokemon.getType();
                        tv3.setText(type);
                        switch (type) {
                            case "Electrique":
                                tv3.setTextColor(Color.parseColor("#D4D300"));
                                break;
                            case "Plante":
                                tv3.setTextColor(Color.parseColor("#0AFF12"));
                                break;
                            case "Feu":
                                tv3.setTextColor(Color.parseColor("#FF0000"));
                                break;
                            case "Eau":
                                tv3.setTextColor(Color.parseColor("#0099FF"));
                                break;
                        }

                        pokemonLayout.addView(tv1, 400, 200);
                        pokemonLayout.addView(tv2, 400, 200);
                        pokemonLayout.addView(tv3, 400, 200);

                        layoutModifiable.addView(pokemonLayout);
                    }
                }

                else if (textSelected.equals("Capturés")) {
                    for (PokemonData pokemon : pokemons) {
                        if (pokemon.getEstCapture() == 1) {

                            newTextView();

                            tv1.setText("\t" + pokemon.getIdString());
                            tv1.setTextColor(Color.parseColor("#D69C38"));

                            tv2.setText(pokemon.getName());

                            type = pokemon.getType();
                            tv3.setText(type);
                            switch (type) {
                                case "Electrique":
                                    tv3.setTextColor(Color.parseColor("#D4D300"));
                                    break;
                                case "Plante":
                                    tv3.setTextColor(Color.parseColor("#0AFF12"));
                                    break;
                                case "Feu":
                                    tv3.setTextColor(Color.parseColor("#FF0000"));
                                    break;
                                case "Eau":
                                    tv3.setTextColor(Color.parseColor("#0099FF"));
                                    break;
                            }

                            pokemonLayout.addView(tv1, 400, 200);
                            pokemonLayout.addView(tv2, 400, 200);
                            pokemonLayout.addView(tv3, 400, 200);

                            layoutModifiable.addView(pokemonLayout);
                        }
                    }
                }
            }
        });
    }
    public void openActivity3(){
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "Accueil", Toast.LENGTH_SHORT).show();
                openMainActivity();
                return true;
            case R.id.item2:
                Toast.makeText(this, "GPS", Toast.LENGTH_SHORT).show();
                openActivity3();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newTextView () {
        tv1 = new TextView(this);
        tv2 = new TextView(this);
        tv3 = new TextView(this);
        pokemonLayout = new LinearLayout(this);
    }

    public void openActivity1(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i( "CycleDeVie",  "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i( "CycleDeVie",  "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i( "CycleDeVie",  "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i( "CycleDeVie",  "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i( "CycleDeVie",  "onDestroy");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        textSelected = "" + parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), textSelected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}