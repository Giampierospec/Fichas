package com.giamp.fichasvers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class UpdFragment extends DialogFragment {
    EditText ed1,ed2,ed3;
    Button btn1;
    BDHelper bd;
    int id;
    Categoria cat;
    ArrayList<String> cate;
    Ficha fi;
    Context ct;
    String c;
    public static UpdFragment newInstance(int id,String cat){
        UpdFragment frag = new UpdFragment();
        Bundle args = new Bundle();
        args.putInt("id",id);
        args.putString("cat",cat);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().setTitle("Actualizar");
        View rootView = inflater.inflate(R.layout.upd_fragment, container, false);
        return rootView;

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        ct = getContext();
        bd = new BDHelper(ct);


        ed1 = (EditText)(view.findViewById(R.id.upd_cat));

        ArrayAdapter<String> adSp1 = new ArrayAdapter<String>(ct,android.R.layout.simple_spinner_dropdown_item,cate);
        adSp1.notifyDataSetChanged();
        ed2 = (EditText)(view.findViewById(R.id.upd_preg));
        ed3 = (EditText)(view.findViewById(R.id.upd_ans));
        btn1 = (Button)(view.findViewById(R.id.upd_btn));
        id = getArguments().getInt("id");
        c = getArguments().getString("cat");

         fi = bd.getFicha(id);
        ed1.setHint(c);
        ed2.setHint(fi.getPreg());
        ed3.setHint(fi.getRes());

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cr = ed1.getText().toString().toLowerCase();
                String preg = ed2.getText().toString().toLowerCase();
                String ans = ed3.getText().toString().toLowerCase();
                btn1.setEnabled(false);
                btn1.setBackgroundColor(Color.LTGRAY);
                if((!cr.matches("")&&!preg.matches("")&&!ans.matches(""))||(!cr.isEmpty()&&!preg.isEmpty()&&!ans.isEmpty())){
                    if(!bd.catExists(cr)){
                        bd.addCategoria(new Categoria(cr));
                        Categoria cg = bd.getIdCat();
                        bd.update_Ficha(new Ficha(preg,ans,cg.getId()),id);
                        Toast.makeText(getContext(),"Actualizando tabla y regresando a la vista principal",Toast.LENGTH_SHORT).show();
                        final Intent i = new Intent(getActivity(),SelectFichas.class);
                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dismiss();
                                startActivity(i);
                            }
                        }, 2000);



                    }
                    else{
                        Categoria cg = bd.getCat(cr);
                        bd.update_Ficha(new Ficha(preg,ans,cg.getId()),id);
                        Toast.makeText(getContext(),"Actualizando tabla y regresando a la vista principal",Toast.LENGTH_SHORT).show();
                        final Intent i = new Intent(getActivity(),SelectFichas.class);
                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dismiss();
                                startActivity(i);
                            }
                        }, 2000);

                    }


                }
                else{
                    Toast.makeText(getContext(),"Uno o mas campos estan vacios intente denuevo",Toast.LENGTH_SHORT).show();
                    ed1.setText(null);
                    ed2.setText(null);
                    ed2.setText(null);
                    btn1.setEnabled(true);
                    btn1.setBackgroundResource(android.R.drawable.btn_default);

                }



            }
        });

    }

}
