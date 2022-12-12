package com.example.info306;

public class PokemonData {

    private int idPokemon;
    private String name;
    private String type;
    private int estCapture;

    public PokemonData(int idPokemon, String name, String type, int estCapture){
        this.setIdPokemon(idPokemon);
        this.setName(name);
        this.setType(type);
        this.setEstCapture(estCapture);
    }

    public int getIdPokemon() {
        return idPokemon;
    }
    public void setIdPokemon(int idPokemon) {
        this.idPokemon = idPokemon;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getIdString(){
        return  idPokemon +"" ;
    }
    public void setEstCapture(int estCapture){
        this.estCapture = estCapture;
    }
    public int getEstCapture(){
        return estCapture;
    }
    public String toString(){
        return idPokemon + " " + name + " " + type ;
    }
}
