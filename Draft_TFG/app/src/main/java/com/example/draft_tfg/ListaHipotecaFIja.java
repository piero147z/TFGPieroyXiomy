package com.example.draft_tfg;

public class ListaHipotecaFIja {

    int id;
    String banco;
    double tin;
    double tae;

    public ListaHipotecaFIja() {
    }

    @Override
    public String toString() {
        return "ListaHipotecaFIja{" +
                "banco='" + banco + '\'' +
                ", tin=" + tin +
                ", tae=" + tae +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public double getTin() {
        return tin;
    }

    public void setTin(double tin) {
        this.tin = tin;
    }

    public double getTae() {
        return tae;
    }

    public void setTae(double tae) {
        this.tae = tae;
    }
}
