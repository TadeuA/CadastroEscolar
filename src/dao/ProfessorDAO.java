/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.Conexao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Professor;
import conexao.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Tadeu
 */
public class ProfessorDAO {
    public void inserir(Professor professor){
        PreparedStatement pstm = null;
        try{
            final String SQL_INSET = "insert into Professor (MATRICULA, NOME, ENDERECO, CPF, RENDA) values(?,?,?,?,?)";
            pstm = Conexao.getCon().prepareStatement(SQL_INSET);
            pstm.setInt(1, professor.getMatricula());
            pstm.setString(2, professor.getNome());
            pstm.setString(3, professor.getEndereco());
            pstm.setString(4, professor.getCpf());
            pstm.setDouble(5, professor.getRenda());
            pstm.executeUpdate();
        } catch(SQLException e){
            System.out.println("erro ao inserir dados \n " + e.getMessage());
        }
    }
    public void atualizar(Professor professor){
        PreparedStatement pstm = null;
        try{
            final String SQL_INSET = "update Professor set MATRICULA = ?, NOME = ?, ENDERECO = ?, CPF = ?, RENDA = ? where MATRICULA = ?";
            pstm = Conexao.getCon().prepareStatement(SQL_INSET);
            pstm.setInt(1, professor.getMatricula());
            pstm.setString(2, professor.getNome());
            pstm.setString(3, professor.getEndereco());
            pstm.setString(4, professor.getCpf());
            pstm.setDouble(4, professor.getRenda());
            pstm.setInt(4, professor.getMatricula());
            pstm.executeUpdate();
        } catch(SQLException e){
            System.out.println("erro ao inserir dados \n " + e.getMessage());
        }
    }
    public void remover(Professor professor) {
        PreparedStatement pstm = null;
        try {
            final String SQL_DELETE = "delete from Professor where MATRICULA = ?";
            pstm = Conexao.getCon().prepareStatement(SQL_DELETE);
            pstm.setInt(1, professor.getMatricula());
            pstm.executeUpdate();
            System.out.println("Exclus�o realizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados!!!\n" + e.getMessage());
        }        
    }
    public Professor getProfessorPorMatricula(int matricula){
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            final String SQL_PESQUISA = "select * from Professor where MATRICULA = ?;";
            pstm = Conexao.getCon().prepareStatement(SQL_PESQUISA);
            pstm.setInt(1, matricula);
            rs = pstm.executeQuery();
            return carregarResultadoSimples(rs);
        }catch (SQLException e) {
            System.out.println("Erro ao inserir dados!!!\n" + e.getMessage());
        }
        return null;
    }
    
    public Professor getProfessorPorNome(String nome) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            final String SQL_PESQUISA_POR_MATRICULA =
                    "select * from Professor where nome like ?";
            stmt = Conexao.getCon().prepareStatement(SQL_PESQUISA_POR_MATRICULA);
            stmt.setString(1, nome );
            rs = stmt.executeQuery();
            return carregarResultadoSimples(rs);
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }
    }
       
    public List<Professor> findAll() throws Exception{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL_STATEMENT ="Select MATRICULA, NOME, ENDERECO, CPF, RENDA" +
                " from Professor order by NOME";
        try {
            stmt = Conexao.getCon().prepareStatement(SQL_STATEMENT);
            rs = stmt.executeQuery();
            return carregarMultiplosResultados(rs);
        }catch(SQLException sqle){
            throw new Exception(sqle);
        } catch(Exception e){
            throw new Exception(e);
        } finally{}
    }

    private List<Professor> carregarMultiplosResultados(ResultSet rs) throws SQLException{
        List<Professor> resultList = new ArrayList<Professor>();
        while (rs.next()) {
            Professor dto = new Professor();
            carregarVO( dto, rs);
            resultList.add(dto);
        }
        return resultList;
    }

    private Professor carregarResultadoSimples(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Professor dto = new Professor();
            carregarVO(dto, rs);
            return dto;
        } else {
            return null;
        }
    }

    private void carregarVO(Professor dto, ResultSet rs)throws SQLException{
        dto.setMatricula(rs.getInt("MATRICULA"));
        dto.setNome(rs.getString("NOME"));
        dto.setEndereco(rs.getString("ENDERECO"));
        dto.setCpf(rs.getString("CPF"));
        dto.setRenda(rs.getDouble("RENDA"));
    }
}
