package com.example.info306;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_Name = "PokemonBdd.db";
    private static final int DATABASE_VERSION = 9;

    public DatabaseManager(Context context){
        super(context, DATABASE_Name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "create table T_Pokemon ("
                      + "   idPokemon integer primary key autoincrement,"
                      + "   name text not null,"
                      + "   type text not null,"
                      + "   estCapture int not null"
                      + ")";
        db.execSQL(strSql);
        db.execSQL(insertPokemonBis("Pikachu", "Electrique", 0));
        db.execSQL(insertPokemonBis("Dracaufeu", "Feu",0));
        db.execSQL(insertPokemonBis("Tortank", "Eau", 0));
        db.execSQL(insertPokemonBis("Torterra", "Plante",0));
        db.execSQL(strSql);
        Log.i("DATABASE", "onCreate invoked");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strSql = "drop table T_Pokemon";
        db.execSQL(strSql);
        strSql = "create table T_Pokemon ("
                + "   idPokemon integer primary key autoincrement,"
                + "   name text not null,"
                + "   type text not null,"
                + "   estCapture int not null"
                + ")";
        db.execSQL(strSql);
        db.execSQL(insertPokemonBis("Pikachu", "Electrique", 0));
        db.execSQL(insertPokemonBis("Dracaufeu", "Feu",0));
        db.execSQL(insertPokemonBis("Tortank", "Eau", 0));
        db.execSQL(insertPokemonBis("Torterra", "Plante",0));
        Log.i("DATABASE", "onUpgrade invoked");
    }

    public void insertPokemon(String name, String type, int estCapture){
        name = name.replace("'","''");
        String strSql = "insert into T_Pokemon (name, type, estCapture) values ('" + name + "', '" + type + "'," + estCapture + ")";
        this.getWritableDatabase().execSQL( strSql );
        Log.i("DATABASE", "insert invoked");
    }
    public String insertPokemonBis(String name, String type, int estCapture){
        name = name.replace("'","''");
        String strSql = "insert into T_Pokemon (name, type, estCapture) values ('" + name + "', '" + type + "'," + estCapture + ")";
        Log.i("DATABASE", "insert invoked");
        return strSql;
    }
    public List<PokemonData> readPokemons(){
        List<PokemonData> pokemons = new ArrayList<>();

        String strSql = "Select * from T_Pokemon";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            PokemonData  pokemondata = new PokemonData(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3) );
            pokemons.add(pokemondata);
            cursor.moveToNext();
        }
        cursor.close();

        return pokemons ;
    }
    public List<PokemonData> readPokemonsAttrapable(){
        List<PokemonData> pokemons = new ArrayList<>();
        String strSql = "Select * from T_Pokemon where estCapture!=1";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            PokemonData  pokemondata = new PokemonData(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3) );
            pokemons.add(pokemondata);
            cursor.moveToNext();
        }
        cursor.close();

        return pokemons ;
    }
    public void setCapturable(){
        String strSql = "UPDATE T_Pokemon SET estCapture = 0";
        this.getWritableDatabase().execSQL( strSql );
    }
    public void setEtatOn(String name){
        name = name.replace("'","''");
        String strSql = "UPDATE T_Pokemon SET estCapture = 1 WHERE name = '"+name+"'" ;
        this.getWritableDatabase().execSQL( strSql );
    }
}
