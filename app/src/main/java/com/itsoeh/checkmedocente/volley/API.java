package com.itsoeh.checkmedocente.volley;

    public interface API {
        public String URL ="http://192.168.0.107/ws/";
        public String URL2 ="https://darkseagreen-wasp-520101.hostingersite.com/ws/CheckMe/Estudiante/";
        public String URL3 ="https://darkseagreen-wasp-520101.hostingersite.com/ws/CheckMe/Admin/";



        //Comienza el CRUD para Grupos
        public String LISTARGPO = URL + "ApiG.php?api=listar";
        public String GUARDARGPO=URL + "ApiG.php?api=guardar";
        public String EDITARGPO=URL + "ApiG.php?api=editar";
        //Acciones para Tutor
        public String ELIMINARTUT=URL + "ApiT.php?api=remTut";
        public String LISTARTUT=URL + "ApiT.php?api=listarAlumn";
        public String AGREGARTUT=URL + "ApiT.php?api=addTut";

        //Acciones para el docente
        public String REGISTARDOC = URL + "ApiD.php?api=registrarDoc";
        public String LISTARMAT=URL + "ApiT.php?api=listarAsignaturasDocente";
        public String BUSCARDOC = URL + "ApiD.php?api=listarDoc";
        public String PASARLISTA = URL + "ApiD.php?api=pasarLista";
        //Acciones el Historial
        public String LISTARHISTORIAL=URL + "ApiT.php?api=listarHistorial";
        public String LISTARASISTENCIAS = URL + "ApiD.php?api=readPase";
        public String LISTARGPOTUTORADO = URL + "ApiT.php?api=listarGruposT";
        public String LISTARH=URL2 + "ApiH.php?api=listar";
        //Acciones del LOGIN
        public String BUSCARCORREO = URL + "ApiD.php?api=listarCorreo";
        public  String RECUPERA_CONTRA = URL + "ApiD.php?api=recuperaContrasenia";

        public String  ACTUALIZAPASE = URL + "ApiD.php?api=updatePase";
        public String ELIMINARGPO=URL + "ApiG.php?api=eliminar";
        public String LISTAR_PER = URL+ "ApiP.php?api=listar";
        public String LISTAR_ASI = URL + "ApiA.php?api=listar";

        public String ESTADOESTU = URL3+"ApiE.php?api=readEstado";
        public String LISTARESTADO = URL3+"ApiD.php?api=readEstado";

    }

