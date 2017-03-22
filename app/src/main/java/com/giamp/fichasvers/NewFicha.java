package com.giamp.fichasvers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewFicha extends AppCompatActivity {
    Spinner sp1;
    BDHelper db;
    EditText  ed2, ed3;
    Button btn1;
    ImageButton imBtn1;
    ArrayList<String> mat;
    List<Categoria> mt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ficha);
        ed2 = (EditText) (findViewById(R.id.preg));
        ed3 = (EditText) (findViewById(R.id.res));
        btn1 = (Button)(findViewById(R.id.grd));
        sp1 = (Spinner) (findViewById(R.id.cat_select));
        imBtn1 = (ImageButton)(findViewById(R.id.temp));
        db = new BDHelper(this);

            AlertDialog.Builder blder = new AlertDialog.Builder(NewFicha.this);
                blder.setMessage("Si no te gustan todas las categorias que ves en la lista puedes agregar nuevas dandole al signo de +")
                        .setTitle("Informacion").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
        blder.setIcon(R.mipmap.index_cards);
        blder.create();
        blder.show();

            mat = new ArrayList<String>();
            mt = db.getAllCategorias();
            for (Categoria cat : mt) {
                mat.add(cat.getCat());
            }
            ArrayAdapter<String> spAd = new ArrayAdapter<String>(NewFicha.this, android.R.layout.simple_spinner_dropdown_item, mat);
            spAd.notifyDataSetChanged();
            sp1.setAdapter(spAd);



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
                        imBtn1.setEnabled(false);
                        imBtn1.setImageResource(R.drawable.plus_grey);

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
                    imBtn1.setEnabled(false);
                    imBtn1.setImageResource(R.drawable.plus_grey);
                    String preg = ed2.getText().toString().toLowerCase();
                    String ans = ed3.getText().toString().toLowerCase();
                    String ct = sp1.getSelectedItem().toString().toLowerCase();

                    if((!preg.matches("")&& !ans.matches(""))||(!preg.isEmpty()&&!ans.isEmpty())){
                        if(!db.questionExists(preg)){

                            Categoria c =  db.getCat(ct);
                            db.addFicha(new Ficha(preg,ans,c.getId()));
                            Toast.makeText(getApplicationContext(),"Guardando los datos, Regresando a vista principal",Toast.LENGTH_SHORT).show();
                            final Intent intl = new Intent(NewFicha.this,SelectFichas.class);
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
                            Toast.makeText(getApplicationContext(),"La pregunta ya existe",Toast.LENGTH_SHORT).show();
                            btn1.setEnabled(true);
                            btn1.setBackgroundResource(android.R.drawable.btn_default);
                            imBtn1.setEnabled(true);
                            imBtn1.setImageResource(R.drawable.plus);
                            ed2.setText(null);
                            ed3.setText(null);

                        }

                    }
                    else{
                        btn1.setEnabled(true);
                        btn1.setBackgroundResource(android.R.drawable.btn_default);
                        imBtn1.setEnabled(true);
                        imBtn1.setImageResource(R.drawable.plus);
                        Toast.makeText(getApplicationContext(),"No ha introducido nada intente denuevo",Toast.LENGTH_SHORT).show();

                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        imBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewFicha.this,"Transfiriendo a otra plantilla",Toast.LENGTH_SHORT).show();
                btn1.setEnabled(false);
                btn1.setBackgroundColor(Color.LTGRAY);
                imBtn1.setEnabled(false);
                imBtn1.setImageResource(R.drawable.plus_grey);
                final Intent i = new Intent(NewFicha.this,NewFichaEdit.class);
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(i);
                        btn1.setEnabled(true);
                        btn1.setBackgroundResource(android.R.drawable.btn_default);
                        imBtn1.setEnabled(true);
                        imBtn1.setImageResource(R.drawable.plus);
                        finish();

                    }
                }, 2000);
            }
        });
    }







}



