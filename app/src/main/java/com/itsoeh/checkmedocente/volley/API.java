package com.itsoeh.checkmedocente.volley;

    public interface API {
        public String URL ="https://slateblue-squid-754504.hostingersite.com/";


        //Comienza el CRUD para Grupos
        public String LISTARTUT=URL + "ApiT.php?api=listarAlumn";
        public String AGREGARTUT=URL + "ApiT.php?api=addTut";
        public String ELIMINARTUT=URL + "ApiT.php?api=remTut";
        public String LISTARGPO = URL + "ApiG.php?api=listar";
        public String AGREGARGPO = "ApiG.php?api=guardar";
        public String ELIMINARGPO=URL + "ApiG.php?api=eliminar";
        public String EDITARGPO=URL + "ApiG.php?api=editar";
        public String BUSCARDOC = URL + "ApiD.php?api=listarDoc";

    }

