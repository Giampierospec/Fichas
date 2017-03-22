package com.giamp.fichasvers;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NewFichaEdit extends AppCompatActivity {
    BDHelper db;
    EditText ed1, ed2, ed3;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ficha_edit);


            ed1 = (EditText) (findViewById(R.id.cat));
            ed2 = (EditText) (findViewById(R.id.preg_ed));
            ed3 = (EditText) (findViewById(R.id.res_ed));
            btn1 = (Button)(findViewById(R.id.grd_ed));
            db = new BDHelper(this);

        ed1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        ed1.clearFocus();
                        ed2.requestFocus();
                        return true;
                    }


                }
                return false;
            }
        });


            ed2.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_ENTER) {
                            ed2.clearFocus();
                            ed3.requestFocus();
                            return true;
                        }


                    }
                    return false;
                }
            });
            ed3.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_ENTER) {
                            btn1.performClick();
                            btn1.setEnabled(false);
                            btn1.setBackgroundColor(Color.LTGRAY);
                            return true;
                        }
                    }
                    return false;
                }


            });
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        btn1.setEnabled(false);
                        btn1.setBackgroundColor(Color.LTGRAY);
                        String preg = ed2.getText().toString().toLowerCase();
                        String ans = ed3.getText().toString().toLowerCase();
                        String cat ="";
                        cat = ed1.getText().toString().toLowerCase();
                        if((!preg.matches("")&& !ans.matches(""))||(!preg.isEmpty()&&!ans.isEmpty())){
                            if(!db.questionExists(preg) && !db.catExists(cat)){


                                db.addCategoria(new Categoria(cat));
                                Categoria c =  db.getIdCat();
                                db.addFicha(new Ficha(preg,ans,c.getId()));
                                Toast.makeText(getApplicationContext(),"Guardando los datos, Regresando a vista principal",Toast.LENGTH_SHORT).show();
                                final Intent intl = new Intent(NewFichaEdit.this,SelectFichas.class);
                                Handler h = new Handler();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(intl);
                                        finish();
                                    }
                                }, 2000);


                            }
                            else{
                                Toast.makeText(getApplicationContext(),"La pregunta o la categoria ya existen",Toast.LENGTH_SHORT).show();
                                btn1.setEnabled(true);
                                btn1.setBackgroundResource(android.R.drawable.btn_default);
                                ed1.setText(null);
                                ed2.setText(null);
                                ed3.setText(null);

                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No ha introducido nada intente denuevo",Toast.LENGTH_SHORT).show();

                        }
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
        }
    }

