/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author tadeu
 */
public class Turma {
    private int id;
    private Curso curso;
    private ArrayList <Aluno>  alunos;
    private Professor professor;
    
    public Turma(){
        alunos = new ArrayList();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * @return the alunos
     */
    public Aluno getAlunos(int matricula) {
        Iterator it=alunos.iterator();
        while(it.hasNext()) {
            Aluno aluno = (Aluno) it;
             if(aluno.getMatricula() == matricula) {
                 return aluno;
             } 
             it.next();
        }
        return null;
    }

    /**
     * @param alunos the alunos to set
     */
    public void setAlunos(Aluno aluno) {
        this.alunos.add(aluno);
    }

    /**
     * @return the professor
     */
    public Professor getProfessor() {
        return professor;
    }

    /**
     * @param professor the professor to set
     */
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
