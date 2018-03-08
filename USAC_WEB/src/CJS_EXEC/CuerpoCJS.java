/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CJS_EXEC;

/**
 *
 * @author richard
 */
import java.util.ArrayList;
import AST.*;
import javax.swing.JOptionPane;
public class CuerpoCJS {
    
    Tabla_Funciones funciones;
    ArrayList<Ambito> ambitos;
    ASTNodo raiz;
    ArrayList<TError> errores_semanticas;
    NodoOperacion retorno;
    boolean huboDetener;
    boolean huboReturn;
    ArrayList<NodoImprimir> imp;
    
    public CuerpoCJS(ArrayList<Ambito> ambitos, ASTNodo raiz, Tabla_Funciones funciones)
    {
        this.ambitos = ambitos;
        this.raiz = raiz;
        this.funciones = funciones;
        this.errores_semanticas = new ArrayList<>();
        this.retorno = new NodoOperacion("error", "error", 0, 0);
        huboDetener = false;
        huboReturn = false;
        this.imp = new ArrayList<>();
    }

    public NodoOperacion getRetorno() {
        return retorno;
    }

    public boolean isHuboDetener() {
        return huboDetener;
    }

    public boolean isHuboReturn() {
        return huboReturn;
    }
    
    public ArrayList<TError> error()
    {
        return this.errores_semanticas;
    }
    
    public Tabla_Variables dameGlobales()
    {
        return this.ambitos.get(0).tabla_simbolos;
    }

    public ArrayList<NodoImprimir> getImp() {
        return imp;
    }

    public Tabla_Funciones getFunciones() {
        return funciones;
    }
    
    
    
    public Variable busca_en_Ambitos(String nombre)
    {
        Variable aux = null;
        for(int x = 0; x < this.ambitos.size(); x++)
        {
            aux = this.ambitos.get(x).busca_Variable(nombre);
            if(aux!=null)
            {
                return aux;
            }
        }
        return aux;
    }
    
    public Variable busca_vector_Ambitos(String nombre)
    {
        Variable aux = null;
        for(int x = 0; x < this.ambitos.size(); x++)
        {
            aux = this.ambitos.get(x).busca_vector(nombre);
            if(aux!=null)
            {
                return aux;
            }
        }
        return aux;
    }
    
    
    public boolean ejecutaCuerpo()
    {
        try 
        {
            //AQUI INICIO EJECUCION
            inicia_ejecucion(raiz);
            return true;
        } catch (Exception e) 
        {
            System.err.println("Error al ejecutar el cuerpo de CJS");
            return false;
        }
    }
    
