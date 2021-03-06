package br.ufc.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.ufc.agenda.br.ufc.agenda.modelo.Aluno;
import br.ufc.agenda.dao.AlunoDAO;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);


        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View itemDaLista, int posicao, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(posicao);
                Intent it = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                it.putExtra("aluno", aluno);
                startActivity(it);
            }
        });

        /*registrando a View para o Menu de Contexto ContextMenu*/

        registerForContextMenu(listaAlunos);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                /*Como sabemos que estamos usando um Adapter numa ListView, faremos o cast AdapterContextMenuInfo*/
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);

                dao.close();

                carregaLista();
                return false;
            }
        });
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAluno();
        dao.close();

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this,android.R.layout.simple_list_item_1,alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregaLista();
    }

    public void chamaFormulario(View v){
        Intent it = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
        startActivity(it);
    }
}
