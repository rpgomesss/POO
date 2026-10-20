package org.domuscontrol.casa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.domuscontrol.dispositivos.Dispositivo;
import org.domuscontrol.exceptions.DispositivoDuplicadoException;
import org.domuscontrol.exceptions.DispositivoInexistenteException;

public class Divisao implements Serializable {
    
    private int identificador;
    private static int proxId = 0;

    public static void setProxId(int id) {
        proxId = id;
    }
    private String nome;
    private Map<Integer,Dispositivo> dispositivos;

    private static final long serialVersionUID = 1L;

    public Divisao() {
        this.identificador = proxId++;
        this.nome = "";
        this.dispositivos = new HashMap<>();
    }

    public Divisao(String nome, Map<Integer, Dispositivo> dispositivos) {
        this.identificador = proxId++;
        this.nome = nome;
        this.setDispositivos(dispositivos);
    }

    public Divisao(Divisao umaDivisao) {
        this.identificador = umaDivisao.getIdentificador();
        this.nome = umaDivisao.getNome();
        this.dispositivos = umaDivisao.getDispositivos();
    }

    public int getIdentificador() {
        return this.identificador;
    }

    public String getNome() {
        return this.nome;
    }

    public Map<Integer,Dispositivo> getDispositivos() {
        Map<Integer,Dispositivo> novo = new HashMap<>(this.dispositivos.size());

        Set<Integer> chaves = this.dispositivos.keySet();
        for(Integer id : chaves){
            novo.put(id, this.dispositivos.get(id));
        }
        return novo;
    } 

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDispositivos(Map<Integer, Dispositivo> dispositivos) {
        this.dispositivos = new HashMap<>(dispositivos.size());

        Set<Integer> chaves = dispositivos.keySet();
        for(Integer id : chaves){
            this.dispositivos.put(id, dispositivos.get(id).clone());
        }
    }

    public Double calculaConsumoTotal() {
        return this.dispositivos.values()
                                .stream()
                                .mapToDouble(Dispositivo::getConsumoTotal)
                                .sum();
    }

    public Dispositivo getDispositivo(int id) throws DispositivoInexistenteException {
        if (!this.dispositivos.containsKey(id)) 
            throw new DispositivoInexistenteException(id);
        return this.dispositivos.get(id);
    }

    public void adicionarDispositivo(Dispositivo d) throws DispositivoDuplicadoException{
        if (d == null)
            throw new IllegalArgumentException("Dispositivo inválido.");


        if (this.dispositivos.containsKey(d.getId())) 
            throw new DispositivoDuplicadoException(d.getId());
        

        this.dispositivos.put(d.getId(), d.clone());
    }

    public void removerDispositivo(int id) throws DispositivoInexistenteException {
        if (!this.dispositivos.containsKey(id))
            throw new DispositivoInexistenteException(id);
        
        this.dispositivos.remove(id);
    }

    public boolean existeDispositivo(int id) {
        return this.dispositivos.containsKey(id);
    }

    public int numeroDispositivos() {
        return this.dispositivos.size();
    }

    @Override
    public Divisao clone() {
        return new Divisao(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Divisão ===\n");
        sb.append("ID: ").append(this.identificador).append("\n");
        sb.append("Nome: ").append(this.nome).append("\n");

        sb.append("\nDispositivos:\n");
        if (this.dispositivos.isEmpty()) {
            sb.append("  (nenhum)\n");
        } else {
            for (Dispositivo d : this.dispositivos.values()) {
                sb.append("  - [")
                  .append(d.getId())
                  .append("] ")
                  .append(d.getClass().getSimpleName())
                  .append("\n");
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Divisao divisao = (Divisao) o;

        return this.identificador == divisao.getIdentificador() &&
               this.nome.equals(divisao.getNome()) &&
               this.dispositivos.equals(divisao.getDispositivos());
    }
}