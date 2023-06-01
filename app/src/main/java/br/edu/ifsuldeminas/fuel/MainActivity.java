package br.edu.ifsuldeminas.fuel;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText textInputEditTextEtanol;
    private TextInputEditText textInputEditTextGasolina;
    private Button buttonCalcular;
    private ImageView imageViewFuel;
    private TextView textViewMessage;
    private ImageView imageViewShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputEditTextEtanol = findViewById(R.id.textInputEditTextEtanol);
        textInputEditTextGasolina = findViewById(R.id.textInputEditTextGas);
        buttonCalcular = findViewById((R.id.buttonCalcular));
        imageViewFuel = findViewById(R.id.imageViewFuel);
        textViewMessage = findViewById(R.id.textViewMessage);
        imageViewShare = findViewById(R.id.imageViewShare);

        buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valorEtanol = textInputEditTextEtanol.getText().toString();
                String valorGas = textInputEditTextGasolina.getText().toString();

                if (valorEtanol.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Valor do etanol não pode ser vazio",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (valorGas.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Valor da gasolina não pode ser vazio",
                            Toast.LENGTH_LONG);
                    toast.show();

                    return;
                }


                Double valorEtanol_ = Double.parseDouble(valorEtanol);
                Double valorGasolina = Double.parseDouble(valorGas);

                String mensagem = "";
                if ((valorEtanol_ / valorGasolina) >= 0.7) {
                    mensagem = "Melhor usar Gasolina";
                    imageViewFuel.setImageResource(R.drawable.gas);
                } else {
                    mensagem = "Melhor usar Etanol";
                    imageViewFuel.setImageResource(R.drawable.ethanol);
                }
                imageViewFuel.setVisibility(ImageView.VISIBLE);
                imageViewShare.setVisibility(ImageView.VISIBLE);
                textViewMessage.setText(mensagem);
                textViewMessage.setVisibility(TextView.VISIBLE);

            }
        });


        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Preços de qual Posto?");

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.alert_dialog_gas_station_view, null);
                builder.setView(layout);

                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText nomePosto = layout.findViewById(R.id.editTextAlertDialogGasStationId);
                        String nomePostoStr = nomePosto.getText().toString();

                        String valorEtanol = textInputEditTextEtanol.getText().toString();
                        String valorGas = textInputEditTextGasolina.getText().toString();
                        Double valorEtanol_ = Double.parseDouble(valorEtanol);
                        Double valorGasolina = Double.parseDouble(valorGas);

                        Double relacao = valorEtanol_ / valorGasolina * 100;

                        String opcao = relacao >= 70 ? "gasolina" : "etanol";

                        String mensagem = String.format("Preços no posto '%s'. Etanol R$%.2f - Gasolina R$%.2f. " +
                                        "Melhor usar '%s', relação %.0f%%.",
                                nomePostoStr, valorEtanol_, valorGasolina, opcao, relacao);

                        Intent intentMensagem = new Intent();
                        intentMensagem.setAction(Intent.ACTION_SEND);
                        intentMensagem.putExtra( Intent.EXTRA_TEXT, mensagem);
                        intentMensagem.setType("text/plain");

                        Intent chooserIntent = Intent.createChooser(intentMensagem, "");
                        startActivity(chooserIntent);


                    }
                });
                builder.setNegativeButton("Cancelar", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        imageViewFuel.setVisibility(ImageView.INVISIBLE);
        textViewMessage.setVisibility(TextView.INVISIBLE);
        imageViewShare.setVisibility(ImageView.INVISIBLE);
        //t.setVisibility(ImageView.INVISIBLE);

    }


}