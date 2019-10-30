/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.Conexao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Curso;
import conexao.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Tadeu
 */
public class CursoDAO {
    public void inserir(Curso curso){
        PreparedStatement pstm = null;
        try{
            final String SQL_INSET = "insert into Curso (ID, NOME, CARGA_HORARIA) values(?,?,?)";
            pstm = Conexao.getCon().prepareStatement(SQL_INSET);
            pstm.setInt(1, curso.getId());
            pstm.setString(2, curso.getNome());
            pstm.setInt(3, curso.getCargaHoraria());
            pstm.executeUpdate();
        } catch(SQLException e){
            System.out.println("erro ao inserir dados \n " + e.getMessage());
        }
    }
    public void atualizar(Curso curso){
        PreparedStatement pstm = null;
        try{
            final String SQL_INSET = "update Curso set ID = ?, NOME = ?, CARGA_HORARIA = ? where MATRICULA = ?";
            pstm = Conexao.getCon().prepareStatement(SQL_INSET);
            pstm.setInt(1, curso.getId());
            pstm.setString(2, curso.getNome());
            pstm.setInt(3, curso.getCargaHoraria());
            pstm.setInt(4, curso.getId());
            pstm.executeUpdate();
        } catch(SQLException e){
            System.out.println("erro ao inserir dados \n " + e.getMessage());
        }
    }
    public void remover(Curso curso) {
        PreparedStatement pstm = null;
        try {
            final String SQL_DELETE = "delete from Curso where ID = ?";
            pstm = Conexao.getCon().prepareStatement(SQL_DELETE);
            pstm.setInt(1, curso.getId());
            pstm.executeUpdate();
            System.out.println("Exclus�o realizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados!!!\n" + e.getMessage());
        }        
    }
    public Curso getCursoPorId(int id){
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            final String SQL_PESQUISA = "select * from Curso where ID = ?;";
            pstm = Conexao.getCon().prepareStatement(SQL_PESQUISA);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            return carregarResultadoSimples(rs);
        }catch (SQLException e) {
            System.out.println("Erro ao inserir dados!!!\n" + e.getMessage());
        }
        return null;
    }
    
    public Curso getCursoPorNome(String nome) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            final String SQL_PESQUISA_POR_ID =
                    "select * from Curso where nome like ?";
            stmt = Conexao.getCon().prepareStatement(SQL_PESQUISA_POR_ID);
            stmt.setString(1, nome );
            rs = stmt.executeQuery();
            return carregarResultadoSimples(rs);
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }
    }
       
    public List<Curso> findAll() throws Exception{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL_STATEMENT ="Select ID, NOME, CARGA_HORARIA" +
                " from Curso order by NOME";
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

    private List<Curso> carregarMultiplosResultados(ResultSet rs) throws SQLException{
        List<Curso> resultList = new ArrayList<Curso>();
        while (rs.next()) {
            Curso dto = new Curso();
            carregarVO( dto, rs);
            resultList.add(dto);
        }
        return resultList;
    }

    private Curso carregarResultadoSimples(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Curso dto = new Curso();
            carregarVO(dto, rs);
            return dto;
        } else {
            return null;
        }
    }

    private void carregarVO(Curso dto, ResultSet rs)throws SQLException{
        dto.setId(rs.getInt("ID"));
        dto.setNome(rs.getString("NOME"));
        dto.setCargaHoraria(rs.getInt("CARGA_HORARIA"));
    }
}