    private Object inicia_ejecucion(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "CUERPO_CJS":
            {
                if(raiz.contarHijos()==2)
                {
                    //ME VOY MAS A LA IZQUIERDA
                    inicia_ejecucion(raiz.getHijo(0));
                    //ME VOY A LA DERECHA
                    if(!huboDetener && !huboReturn)
                    {
                        inicia_ejecucion(raiz.getHijo(1));
                    }
                }
                if(raiz.contarHijos()==1)
                {
                    //BAJO EN EL ARBOL
                    inicia_ejecucion(raiz.getHijo(0));
                }
                break;
            }
            case "C_VAR_1"://VARIABLES QUE SOLO FUERON DECLARADAS
            {
                if(raiz.contarHijos()==1)
                {
                    //ME VOY A LA LISTA DE IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    if(ids!=null)
                    {
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable var = new Variable(ids.get(x).getEtiqueta());
                            var.setEsVector(false);
                            if(!this.ambitos.get(0).agregaVariableAlAmbito(var))
                            {
                                //SE GENERA UN ERROR DE SEMANTICA AGREGAR LA LINEA Y LA COLUMNA
                                TError error = new TError(ids.get(x).getEtiqueta(),"Error Semantico","Esta variable ya existe en este Ambito",ids.get(x).getLine(),ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                            
                        }
                    }
                }
                break;
            }
            case "C_VAR_2":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    if(ids!=null && !res.getValor().equals("error"))
                    {
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable var = new Variable(ids.get(x).getEtiqueta());
                            if(res.getTipo().equals("vector"))
                            {
                                var.setEsVector(true);
                                var.setTipo("vector");
                                var.setValVectores(res.getValVectores());
                            }
                            else
                            {
                                var.setEsVector(false);
                                var.setTipo(res.getTipo());
                                var.addValor(res.getValor());
                            }
                            if(!this.ambitos.get(0).agregaVariableAlAmbito(var))
                            {
                                TError error = new TError(ids.get(x).getEtiqueta(),"Error Semantico","Esta variable ya existe en este Ambito",ids.get(x).getLine(),ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                    }
                }
                break;
            }
            case"C_VAR_3":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    NodoOperacion res = (NodoOperacion)inicia_ejecucion(raiz.getHijo(1));//DIMENSION DEL ARRAY
                    if(ids!=null && res!=null)
                    {
                        if(res.getTipo().equals("numerico"))
                        {
                            for(int x = 0; x < ids.size(); x++)
                            {
                                Variable var = new Variable(ids.get(x).getEtiqueta());
                                var.setEsVector(true);
                                var.setTipo("vector");
                                ArrayList<NodoOperacion> valores = new ArrayList<>();
                                int dim = Integer.parseInt(res.getValor());
                                var.setDimension(dim);
                                for(int d = 0; d < dim; d++)
                                {
                                    valores.add(new NodoOperacion("0","0",0,0));
                                }
                                var.setValVectores(valores);
                                if(!this.ambitos.get(0).agregaVariableAlAmbito(var))
                                {
                                    TError error = new TError(ids.get(x).getEtiqueta(),"Error Semantico","Esta variable ya existe en este Ambito",ids.get(x).getLine(),ids.get(x).getColumn());
                                    this.errores_semanticas.add(error);
                                }
                            }
                        }
                    }
                }
                break;
            }
            case "C_VAR_4":
            {
                if(raiz.contarHijos()==4)
                {
                    //OBTENIENDO LOS IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    //DIMENSION
                    NodoOperacion dimension = (NodoOperacion)inicia_ejecucion(raiz.getHijo(1));
                    //PRIMERA POSICION
                    Expresion exp1 = new Expresion(funciones, ambitos, raiz.getHijo(2), errores_semanticas);
                    NodoOperacion prPos = (NodoOperacion)exp1.evaluaExpresion();
                    //LISTA DE EXPRESIONES QUE HABRIAN OBTENIDAS DEL VECTOR
                    ArrayList<NodoOperacion> ops = (ArrayList<NodoOperacion>)inicia_ejecucion(raiz.getHijo(3));
                    if(ids!=null && dimension.getTipo().equals("numerico") && !prPos.getValor().equals("error") && ops!=null)
                    {
                        int dim = Integer.parseInt(dimension.getValor());
                        ops.add(0, prPos);
                        int size = ops.size();
                        if(dim == size)
                        {
                            for(int x = 0; x< ids.size(); x++)
                            {
                                Variable var = new Variable(ids.get(x).getEtiqueta());
                                var.setEsVector(true);
                                var.setTipo("vector");
                                var.setValVectores(ops);
                                var.setDimension(dim);
                                if(!this.ambitos.get(x).agregaVariableAlAmbito(var))
                                {
                                    TError error = new TError(ids.get(x).getEtiqueta(),"Error Semantico","Esta variable ya existe en este Ambito",ids.get(x).getLine(),ids.get(x).getColumn());
                                    this.errores_semanticas.add(error);
                                }
                            }
                        }
                        else
                        {
                            //ERROR SEMANTICO
                            TError error = new TError("Dimension: "+String.valueOf(dim), "Error Semantico", "El tamanio del vector no coincide con valores",dimension.getLinea(), dimension.getColumna());
                            this.errores_semanticas.add(error);
                        }
                    }
                    else
                    {
                        //ERROR SEMANTICO
                        TError error = new TError("Declaracion Invalida","Error Semantico","Parametros invalidos al declara un vector",dimension.getLinea(), dimension.getColumna());
                        this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "C_VAR_5":
            {
                if(raiz.contarHijos()==3)
                {
                    //OBTENIENDO LOS IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    //PRIMERA POSICION
                    Expresion exp1 = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion prPos = (NodoOperacion)exp1.evaluaExpresion();
                    //LISTA DE EXPRESIONES QUE HABRIAN OBTENIDAS DEL VECTOR
                    ArrayList<NodoOperacion> ops = (ArrayList<NodoOperacion>)inicia_ejecucion(raiz.getHijo(2));
                    if(ids!=null && !prPos.getValor().equals("error") && ops!=null)
                    {
                        ops.add(0,prPos);
                        int dimen = ops.size();
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable var = new Variable(ids.get(x).getEtiqueta());
                            var.setEsVector(true);
                            var.setTipo("vector");
                            var.setDimension(dimen);
                            var.setValVectores(ops);
                            if(!this.ambitos.get(0).agregaVariableAlAmbito(var))
                            {
                                TError error = new TError(ids.get(x).getEtiqueta(),"Error Semantico","Esta variable ya existe en este Ambito",ids.get(x).getLine(),ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                    }
                    else
                    {
                       TError error = new TError("Declaracion Invalida","Error Semantico","Parametros invalidos al declara un vector",prPos.getLinea(), prPos.getColumna());
                       this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "C_VAR_6":
            {
                //AUN PENDIENTE AQUE DEBO DE LLAMAR LA TABLA DE OBJETOS CHTML QUE GENERE DESPUES DE INTERPRETARLO
                if(raiz.contarHijos()==2)
                {
                    ArrayList<ASTNodo>ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    String objeto = raiz.getHijo(1).getEtiqueta();
                    if(ids!=null)
                    {
                        JOptionPane.showMessageDialog(null, "ACORDATE DE PONER EL OBTENER!!!!!!");
                    }
                }
                break;
            }
            case "AS_VAR_1":
            {
                if(raiz.contarHijos()==2)
                {
                    //OBTENGO LOS IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    //OBTENER LA EXPRESION
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    if(ids!=null && !res.getValor().equals("error"))
                    {
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable var = busca_en_Ambitos(ids.get(x).getEtiqueta());
                            if(var!=null)
                            {
                                var.setEsVector(false);
                                var.setTipo(res.getTipo());
                                var.setValor(res.getValor());
                            }
                            else
                            {
                                //ERROR SEMANTICO
                                TError error = new TError(ids.get(x).getEtiqueta(), "Error Semantico", "Asignacion a variable no declarada", ids.get(x).getLine(), ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                    }
                    else
                    {
                        //ERROR SEMANTICO
                        TError error = new TError("Asingnacion Invalida","Error Semantico","Parametros invalidos para la asignacion",res.getLinea(), res.getColumna());
                       this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "AS_VAR_2":
            {
                if(raiz.contarHijos()==3)
                {
                    //OBTENGO LOS IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    //OBTENER EL INDEX AL QUE ME ESTOY REFIRIENDO
                    NodoOperacion index = (NodoOperacion)inicia_ejecucion(raiz.getHijo(1));
                    //EL NUEVO VALOR A ASIGNAR
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(2), errores_semanticas);
                    NodoOperacion valor = (NodoOperacion)exp.evaluaExpresion();
                    if(ids!=null && index.getTipo().equals("numerico") && !valor.getValor().equals("error"))
                    {
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable var = busca_vector_Ambitos(ids.get(x).getEtiqueta());
                            if(var!=null)
                            {
                                int dimension = var.getDimension();
                                int indice = Integer.parseInt(index.getValor());
                                if(indice < dimension)
                                {
                                    var.set_val_index(indice, valor);
                                    var.setEsVector(true);
                                }
                                else
                                {
                                    TError error = new TError(ids.get(x).getEtiqueta(), "Error Semantico", "Indice fuera de limites del Vector", ids.get(x).getLine(), ids.get(x).getColumn());
                                }
                            }
                            else
                            {
                                TError error = new TError(ids.get(x).getEtiqueta(), "Error Semantico", "Asignacion a vector no declarada", ids.get(x).getLine(), ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                    }
                    else
                    {
                        TError error = new TError("Asignacion Invalida","Error Semantico","Parametros invalidos para la asignaciona un vector",valor.getLinea(), valor.getColumna());
                       this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "AS_VAR_3":
            {
                if(raiz.contarHijos()==3)
                {
                    //LISTA DE IDS
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    //PRIMERA EXPRESION
                    Expresion primera = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion first = (NodoOperacion)primera.evaluaExpresion();
                    //OBTENGO LOS DEMAS VALORES
                    ArrayList<NodoOperacion> valores = (ArrayList<NodoOperacion>)inicia_ejecucion(raiz.getHijo(2));
                    if(ids!=null && !first.getValor().equals("error") && valores!=null)
                    {
                        valores.add(0, first);
                        for(int x = 0; x < ids.size(); x++)
                        {
                            Variable vec = busca_vector_Ambitos(ids.get(x).getEtiqueta());
                            if(vec!=null)
                            {
                                int dimension = vec.getDimension();
                                int vals = valores.size();
                                if(dimension == vals)
                                {
                                    vec.setValVectores(valores);
                                    vec.setEsVector(true);
                                }
                                else
                                {
                                   TError error = new TError(ids.get(x).getEtiqueta(), "Error Semantico", "Dimension y Cantidad de Valores no Coinciden", ids.get(x).getLine(), ids.get(x).getColumn());
                                    this.errores_semanticas.add(error); 
                                }
                            }
                            else
                            {
                                TError error = new TError(ids.get(x).getEtiqueta(), "Error Semantico", "Asignacion a vector no declarada", ids.get(x).getLine(), ids.get(x).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                    }
                    else
                    {
                        TError error = new TError("Asignacion Invalida","Error Semantico","Parametros invalidos para la asignaciona un vector",first.getLinea(), first.getColumna());
                       this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "AS_VAR_4":
            {
                //ASIGNACION DE UN OBJETO DEL DOCUMENTO AUN PENDIENTE IGUAL FORMA HAGO UN LLAMADO AL OBJETO QUE NECESITO PARA PODER TOMARLO
                if(raiz.contarHijos()==2)
                {
                    ArrayList<ASTNodo>ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    String objeto = raiz.getHijo(1).getEtiqueta();
                    if(ids!=null)
                    {
                        JOptionPane.showMessageDialog(null, "ASINGANDO A :"+ids.get(0).getEtiqueta()+" el componente: "+objeto);
                    }
                }
                break;
            }
            case "SENT_SI":
            {
                //INSERTO UN AMBITO TEMPORAL, EL CUAL SIMULA LA VISUALIZACION DE LAS VARIABLES
                Ambito ambito = new Ambito("sentencia si");
                this.ambitos.add(0, ambito);//PSUH
                evaluaSentenciaSi(raiz);
                this.ambitos.remove(0);//POP
                break;
            }
            case "SELECCIONA":
            {
                evaluaSentenciaSelecciona(raiz);
                break;
            }
            case "SENT_PARA":
            {
                Ambito ambito = new Ambito("PARA");
                this.ambitos.add(0, ambito);
                evaluaSentenciaPara(raiz);
                this.ambitos.remove(0);
                break;
            }
            case "SENT_MIENTRAS":
            {
                Ambito ambito = new Ambito("MIENTRAS");
                this.ambitos.add(0, ambito);
                evaluaSentenciaMientras(raiz);
                this.ambitos.remove(0);
                break;
            }
            case "DETENER":
            {
                this.huboDetener = true;
                break;
            }
            case "RETORNAR":
            {
                this.huboReturn = true;
                if(raiz.contarHijos()==1)
                {
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    if(!op.getValor().equals("error"))
                    {
                        this.retorno = op;
                    }
                }
                break;
            }
            case "IMPRIMIR":
            {
                if(raiz.contarHijos()==1)
                {
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    if(!op.getValor().equals("error"))
                    {
                        this.imp.add(new NodoImprimir(op.getValor(), raiz.getLine()+1, raiz.getColumn()+1));
                        System.out.println("Imprimir: "+op.getValor()+" | Linea: "+op.getLinea()+" | Columna: "+op.getColumna());
                    }
                }
                break;
            }
            case "MENSAJE":
            {
                if(raiz.contarHijos()==1)
                {
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    if(!op.getValor().equals("error"))
                    {
                        JOptionPane.showMessageDialog(null, op.getValor());
                    }
                }
                break;
            }
            case "INDEX":
            {
                if(raiz.contarHijos()==1)
                {
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    return op;
                }
                break;
            }
            case "AUM_DISM":
            {
                if(raiz.contarHijos()==2)
                {
                    String id = raiz.getHijo(0).getEtiqueta();
                    Variable var = busca_en_Ambitos(id);
                    if(var.getTipo().equals("numerico"))
                    {
                        double aux = Double.parseDouble(var.getValor());
                        if(raiz.getHijo(1).getEtiqueta().equals("++"))
                        {
                            aux = aux+1;
                            var.setValor(String.valueOf(aux));
                        }
                        else
                        {
                            aux = aux-1;
                            var.setValor(String.valueOf(aux));
                        }
                    }
                    else
                    {
                        TError error = new TError("Expresion invalida","Error Semantico","La expresion en Sentencia SI es invalida",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                        this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "ARRAY":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<NodoOperacion> op = (ArrayList<NodoOperacion>)inicia_ejecucion(raiz.getHijo(1));
                    if(op==null)
                    {
                        op = new ArrayList<>();
                    }
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    if(!res.getValor().equals("error"))
                    {
                        op.add(0,res);
                    }
                    return op;
                }
                if(raiz.contarHijos()==1)
                {
                    ArrayList<NodoOperacion> op = new ArrayList<>();
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    if(!res.getValor().equals("error"))
                    {
                        op.add(res);
                    }
                    return op;
                }
                break;
            }
            case "L_ID":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<ASTNodo> ids = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(0));
                    if(ids==null)
                    {
                        ids = new ArrayList<>();
                    }
                    ids.add(raiz.getHijo(1));
                    return ids;
                }
                if(raiz.contarHijos()==1)
                {
                    
                    ArrayList<ASTNodo> ids = new ArrayList<>();
                    ids.add(raiz.getHijo(0));
                    return ids;
                }
            }
            case "DIMENSION":
            {
                if(raiz.contarHijos()==1)
                {
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    return res;
                }
                break;
            }
            case "FUNCION_1":
            {
                //UNA FUNCION CON SU NOMBRE Y  PARAMETROS SIN NINUGNA EJECUCION
                if(raiz.contarHijos()==2)
                {
                    String nombreFun = raiz.getHijo(0).getEtiqueta();
                    ArrayList<ASTNodo> nomParams = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(1));
                    if(nomParams!=null)
                    {
                        Funcion f = new Funcion(nombreFun, null);
                        ArrayList<Parametro> params = new ArrayList<>();
                        for(int x = 0; x < nomParams.size(); x++)
                        {
                            Parametro p = new Parametro(nomParams.get(x).getEtiqueta(), "null");
                            params.add(p);
                        }
                        f.setParametros(params);
                        if(!this.funciones.addFuncion(f))
                        {
                            TError error = new TError("Funcion: "+nombreFun,"Error Semantico","Esta funcion ya existe",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                            this.errores_semanticas.add(error);
                        }
                    }
                }
                break;
            }
            case "FUNCION_2":
            {
                if(raiz.contarHijos()==1)
                {
                    String nombreFun = raiz.getHijo(0).getEtiqueta();
                    Funcion f = new Funcion(nombreFun, null);
                    if(!this.funciones.addFuncion(f))
                    {
                        TError error = new TError("Funcion: "+nombreFun,"Error Semantico","Esta funcion ya existe",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                        this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "FUNCION_4":
            {
                if(raiz.contarHijos()==3)
                {
                    String nombreFun = raiz.getHijo(0).getEtiqueta();
                    ArrayList<ASTNodo> parm = (ArrayList<ASTNodo>)inicia_ejecucion(raiz.getHijo(1));
                    if(parm!=null)
                    {
                        Funcion f = new Funcion(nombreFun, raiz.getHijo(2));
                        ArrayList<Parametro>params = new ArrayList<>();
                        for(int x = 0; x < parm.size(); x++)
                        {
                            Parametro p = new Parametro(parm.get(x).getEtiqueta(), "null");
                            params.add(p);
                        }
                        f.setParametros(params);
                        if(!this.funciones.addFuncion(f))
                        {
                            TError error = new TError("Funcion: "+nombreFun,"Error Semantico","Esta funcion ya existe",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                            this.errores_semanticas.add(error);
                        }
                    }
                }
                break;
            }
            case "FUNCION_5":
            {
                if(raiz.contarHijos()==2)
                {
                    String nombreFun = raiz.getHijo(0).getEtiqueta();
                    Funcion f = new Funcion(nombreFun, raiz.getHijo(1));
                    if(!this.funciones.addFuncion(f))
                    {
                        TError error = new TError("Funcion: "+nombreFun,"Error Semantico","Esta funcion ya existe",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                        this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "SETELEMT_VAR":
            {
                if(raiz.contarHijos()==3)
                {
                    String nombreVar = raiz.getHijo(0).getEtiqueta();
                    String propiedad = raiz.getHijo(1).getEtiqueta();
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(2), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    if(!op.getTipo().equals("error"))
                    {
                        //AQUI SETEO LA NUEVA PROPIEDAD QUE NECEISTO CAMBIAR BUSCANDO EN LA TABLA DE VALORES PARA RECUPERAR EL OBJETO Y CAMBIARLE LA PROPIEDAD
                    }
                }   
                break;
            }
            case "SETELEMT_OBJ":
            {
                if(raiz.contarHijos()==3)
                {
                    String nombreVar = raiz.getHijo(0).getEtiqueta();
                    String propiedad = raiz.getHijo(1).getEtiqueta();
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(2), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    if(!op.getTipo().equals("error"))
                    {
                        //AQUI BUSCO DIRECTAMENTE EL ELEMENTO Y LE CAMBIO LA PROPIEDAD
                    }
                }
                break;
            }
            case "OBSERVADOR_1":
            {
                if(raiz.contarHijos()==2)
                {
                    String nombreOBJ = raiz.getHijo(0).getEtiqueta();
                    String nombreFun = "";
                    if(raiz.getHijo(1).contarHijos()>0)
                    {
                        nombreFun = raiz.getHijo(1).getHijo(0).getEtiqueta();
                        if(!nombreFun.equals(""))
                        {
                            //ANADO EL NOMBRE DE LA FUNCION AL ACTION LISTENER DEL OBJETO
                        }
                    }
                }
                break;
            }
            case "OBSERVADOR_2":
            {
                if(raiz.contarHijos()==2)
                {
                    String nomOBJ = raiz.getHijo(0).getEtiqueta();
                    String nombreFun = raiz.getHijo(1).getEtiqueta();
                    //AGREGO AL ACTION LISTENER DEL BOTON LA LLAMADA A ESTA FUNCION
                }
                break;
            }
            case "OBSERVADOR_3":
            {
                if(raiz.contarHijos()==3)
                {
                    String nombreVar = raiz.getHijo(0).getEtiqueta();
                    String tipo = raiz.getHijo(1).getEtiqueta();
                    String nombreFun = "";
                    if(raiz.getHijo(2).contarHijos()>0)
                    {
                        nombreFun = raiz.getHijo(2).getHijo(0).getEtiqueta();
                    }
                    //BUSCO LA VARIABLE Y LE ASIGNO AL OBJETO QUE ESTA REFERENCIADO EN EL EVENTO QUE PIDIO EL NOMBRE DE FUNCION A BUSCAR Y EJECUTAR
                }
                break;
            }
            case "OBSERVADOR_4":
            {
                if(raiz.contarHijos()==3)
                {
                     String nombreVar = raiz.getHijo(0).getEtiqueta();
                     String tipo = raiz.getHijo(1).getEtiqueta();
                     String nombreFun = raiz.getHijo(2).getEtiqueta();
                     //BUSCO LA VARIABLE Y LE ASIGNO AL BJETO EL NOMBRE DE LA FUNCION QUE ESTA REFERENCIANDO
                }
                break;
            }
            case "EXEC_FUNC":
            {
                if(raiz.contarHijos()==2)
                {
                    String nombre = raiz.getHijo(0).getEtiqueta();
                    ArrayList<NodoOperacion> parametros = (ArrayList<NodoOperacion>)inicia_ejecucion(raiz.getHijo(1));
                    if(parametros!=null)
                    {
                        Funcion f = this.funciones.buscaFuncion(nombre, parametros.size());
                        if(f!=null)
                        {
                           //VALIDACION DE LA CANTIDAD DE PARAMETROS
                            if(f.getNumeroParametros() == parametros.size())
                            {
                                Ambito ambito = new Ambito(nombre);
                                this.ambitos.add(0, ambito);
                                for(int x = 0; x < parametros.size() ; x++)
                                {
                                    Variable var = new Variable(f.getParametroIndex(x).getIdParametro());
                                    var.setTipo(parametros.get(x).getTipo());
                                    if(parametros.get(x).getTipo().equals("vector"))
                                    {
                                        var.setEsVector(true);
                                        var.setValVectores(parametros.get(x).getValVectores());
                                    }
                                    else
                                    {
                                        var.setEsVector(false);
                                        var.setValor(parametros.get(x).getValor());
                                    }
                                    this.ambitos.get(0).agregaVariableAlAmbito(var);
                                }
                                //EJECUTO LA FUNCION
                                if(f.getRaiz()!=null)
                                {
                                    CuerpoCJS cuerpo = new CuerpoCJS(ambitos, f.getRaiz(), funciones);
                                    cuerpo.ejecutaCuerpo();
                                    if(cuerpo.huboReturn)
                                    {
                                        return cuerpo.getRetorno();
                                    }
                                }
                                this.ambitos.remove(0);
                            }
                            else
                            {
                                TError error = new TError("Funcion: "+nombre,"Error Semantico","La cantidad de parametros no coinciden",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                                this.errores_semanticas.add(error);
                            }
                        }
                        else
                        {
                            TError error = new TError("Funcion: "+nombre,"Error Semantico","No existe una funcion declarada como: "+nombre,raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                            this.errores_semanticas.add(error);
                        }
                    }
                    else
                    {
                        TError error = new TError("Funcion: "+nombre,"Error Semantico","Esta funcion requiere parametros validos",raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                        this.errores_semanticas.add(error);
                    }
                }
                if(raiz.contarHijos()==1)
                {
                    String nombre = raiz.getHijo(0).getEtiqueta();
                    Funcion f = this.funciones.buscaFuncion(nombre, 0);
                    if(f!=null)
                    {
                        Ambito ambito = new Ambito(nombre);
                        this.ambitos.add(0, ambito);
                        if(f.getRaiz()!=null)
                        {
                            CuerpoCJS cuerpo = new CuerpoCJS(ambitos,f.getRaiz(), funciones);
                            cuerpo.ejecutaCuerpo();
                            if(cuerpo.huboReturn)
                            {
                                return cuerpo.getRetorno();
                            }
                        }
                        this.ambitos.remove(0);
                    }
                    else
                    {
                        //ERROR SEMANTICO
                        TError error = new TError("Funcion: "+nombre,"Error Semantico","No existe una funcion declarada como: "+nombre,raiz.getHijo(0).getLine(), raiz.getHijo(0).getColumn());
                        this.errores_semanticas.add(error);
                    }
                }
                break;
            }
            case "PARAMS":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<NodoOperacion> parametros = (ArrayList<NodoOperacion>)inicia_ejecucion(raiz.getHijo(0));
                    if(parametros==null)
                    {
                        parametros = new ArrayList<>();
                    }
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    if(!op.getValor().equals("error"))
                    {
                        parametros.add(op);
                    }
                    return parametros;
                }
                if(raiz.contarHijos()==1)
                {
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    ArrayList<NodoOperacion> parametros = new ArrayList<>();
                    if(!op.getValor().equals("error"))
                    {
                        parametros.add(op);
                    }
                    return parametros;
                }
                break;
            }
        }
        return null;
    }
    
    //SENTENCIA SI - SINO
    private Object evaluaSentenciaSi(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "SENT_SI":
            {
                //SI ES UNA SENTENCIA SI SIN SINO
                if(raiz.contarHijos()==2)
                {
                    //OBTENGO EXPRESION
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    //EVALUO EL RESULTADO DE LA OPERACION:
                    if(!res.getValor().equals("error") && res.getTipo().equals("boolean"))
                    {
                        if(res.getValor().equals("true"))
                        {
                            //EJECUTO LAS ACCIONES QUE VIENEN ADENTRO
                            inicia_ejecucion(raiz.getHijo(1));
                        }
                    }
                    else
                    {
                        //ERROR SEMANTICO
                        TError error = new TError("Expresion invalida","Error Semantico","La expresion en Sentencia SI es invalida",res.getLinea(), res.getColumna());
                       this.errores_semanticas.add(error);
                    }
                }
                //SI ES UNA SENTENCIA SI CON SINO
                if(raiz.contarHijos()==3)
                {
                    //OBTENGO EXPRESION
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion res = (NodoOperacion)exp.evaluaExpresion();
                    //EVALUO EL RESULTADO DE LA OPERACION
                    if(!res.getValor().equals("error") && res.getTipo().equals("boolean"))
                    {
                        if(res.getValor().equals("true"))
                        {
                            //EJECUTO LAS ACCIONES QUE VIENEN ADENTRO
                            inicia_ejecucion(raiz.getHijo(1));
                        }
                        else
                        {
                            //SI NO ES VERDADERO ENTONCES EJECUTO LA SIGUIENTE RAMA
                            inicia_ejecucion(raiz.getHijo(2));
                        }
                    }
                    else
                    {
                        //ERROR SEMANTICO
                        TError error = new TError("Expresion invalida","Error Semantico","La expresion en Sentencia SI es invalida",res.getLinea(), res.getColumna());
                       this.errores_semanticas.add(error);
                    }
                }
                break;
            }
        }
        return null;
    }
    
    private Object evaluaSentenciaSelecciona(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "SELECCIONA":
            {
                Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                NodoOperacion eval = (NodoOperacion)exp.evaluaExpresion();
                if(!eval.getValor().equals("error"))
                {
                    //OBTENGO LA LISTA DE CASOS POSIBLES
                    ArrayList<ASTNodo> casos = (ArrayList<ASTNodo>)evaluaSentenciaSelecciona(raiz.getHijo(1));
                    if(raiz.contarHijos()>0 && casos!=null)
                    {
                        boolean ejecutaDefecto = true;
                        for(int x = 0; x < casos.size(); x++)
                        {
                            //INGRESO UN NUEVO AMBITO
                            Ambito am = new Ambito("Caso_"+x);
                            this.ambitos.add(0, am);
                            if(casos.get(x).contarHijos()>0)
                            {
                                Expresion exps = new Expresion(funciones, ambitos, casos.get(x).getHijo(0), errores_semanticas);
                                NodoOperacion ope = (NodoOperacion)exps.evaluaExpresion();
                                if(!ope.getValor().equals("error"))
                                {
                                    //EVALUO LA EXPRESION
                                    if(ope.getValor().equals(eval.getValor()) && ope.getTipo().equals(eval.getTipo()))
                                    {
                                        //EJECUTO
                                        ejecutaDefecto = false;
                                        if(casos.get(x).contarHijos()==2)
                                        {
                                            inicia_ejecucion(casos.get(x).getHijo(1));
                                        }
                                    }
                                }
                            }
                            //SACO EL AMBITO
                            this.ambitos.remove(0);
                        }
                        if(ejecutaDefecto && raiz.contarHijos()==3)
                        {
                            //EJECUTO POR DEFECTO
                            evaluaSentenciaSelecciona(raiz.getHijo(2));
                        }
                    }
                }
                else
                {
                    //ERROR SEMANTICO
                    TError error = new TError("Expresion Invalida","Error Semantico","Expresion no valida en Sentencia SELECCIONA",eval.getLinea(), eval.getColumna());
                    this.errores_semanticas.add(error);
                }
                break;
            }
            case "L_CASOS":
            {
                if(raiz.contarHijos()==2)
                {
                    ArrayList<ASTNodo> nodos = (ArrayList<ASTNodo>)evaluaSentenciaSelecciona(raiz.getHijo(1));
                    if(nodos==null)
                    {
                        nodos = new ArrayList<>();
                    }
                    nodos.add(0, raiz.getHijo(0));
                    return nodos;
                }
                if(raiz.contarHijos()==1)
                {
                    ArrayList<ASTNodo> nodos = new ArrayList<>();
                    nodos.add(raiz.getHijo(0));
                    return nodos;
                }
                break;
            }
            case "DEFECTO":
            {
                Ambito am = new Ambito("Defecto");
                this.ambitos.add(0,am);
                inicia_ejecucion(raiz.getHijo(0));
                this.ambitos.remove(0);
                break;
            }
        }
        return null;
    }

    private Object evaluaSentenciaPara(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "SENT_PARA":
            {
                if(raiz.contarHijos()>0)
                {
                    //OBTENGO LA NUEVA VARIABLE DE CONTROL
                    String auxiliar = raiz.getHijo(0).getEtiqueta();
                    //OBTENGO EL VALOR INICIAL DE LA EXPRESION
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(1), errores_semanticas);
                    NodoOperacion op = (NodoOperacion)exp.evaluaExpresion();
                    //CREO LA VARIABLE QUE LLEVA EL CONTROL...
                    if(op.getTipo().equals("numerico"))
                    {
                        Variable control = new Variable(auxiliar);
                        control.setTipo("numerico");
                        control.setValor(op.getValor());
                        control.setEsVector(false);
                        if(!this.ambitos.get(0).agregaVariableAlAmbito(control))
                        {
                            TError error = new TError("Variable: "+auxiliar,"Error Semantico","Ya existe en este ambito",op.getLinea(), op.getColumna());
                            this.errores_semanticas.add(error);
                        }
                        else
                        {
                            Expresion cond = new Expresion(funciones, ambitos, raiz.getHijo(2), errores_semanticas);
                            NodoOperacion condi = (NodoOperacion)cond.evaluaExpresion();
                            int tipo = 0;
                            if(raiz.getHijo(3).getEtiqueta().equals("++")){tipo = 1;}
                            if(condi.getTipo().equals("boolean"))
                            {
                                boolean v = toBoolean(condi);
                                while(v)
                                {
                                    if(raiz.contarHijos()==5)
                                    {
                                        CuerpoCJS cuerpoPara = new CuerpoCJS(ambitos, raiz.getHijo(4), funciones);
                                        cuerpoPara.ejecutaCuerpo();
                                        //inicia_ejecucion(raiz.getHijo(4));
                                        if(cuerpoPara.isHuboDetener())
                                        {
                                            break;
                                        }
                                        else if(cuerpoPara.isHuboReturn())
                                        {
                                            break;
                                        }
                                    }
                                    Variable aux = busca_en_Ambitos(auxiliar);
                                    double aume_dec = Double.parseDouble(aux.getValor());
                                    if(tipo == 1)
                                    {
                                        aume_dec = aume_dec +1;
                                    }
                                    else
                                    {
                                        aume_dec = aume_dec -1;
                                    }
                                    aux.setValor(String.valueOf(aume_dec));
                                    condi = (NodoOperacion)cond.evaluaExpresion();
                                    v = toBoolean(condi);
                                }
                            }
                        }
                    }
                    else
                    {
                        TError error = new TError("Variable: "+auxiliar,"Error Semantico","Tipo no compatible para ciclo PARA",op.getLinea(), op.getColumna());
                        this.errores_semanticas.add(error);
                    }
                }
            }
        }
        return null;
    }
    
    private boolean toBoolean(NodoOperacion op)
    {
        if(op.getValor().toLowerCase().equals("true"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private Object evaluaSentenciaMientras(ASTNodo raiz)
    {
        switch(raiz.getEtiqueta())
        {
            case "SENT_MIENTRAS":
            {
                if(raiz.contarHijos()>0)
                {
                    Expresion exp = new Expresion(funciones, ambitos, raiz.getHijo(0), errores_semanticas);
                    NodoOperacion condi = (NodoOperacion)exp.evaluaExpresion();
                    if(condi.getTipo().equals("boolean"))
                    {
                        boolean condicion = toBoolean(condi);
                        while(condicion)
                        {
                            if(raiz.contarHijos()==2)
                            {
                                CuerpoCJS cuerpoMientras = new CuerpoCJS(ambitos, raiz.getHijo(1), funciones);
                                cuerpoMientras.ejecutaCuerpo();
                                if(cuerpoMientras.isHuboDetener())
                                {
                                    break;
                                }
                                else if(cuerpoMientras.huboReturn)
                                {
                                    break;
                                }
                                //inicia_ejecucion(raiz.getHijo(1));
                            }
                            condi = (NodoOperacion)exp.evaluaExpresion();
                            condicion = toBoolean(condi);
                        }
                    }
                    else
                    {
                        TError error = new TError("Condicion: "+condi.getValor(),"Error Semantico","Expresion requerdia: Boolean, encontrada: "+condi.getTipo(),condi.getLinea(), condi.getColumna());
                        this.errores_semanticas.add(error);
                    }
                }
                break;
            }
        }
        return null;
    }

}
