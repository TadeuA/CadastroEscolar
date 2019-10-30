/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Aluno;

/**
 *
 * @author Tadeu
 */
public class AlunoDAO {


    public void inserir(Aluno aluno) {
        PreparedStatement pstm = null;
        try {
            final String SQL_INSERT = 
                "insert into ALUNO (MATRICULA,NOME, ENDERECO, CPF, RENDA) " +
                "values (?,?,?,?,?)";
            pstm = Conexao.getCon().prepareStatement(SQL_INSERT);
            pstm.setInt(1, aluno.getMatricula());
            pstm.setString(2, aluno.getNome());
            pstm.setString(3, aluno.getEndereco());
            pstm.setString(4, aluno.getCpf());
            pstm.setDouble(5, aluno.getRenda());
            pstm.executeUpdate();
            System.out.println("Inclusão realizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados!!!\n" + e.getMessage());
        }
    }

    public void atualizar(Aluno aluno) {
        PreparedStatement pstm = null;
        try {
            final String SQL_UPDATE =
                "UPDATE ALUNO SET nome = ?, endereco = ?, " +
                "cpf = ?, renda = ? WHERE matricula = ?";
            pstm = Conexao.getCon().prepareStatement(SQL_UPDATE);
            pstm.setString(1, aluno.getNome());
            pstm.setString(2, aluno.getEndereco());
            pstm.setString(3, aluno.getCpf());
            pstm.setDouble(4, aluno.getRenda());
            pstm.setInt(5, aluno.getMatricula());
            pstm.executeUpdate();
            System.out.println("atualização realizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar dados!!!\n" + e.getMessage());
        }
    }
    
    public void remover(Aluno aluno) {
        PreparedStatement pstm = null;
        try {
            final String SQL_DELETE = "delete from ALUNO where matricula = ?";
            pstm = Conexao.getCon().prepareStatement(SQL_DELETE);
            pstm.setInt(1, aluno.getMatricula());
            pstm.executeUpdate();
            System.out.println("Exclusão realizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados!!!\n" + e.getMessage());
        }
        
    }

    public Aluno getAlunoPorId(int matricula) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            final String SQL_PESQUISA_POR_MATRICULA = 
                    "select * from ALUNO where matricula = ?";
            stmt = Conexao.getCon().prepareStatement(SQL_PESQUISA_POR_MATRICULA);
            stmt.setInt(1, matricula);
            rs = stmt.executeQuery();
            return carregarResultadoSimples(rs);
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
        }
    }

    public List<Aluno> getAlunoPorNome(String nome) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            final String SQL_PESQUISA_POR_MATRICULA =
                    "select * from ALUNO where nome like ?";
            stmt = Conexao.getCon().prepareStatement(SQL_PESQUISA_POR_MATRICULA);
            stmt.setString(1, nome + "%");
            rs = stmt.executeQuery();
            return carregarMultiplosResultados(rs);
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
        }
    }
       
    public List<Aluno> findAll() throws Exception{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL_STATEMENT ="Select MATRICULA, NOME, ENDERECO, CPF, RENDA" +
                " from ALUNO order by NOME";
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

    private List<Aluno> carregarMultiplosResultados(ResultSet rs) throws SQLException{
        List<Aluno> resultList = new ArrayList<Aluno>();
        while (rs.next()) {
            Aluno dto = new Aluno();
            carregarVO( dto, rs);
            resultList.add(dto);
        }
        return resultList;
    }

    private Aluno carregarResultadoSimples(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Aluno dto = new Aluno();
            carregarVO(dto, rs);
            return dto;
        } else {
            return null;
        }
    }

    private void carregarVO(Aluno dto, ResultSet rs)throws SQLException{
        dto.setMatricula(rs.getInt("MATRICULA"));
        dto.setNome(rs.getString("NOME"));
        dto.setEndereco(rs.getString("ENDERECO"));
        dto.setRenda(rs.getDouble("RENDA"));
        dto.setCpf(rs.getString("CPF"));
    }
}
