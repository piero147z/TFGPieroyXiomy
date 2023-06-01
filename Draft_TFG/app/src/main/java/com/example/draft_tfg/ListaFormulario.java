package com.example.draft_tfg;

public class ListaFormulario {

     int id;
     int precioVivienda;
     int dineroNecesita;
     int añosPago;
     String tipoVivienda;
     String usoVivienda;
     String provincia;
     int edad;
     String tipoBanco;

    public ListaFormulario() {
    }

    @Override
    public String toString() {
        return "ListaFormulario{" +
                "precioVivienda=" + precioVivienda +
                ", dineroNecesita=" + dineroNecesita +
                ", añosPago=" + añosPago +
                ", tipoVivienda='" + tipoVivienda + '\'' +
                ", usoVivienda='" + usoVivienda + '\'' +
                ", provincia='" + provincia + '\'' +
                ", edad=" + edad +
                ", tipoBanco='" + tipoBanco + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getPrecioVivienda() {
        return precioVivienda;
    }

    public void setPrecioVivienda(int precioVivienda) {
        this.precioVivienda = precioVivienda;
    }

    public double getDineroNecesita() {
        return dineroNecesita;
    }

    public void setDineroNecesita(int dineroNecesita) {
        this.dineroNecesita = dineroNecesita;
    }

    public int getAñosPago() {
        return añosPago;
    }

    public void setAñosPago(int añosPago) {
        this.añosPago = añosPago;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getUsoVivienda() {
        return usoVivienda;
    }

    public void setUsoVivienda(String usoVivienda) {
        this.usoVivienda = usoVivienda;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTipoBanco() {
        return tipoBanco;
    }

    public void setTipoBanco(String tipoBanco) {
        this.tipoBanco = tipoBanco;
    }
}
