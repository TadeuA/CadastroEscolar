/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author tadeu
 */
import conexao.Conexao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Turma;
import conexao.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class TurmaDAO {
    public void inserir(Turma turma){
        PreparedStatement pstm = null;
        try{
            final String SQL_INSET = "insert into Turma (ID, MATRICULA_PROFESSOR, ID_CURSO) values(?,?,?)";
            pstm = Conexao.getCon().prepareStatement(SQL_INSET);
            pstm.setInt(1, turma.getId());
            pstm.setString(2, Integer.toString(turma.getProfessor().getMatricula()));
            pstm.setString(3, Integer.toString(turma.getCurso().getId()));
            pstm.executeUpdate();
        } catch(SQLException e){
            System.out.println("erro ao inserir dados \n " + e.getMessage());
        }
    }
    public void atualizar(Turma turma){
        PreparedStatement pstm = null;
        try{
            final String SQL_INSET = "update Turma set ID = ?, MATRICULA_PROFESSOR = ?, ID_CURSO = ? where ID = ?";
            pstm = Conexao.getCon().prepareStatement(SQL_INSET);
            pstm.setInt(1, turma.getId());
            pstm.setString(2, Integer.toString(turma.getProfessor().getMatricula()));
            pstm.setString(3, Integer.toString(turma.getCurso().getId()));
            pstm.executeUpdate();
        } catch(SQLException e){
            System.out.println("erro ao inserir dados \n " + e.getMessage());
        }
    }
    public void remover(Turma turma) {
        PreparedStatement pstm = null;
        try {
            final String SQL_DELETE = "delete from Turma where ID = ?";
            pstm = Conexao.getCon().prepareStatement(SQL_DELETE);
            pstm.setInt(1, turma.getId());
            pstm.executeUpdate();
            System.out.println("Exclus�o realizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados!!!\n" + e.getMessage());
        }        
    }
    public Turma getTurmaPorId(int ID){
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            final String SQL_PESQUISA = "select * from Turma where ID = ?;";
            pstm = Conexao.getCon().prepareStatement(SQL_PESQUISA);
            pstm.setInt(1, ID);
            rs = pstm.executeQuery();
            return carregarResultadoSimples(rs);
        }catch (SQLException e) {
            System.out.println("Erro ao inserir dados!!!\n" + e.getMessage());
        }
        return null;
    }
    
  
       
    public List<Turma> findAll() throws Exception{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL_STATEMENT ="Select ID, MATRICULA_PROFESSOR, ID_CURSO" +
                " from TURMA order by ID";
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

    private List<Turma> carregarMultiplosResultados(ResultSet rs) throws SQLException{
        List<Turma> resultList = new ArrayList<Turma>();
        while (rs.next()) {
            Turma dto = new Turma();
            carregarVO( dto, rs);
            resultList.add(dto);
        }
        return resultList;
    }

    private Turma carregarResultadoSimples(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Turma dto = new Turma();
            carregarVO(dto, rs);
            return dto;
        } else {
            return null;
        }
    }

    private void carregarVO(Turma dto, ResultSet rs)throws SQLException{
        ProfessorDAO prof = null;
        CursoDAO cur = null;
        dto.setId(rs.getInt("ID"));
        dto.setProfessor(prof.getProfessorPorMatricula(Integer.parseInt(rs.getString("MATRICULA_PROFESSOR"))));
        dto.setCurso(cur.getCursoPorId(Integer.parseInt(rs.getString("ID_CURSO"))));
       
    }
}
