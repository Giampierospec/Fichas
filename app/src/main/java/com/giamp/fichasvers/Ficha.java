package com.giamp.fichasvers;




    public class Ficha {
        private int id;
        private String preg;
        private String res;
        private int idCat;

        public Ficha(){

        }
        public Ficha(int id, String preg, String res,int idCat){
            this.id = id;
            this.preg = preg;
            this.res = res;
            this.idCat = idCat;
        }
        public Ficha(String preg, String res,int idCat){
            this.preg = preg;
            this.res = res;
            this.idCat = idCat;

        }
        public int getId(){
            return this.id;
        }
        public void setId(int id){
            this.id = id;
        }
        public  String getPreg(){
            return this.preg;
        }
        public void setPreg(String preg){
            this.preg = preg;

        }
        public String getRes(){
            return this.res;
        }
        public void setRes(String res){
            this.res = res;

        }
        public int getIdCat(){
            return this.idCat;
        }
        public void setIdCat(int idCat){
            this.idCat = idCat;
        }

    }

