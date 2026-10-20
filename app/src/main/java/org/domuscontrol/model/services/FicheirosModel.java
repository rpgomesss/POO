package org.domuscontrol.model.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.domuscontrol.model.DomusControl;

public class FicheirosModel {

    private DomusControl domus;

    public FicheirosModel(DomusControl d){
        this.domus = d;
    }
    
    // ================= FICHEIROS =================

    public void carregarEstado(String nomeFicheiro) throws IOException, ClassNotFoundException{
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeFicheiro))) {
            DomusControl dc = (DomusControl) ois.readObject();
            this.domus.setUtilizadores(dc.getUtilizadores());
            this.domus.setCasas(dc.getCasas());
            this.domus.setTempoAtual(dc.getTempoAtual());
        }
    }

    public void guardarEstado(String nomeFicheiro) throws IOException, FileNotFoundException{
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeFicheiro))) {
            oos.writeObject(this.domus);
        }
    }

}
