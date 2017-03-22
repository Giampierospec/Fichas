package com.giamp.fichasvers;



public class Categoria {
    private int id;
    private String cat;

    public Categoria(){

    }
    public Categoria(int id, String cat){
        this.id = id;
        this.cat = cat;

    }
    public Categoria(String cat){
        this.cat = cat;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getCat(){
        return this.cat;
    }
    public void setCat(String cat){
        this.cat = cat;
    }
}
