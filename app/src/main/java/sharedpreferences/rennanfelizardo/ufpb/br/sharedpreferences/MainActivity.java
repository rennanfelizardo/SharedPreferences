package sharedpreferences.rennanfelizardo.ufpb.br.sharedpreferences;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText nomeCampo;
    private Button salvarBotao;
    private TextView textoExibicao;
    private Button resetBotao;
    private AlertDialog.Builder dialog;

    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeCampo = (EditText) findViewById(R.id.nomeId);
        salvarBotao = (Button) findViewById(R.id.salvarBotaoId);
        textoExibicao = (TextView) findViewById(R.id.textoExibicaoId);
        resetBotao = (Button) findViewById(R.id.resetBotaoId);


        //ao clicar no botão salvar as seguintes ação irão ocorrer:
        //Salvar o nome de forma que possa ser recuperado quando reabrir o aplicativo
        salvarBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeUsuario = nomeCampo.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nome", nomeUsuario).apply();

                textoExibicao.setText(nomeUsuario);
                Toast.makeText(MainActivity.this, "Nome " + nomeUsuario + " salvo com sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        //ao abrir o aplicativo esta ação é executada, buscando no arquivo se o nome do usuário foi definido ou não
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        String nomeUsuario;
        if (sharedPreferences.contains("nome")){

            nomeUsuario = sharedPreferences.getString("nome", getResources().getString(R.string.usuario_nao_definido_texto));
            textoExibicao.setText(nomeUsuario);
        }else{
            nomeUsuario = getResources().getString(R.string.usuario_nao_definido_texto);
        }
        textoExibicao.setText(nomeUsuario);


        //ao clicar no botão reset
        resetBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //criação da caixa de diálogo que irá alertar o usuário
                dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Deseja realmente apagar?")
                        .setMessage("Ao confirmar, todos os dados serão apagados e não serão possíveis serem resgatados futuramente.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //faça nada
                            }
                        })
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                                sharedPreferences.edit().clear().apply();
                                Toast.makeText(MainActivity.this, "Dados apagados com sucesso", Toast.LENGTH_SHORT).show();
                                String nomeUsuario = getResources().getString(R.string.usuario_nao_definido_texto);
                                textoExibicao.setText(nomeUsuario);
                            }
                        });
                dialog.create().show();
            }
        });
    }
}
