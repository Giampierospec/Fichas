package com.giamp.fichasvers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BDHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SISTEMA_FICHAS";
    private static final String TABLE_FICHA = "FICHA";
    private static final String KEY_QUESTION = "pregunta";
    private static final String KEY_ANSWER = "respuesta";
    private static final String KEY_ID = "id";
    private static final String KEY_ID_CAT = "id_cat";
    private static final String KEY_FK_IDCATEGORIA = "id_categoria";
    private static final String TABLE_CATEGORIA = "CATEGORIA";
    private static final String KEY_CATEGORIA = "categoria";

    public BDHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_FICHA+"("+KEY_ID+" INTEGER PRIMARY KEY,"+KEY_QUESTION+" TEXT,"+KEY_ANSWER+" TEXT,"+KEY_FK_IDCATEGORIA+" INTEGER,"+" FOREIGN KEY "+"("+KEY_FK_IDCATEGORIA+")"+" REFERENCES "+TABLE_CATEGORIA+"("+KEY_ID+")"+")");
        db.execSQL("CREATE TABLE "+TABLE_CATEGORIA+"("+KEY_ID_CAT+" INTEGER PRIMARY KEY,"+KEY_CATEGORIA+" TEXT"+")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FICHA);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIA);

        onCreate(db);

    }
    //Agrega un nuevo registro a la tabla
    public void addFicha(Ficha fi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cV = new ContentValues();
        cV.put(KEY_QUESTION,fi.getPreg());
        cV.put(KEY_ANSWER,fi.getRes());
        cV.put(KEY_FK_IDCATEGORIA,fi.getIdCat());
        db.insert(TABLE_FICHA,null,cV);
        db.close();

    }

    //Consigo todos los datos de  la BD
    public List<Ficha> getAllFichas(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_FICHA;
        Cursor cr = db.rawQuery(selectQuery,null);
        List<Ficha> consInfo = new ArrayList<Ficha>();
        cr.moveToFirst();
        while(!cr.isAfterLast()){
            Ficha fi = new Ficha();
            fi.setId(Integer.parseInt(cr.getString(0)));
            fi.setPreg(cr.getString(1));
            fi.setRes(cr.getString(2));
            fi.setIdCat(Integer.parseInt(cr.getString(3)));
            cr.moveToNext();
            consInfo.add(fi);


        }
        return consInfo;
    }
    public Categoria getCat(String cat){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_CATEGORIA+" WHERE "+KEY_CATEGORIA+" =?";
        String[] preg = {cat};
        Cursor cr = db.rawQuery(selectQuery,preg);
        if(cr!= null){
            cr.moveToFirst();
        }
        Categoria ca = new Categoria(Integer.parseInt(cr.getString(0)),cr.getString(1));
        return ca;

    }
    public boolean catExists(String cat){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_CATEGORIA+" WHERE "+KEY_CATEGORIA+" =?";
        String[] preg = {cat};
        Cursor cr = db.rawQuery(selectQuery,preg);

        boolean exists = (cr.getCount() > 0);
        return exists;

    }
    public List<Ficha> getFichaByCategoria(String cat){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_FICHA+" tf INNER JOIN "+TABLE_CATEGORIA+" tc ON tf."+KEY_FK_IDCATEGORIA+" = "+" tc."+KEY_ID_CAT+" WHERE "+KEY_CATEGORIA+" =?";
        Log.e("Query",selectQuery);
        String[] cat_ar= {cat};
        Cursor cr = db.rawQuery(selectQuery,cat_ar);
        List<Ficha> consInfo = new ArrayList<Ficha>();
        cr.moveToFirst();
        while(!cr.isAfterLast()){
            Ficha fi = new Ficha();
            fi.setId(Integer.parseInt(cr.getString(0)));
            fi.setPreg(cr.getString(1));
            fi.setRes(cr.getString(2));
            fi.setIdCat(Integer.parseInt(cr.getString(3)));
            cr.moveToNext();
            consInfo.add(fi);


        }
        return consInfo;
    }


    public List<Categoria> getAllCategorias(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_CATEGORIA;
        Cursor cr = db.rawQuery(selectQuery,null);
        List<Categoria> consInfo = new ArrayList<Categoria>();
        cr.moveToFirst();
        while(!cr.isAfterLast()){
            Categoria cat = new Categoria();
            cat.setId(Integer.parseInt(cr.getString(0)));
            cat.setCat(cr.getString(1));
            cr.moveToNext();
            consInfo.add(cat);


        }
        return consInfo;
    }
    public void addCategoria(Categoria cat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cV = new ContentValues();
        cV.put(KEY_CATEGORIA,cat.getCat());
          db.insert(TABLE_CATEGORIA,null,cV);

        db.close();

    }
    public Categoria getIdCat(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_CATEGORIA+" WHERE "+KEY_ID_CAT+" = (SELECT MAX(id_cat) FROM "+TABLE_CATEGORIA+")";
        Cursor cr = db.rawQuery(selectQuery, null);
        if(cr != null){
            cr.moveToFirst();
        }
        Categoria ca = new Categoria(Integer.parseInt(cr.getString(0)),cr.getString(1));
        return ca;


    }
    // Me permite conseguir los datos de una ficha en especifico por su id
    public Ficha getFicha(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] id_String = {String.valueOf(id)};
        Cursor cr = db.rawQuery("SELECT * FROM "+TABLE_FICHA+" WHERE "+KEY_ID+" =?",id_String);
        if(cr != null){
            cr.moveToFirst();
        }
        Ficha fi = new Ficha(Integer.parseInt(cr.getString(0)),cr.getString(1),cr.getString(2),Integer.parseInt(cr.getString(3)));
        return fi;
    }

    public Categoria getCat(int idCat){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cat_ar = {String.valueOf(idCat)};
        Cursor cr = db.rawQuery("SELECT * FROM "+TABLE_CATEGORIA+" WHERE "+KEY_ID_CAT+" =?",cat_ar);
        if(cr != null){
            cr.moveToFirst();
        }
        Categoria catl = new Categoria(Integer.parseInt(cr.getString(0)),cr.getString(1));
        return catl;
    }
    //Consigo todos los datos que se encuentran en el indice de determinada pregunta
    public boolean questionExists(String quest){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_FICHA+" WHERE "+KEY_QUESTION+" =?";
        String[] preg = {quest};
        Cursor cr = db.rawQuery(selectQuery,preg);

        boolean exists = (cr.getCount() > 0);
        return exists;

    }

    // Consigo la cantidad de registros que posee la Base de datos
    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+TABLE_FICHA;
        Cursor cr = db.rawQuery(selectQuery,null);
        cr.moveToFirst();
        return cr.getCount();

    }
    public void update_Ficha(Ficha fi,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, fi.getPreg());
        values.put(KEY_ANSWER, fi.getRes());
        values.put(KEY_FK_IDCATEGORIA,fi.getIdCat());
        db.update(TABLE_FICHA,values,KEY_ID+"="+id,null);
        db.close();

    }
    public void update_Categoria(Categoria ct,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORIA, ct.getCat());
        db.update(TABLE_CATEGORIA,values,KEY_ID+"="+id,null);
        db.close();

    }

    //Este metodo me permite saber si la base de datos tiene datos o no
    public Boolean fichaIsEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT * FROM "+TABLE_FICHA,null);
        if(cr.moveToFirst()){
            return false;
        }
        else{
            return true;
        }
    }
    public Boolean catIsEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT * FROM "+TABLE_CATEGORIA,null);
        if(cr.moveToFirst()){
            return false;
        }
        else{
            return true;
        }
    }
    //Me permite borrar una ficha por el id introducido
    public void delete_Ficha(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] id_Event = {String.valueOf(id)};
        db.delete(TABLE_FICHA,KEY_ID+" =?",id_Event);
        db.close();
    }
    //Me permite borrar todos los datos de la unica tabla que existe
    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_FICHA);
        db.execSQL("DELETE FROM "+TABLE_CATEGORIA);
        db.close();


    }
}
