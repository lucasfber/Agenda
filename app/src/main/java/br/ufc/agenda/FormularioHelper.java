package br.ufc.agenda;

import android.widget.EditText;
import android.widget.RatingBar;

import br.ufc.agenda.br.ufc.agenda.modelo.Aluno;

/**
 * Created by lucas on 27/07/16.
 */
public class FormularioHelper {

    private final EditText nome;
    private final EditText endereco;
    private final EditText telefone;
    private final EditText site;
    private final RatingBar nota;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {
        nome = (EditText) activity.findViewById(R.id.formulario_nome);
        endereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        telefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        site = (EditText) activity.findViewById(R.id.formulario_site);
        nota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        aluno = new Aluno();
    }

    public Aluno getAluno(){
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));

        return  aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        nome.setText(aluno.getNome());
        endereco.setText(aluno.getEndereco());
        telefone.setText(aluno.getTelefone());
        site.setText(aluno.getSite());
        nota.setProgress(aluno.getNota().intValue());
        this.aluno = aluno;
    }
}

