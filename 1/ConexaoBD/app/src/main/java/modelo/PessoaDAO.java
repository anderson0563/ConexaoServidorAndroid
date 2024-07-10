/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controlador.FabricaConexoes;

/**
 *
 * @author anderson
 */
public class PessoaDAO {

    Connection banco;

    public PessoaDAO() throws ClassNotFoundException, SQLException {
        FabricaConexoes fabrica = new FabricaConexoes();
        banco = fabrica.Conexao();
    }
    
    @Override
    @SuppressWarnings("FinalizeDeclaration")
    protected void finalize() throws SQLException, Throwable
    {
        try {
            banco.close();
        } finally {
            super.finalize();
        }
    }

    public int inserir(Pessoa p) throws ClassNotFoundException, SQLException {
        PreparedStatement stmt = banco.prepareStatement("INSERT INTO pessoa (nome, "
                + "email, cpf, telefone) VALUES (?, ?, ?, ?)");

        stmt.setString(1, p.getNome());
        stmt.setString(2, p.getEmail());
        stmt.setString(3, p.getCpf());
        stmt.setString(4, p.getTelefone());
        return stmt.executeUpdate();
    }
    
    public int apagarPeloNome(Pessoa p) throws SQLException{
        PreparedStatement stmt = banco.prepareStatement("DELETE FROM pessoa WHERE nome LIKE ?");
        stmt.setString(1, "%"+p.getNome()+"%");
        return stmt.executeUpdate();
    }
    
    public int atualizarTelefone(Pessoa p, String telefone) throws SQLException{
        PreparedStatement stmt = banco.prepareStatement("UPDATE pessoa SET telefone=? WHERE nome LIKE ?");
        stmt.setString(1, telefone);
        stmt.setString(2, "%"+p.getNome()+"%");
        return stmt.executeUpdate();        
    }
    
    public ArrayList listar() throws SQLException{
        PreparedStatement stmt = banco.prepareStatement("SELECT * FROM pessoa");
        ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
        
        ResultSet resultado = stmt.executeQuery();
        Pessoa p;
        while(resultado.next()){
            p = new Pessoa();
            p.setNome(resultado.getString("nome"));
            p.setCpf(resultado.getString("cpf"));
            p.setEmail(resultado.getString("email"));
            p.setTelefone(resultado.getString("telefone"));
            lista.add(p);
        }
        
        return lista;
    }
            
    
}
