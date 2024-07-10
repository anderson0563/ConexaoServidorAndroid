package br.edu.unicarioca.informatica.anderson.conexaobd;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import controlador.FabricaConexoes;
import modelo.Pessoa;
import modelo.PessoaDAO;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void Conexao(View v) throws SQLException, ClassNotFoundException {
        FabricaConexoes conexao = new FabricaConexoes();
        boolean conectou = conexao.Conexao()!=null;
        TextView conexaotxt = (TextView) findViewById(R.id.conexao);
        conexaotxt.setText(conectou ? "SUCESSO" : "FALHA");
    }
    public void Inserir(View v) throws ParseException, SQLException, ClassNotFoundException {
        Pessoa p = new Pessoa();
        p.setNome("Anderson");
        p.setEmail("asantos@unicarioca.edu.br");
        p.setCpf("000.000.000-00");
        p.setTelefone("9999-8888");
        Date datanascimento = new SimpleDateFormat("yyyy-MM-dd").parse("1974-01-01");
        PessoaDAO operacao = new PessoaDAO();
        int resultado = operacao.inserir(p);
        TextView inserirtxt = (TextView) findViewById(R.id.inserir);
        inserirtxt.setText(resultado==1?"INSERIU":"NÃO INSERIU");
    }
    public void Remover(View v) throws SQLException, ClassNotFoundException {
        Pessoa p = new Pessoa();
        p.setNome("Anderson");
        PessoaDAO operacao = new PessoaDAO();
        int resultado = operacao.apagarPeloNome(p);
        TextView removertxt = (TextView) findViewById(R.id.remover);
        removertxt.setText(resultado > 0 ? "APAGOU" : "NÃO APAGOU");
    }
    public void Atualizar(View v) throws SQLException, ClassNotFoundException {
        Pessoa p = new Pessoa();
        p.setNome("Anderson");
        String telefone = "8888-9999";
        PessoaDAO operacao = new PessoaDAO();
        int resultado = operacao.atualizarTelefone(p, telefone);
        TextView atualizartxt = (TextView) findViewById(R.id.atualizar);
        atualizartxt.setText(resultado > 0 ? "ATUALIZOU" : "NÃO ATUALIZOU");
    }
    public void Listar(View v) throws SQLException, ClassNotFoundException {
        PessoaDAO operacao = new PessoaDAO();
        ArrayList lista = operacao.listar();
        Iterator indice = lista.iterator();
        Pessoa p = new Pessoa();
        String resposta = "";
        int i=0;
        while(indice.hasNext())
        {
            resposta += (Pessoa) lista.get(i++);
            indice.next();
        }
        TextView listartxt = (TextView) findViewById(R.id.listar);
        listartxt.setText(resposta);
    }
}
