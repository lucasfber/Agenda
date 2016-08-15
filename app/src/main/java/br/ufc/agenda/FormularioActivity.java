package br.ufc.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import br.ufc.agenda.br.ufc.agenda.modelo.Aluno;
import br.ufc.agenda.dao.AlunoDAO;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();

        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if(aluno != null){
            helper.preencheFormulario(aluno);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void salvarAluno(MenuItem item){
        Aluno aluno = helper.getAluno();
        AlunoDAO dao = new AlunoDAO(this);

        if(aluno.getId() != null){
            dao.altera(aluno);
        }
        else{
            dao.insere(aluno);
        }

        dao.close();

        finish();
    }

}
