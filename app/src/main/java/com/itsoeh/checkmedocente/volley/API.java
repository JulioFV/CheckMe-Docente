package com.itsoeh.checkmedocente.volley;

    public interface API {
        public String URL ="https://slateblue-squid-754504.hostingersite.com/Docente/";


        //Comienza el CRUD para Grupos
        public String LISTARTUT=URL + "ApiT.php?api=listarAlumn";
        public String AGREGARTUT=URL + "ApiT.php?api=addTut";
        public String ELIMINARTUT=URL + "ApiT.php?api=remTut";
        public String LISTARGPO = URL + "ApiG.php?api=listar";
        public String REGISTARDOC = URL + "ApiD.php?api=registrarDoc";
        public String LISTARMAT=URL + "ApiT.php?api=listarAsignaturasDocente";
        public String LISTARHISTORIAL=URL + "ApiT.php?api=listarHistorial";
        public String BUSCARDOC = URL + "ApiD.php?api=listarDoc";
        public String PASARLISTA = URL + "ApiD.php?api=pasarLista";

    }

