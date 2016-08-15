package br.ufc.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.ufc.agenda.br.ufc.agenda.modelo.Aluno;

/**
 * Created by lucas on 05/08/16.
 */
public class AlunoDAO extends SQLiteOpenHelper{

    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos(id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL," +
                " endereco TEXT," +
                " telefone TEXT," +
                "site TEXT," +
                "nota REAL);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(sql);

        onCreate(db);
    }

    public void insere(Aluno aluno) {
        /*Pega uma instancia do banco que poderá ser feita escrita*/

        SQLiteDatabase db = getWritableDatabase();

        /*Evitando SQLInjection*/

        ContentValues dados = getContentValuesAluno(aluno);

        db.insert("Alunos", null, dados);
    }

    // Método auxiliar criado para settar os valores no ContentValues

    @NonNull
    private ContentValues getContentValuesAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        return dados;
    }

    public List<Aluno> buscaAluno() {
        String sql = "SELECT * FROM Alunos";

        /*Pega uma instancia do banco que poderá ser feita leitura*/

        SQLiteDatabase db = getReadableDatabase();

        /*Faz a leitura com rawQuery e
         retorna um Cursor para percorrer entre os resultados
         */

        Cursor cursor = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<>();

        /*
        * Cursor começa numa posição anterior dos resultados
        * moveToNext avança pra proxima posição e retorna true
        * caso ainda haja resultados.
        *
        */

        while(cursor.moveToNext()){
            Aluno aluno = new Aluno();

            /*cursor.getLong()/String() recupera um valor se baseando no indice da coluna
            * E o indice da coluna é conseguido através de getColumnIndex com base no nome
            * da coluna.*/

            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));

            alunos.add(aluno);
        }

        /*retornando o recurso ao sistema*/

        cursor.close();

        return alunos;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        /*Prevenindo SQL Injection*/
        String[] params = {aluno.getId().toString()};
        db.delete("Alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValuesAluno(aluno);

        /*Prevenindo SQL Injection*/
        String[] params = {aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);
    }
}
