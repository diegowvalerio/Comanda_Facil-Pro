package br.com.dw.comanda_facil_pro.telas.relatorio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import br.com.dw.comanda_facil_pro.R;
import br.com.dw.comanda_facil_pro.util.Util;

public class Relatorios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
    }

    public void rel_totalvenda(View view) {
            Intent intent = new Intent(this, Total_Venda.class);
            startActivity(intent);
    }

    public void rel_totalvendamesa(View view)  {
            Intent intent = new Intent(this, Total_Venda_Mesa.class);
            startActivity(intent);

    }

    public void rel_totalvendames(View view) {
            Intent intent = new Intent(this, Total_Venda_Mensal.class);
            startActivity(intent);
    }
}