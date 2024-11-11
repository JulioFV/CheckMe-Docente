package com.itsoeh.checkmedocente.volley;

    public interface API {
        public String URL ="http://192.168.1.69/ws/";


        //Comienza el CRUD para Grupos
        public String LISTARTUT=URL + "ApiT.php?api=listarAlumn";
        public String AGREGARTUT=URL + "ApiT.php?api=addTut";
        public String ELIMINARTUT=URL + "ApiT.php?api=remTut";
        public String LISTARGPO = URL + "ApiG.php?api=listar";
        public String AGREGARGPO = "";
        public String ELIMINARGPO=URL + "";
        public String EDITARGPO=URL + "";
        public String BUSCARDOC = URL + "ApiD.php?api=buscarDoc";

    }

