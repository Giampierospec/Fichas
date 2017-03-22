package com.giamp.fichasvers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import java.util.ArrayList;
import java.util.List;

public class SelectFichas extends AppCompatActivity {
    TextView tv;
    ListView lv1;
    Spinner sp1;
    ImageButton btn1;
    List<Categoria> cat;
    List<Ficha> fetchFich;
    ArrayList<Ficha> fi;
    ArrayList<String> mat;
    MyAdapter mAdapter;
    BDHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_fichas);
        tv = (TextView)(findViewById(R.id.txtHeader));
        sp1 = (Spinner)(findViewById(R.id.sp1));
        btn1 = (ImageButton)(findViewById(R.id.rg));
        fi = new ArrayList<Ficha>();

        db = new BDHelper(this);
        if(!db.fichaIsEmpty()&& !db.catIsEmpty()){


            cat = db.getAllCategorias();
            mat = new ArrayList<String>();
            for(Categoria cate: cat){
                mat.add(cate.getCat());
            }
            ArrayAdapter<String> spAd = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,mat);
            sp1.setAdapter(spAd);
            sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selection = sp1.getSelectedItem().toString();
                    lv1 = (ListView)(findViewById(R.id.lv1));
                    fetchFich = db.getFichaByCategoria(selection);
                    mAdapter = new MyAdapter(getApplicationContext(),(ArrayList<Ficha>)fetchFich);
                    lv1.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final int id_ad = mAdapter.getId(position);
                            final String cate = sp1.getSelectedItem().toString();
                            AlertDialog.Builder builder = new AlertDialog.Builder(SelectFichas.this);
                            String[] item = {"Actualizar dato","Eliminar dato","Eliminar Todo"};
                            builder.setTitle("¿Qué desea hacer?").setItems(item, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch(which){
                                        case 0:
                                            showDialog(id_ad,cate);
                                            break;
                                        case 1:
                                            db.delete_Ficha(id_ad);
                                            finish();
                                            Intent i = new  Intent(SelectFichas.this,SelectFichas.class);
                                            startActivity(i);
                                            break;
                                        case 2:
                                            db.deleteTable();
                                            Intent in = new Intent(SelectFichas.this,SelectFichas.class);
                                            finish();
                                            startActivity(in);
                                            break;
                                    }
                                }
                            });
                            builder.setIcon(R.mipmap.index_cards);
                            builder.create();
                            builder.show();

                        }
                    });


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(getApplicationContext(),"No ha seleccionado nada intente denuevo",Toast.LENGTH_SHORT).show();

                }
            });






        }
        else{
            sp1.setVisibility(View.INVISIBLE);
            tv.setText("La base de datos está vacía intente crear una nueva Ficha");
            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        }



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setEnabled(false);
                btn1.setImageResource(R.drawable.plus_grey);
                if(!db.catIsEmpty()){
                    Toast.makeText(getApplicationContext(), "Transfiriendo a crear nueva ficha", Toast.LENGTH_SHORT).show();
                    Handler h = new Handler();
                    final Intent in = new Intent(SelectFichas.this,NewFicha.class);
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn1.setEnabled(true);
                            btn1.setImageResource(R.drawable.plus);

                            startActivity(in);
                            finish();

                        }
                    }, 2000);

                }
                else{
                    Toast.makeText(getApplicationContext(), "Transfiriendo a crear nueva ficha", Toast.LENGTH_SHORT).show();
                    Handler h = new Handler();
                    final Intent in = new Intent(SelectFichas.this,NewFichaEdit.class);
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn1.setEnabled(true);
                            btn1.setImageResource(R.drawable.plus);
                            startActivity(in);
                            finish();

                        }
                    }, 2000);


                }

            }
        });



    }
    public void showDialog(int id,String cat){
        FragmentManager fm = getSupportFragmentManager();
        UpdFragment upd =  UpdFragment.newInstance(id,cat);
        Log.e("id",String.valueOf(id));

        upd.show(fm,"Update Fragment");
    }
}
