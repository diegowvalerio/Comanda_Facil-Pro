package br.com.dw.comanda_facil_pro.telas.comanda;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.dw.comanda_facil_pro.R;
import br.com.dw.comanda_facil_pro.adapters.Adp_ComandasMesas;
import br.com.dw.comanda_facil_pro.banco.DatabaseHelper;
import br.com.dw.comanda_facil_pro.dao.Dao_Comanda;
import br.com.dw.comanda_facil_pro.dao.Dao_Mesa;
import br.com.dw.comanda_facil_pro.entidades.Comanda;
import br.com.dw.comanda_facil_pro.entidades.Mesa;

public class Comandas_Mesa extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    DatabaseHelper banco;
    Dao_Comanda dao_comanda;
    Dao_Mesa dao_mesa;
    Mesa mesa;
    TextView mesaselecionada;
    List<Comanda> comandas = new ArrayList<>();
    List<Comanda> totalgeralcomadas = new ArrayList<>();
    int idmesa;
    ListView listView;
    Adp_ComandasMesas adp_comandasMesas;
    int n = 0;
    private AlertDialog alerta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);

        mesaselecionada = findViewById(R.id.mesaselecionada);
        listView = findViewById(R.id.listvew_pedidos);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        preenche();
    }

    public void comanda_pedido(View view){
        Intent intent = new Intent(this, Comanda_Pedido.class);
        intent.putExtra("id", idmesa);
        startActivity(intent);
    }

    private void preenche() {
        //busca comandas em aberto,parcial ,atendido da mesa selecionada.

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("id")) {
            idmesa = bundle.getInt("id");
            banco = new DatabaseHelper(this);
            try {
                dao_mesa = new Dao_Mesa(banco.getConnectionSource());
                mesa = dao_mesa.queryForId(idmesa);
                mesaselecionada.setText(mesa.getDescricao());

                Object[] status = {"ABERTO","PARCIAL","ATENDIDO"};
                dao_comanda = new Dao_Comanda(banco.getConnectionSource());
                comandas = dao_comanda.queryBuilder().where().eq("mesa",idmesa).and().in("status",status).query();
                adp_comandasMesas = new Adp_ComandasMesas(this,comandas);
                listView.setAdapter(adp_comandasMesas);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        try {
            //pega total de comandas do sistema
            totalgeralcomadas.clear();
            totalgeralcomadas = dao_comanda.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Comanda comanda = (Comanda) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, Comanda_Pedido.class);
        intent.putExtra("idcomanda",comanda.getId());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Comanda comanda = (Comanda) parent.getItemAtPosition(position);
        if(comanda.getValor_total() > 0){
            Toast.makeText(this, "Para excluir a Comanda, remova todos os itens ! ", Toast.LENGTH_SHORT).show();
        }else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            ArrayList<String> itens = new ArrayList<>();
            itens.add("Sim");
            itens.add("Não");
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_alerta, itens);
            builder.setTitle("Confirma Exclusão da Comanda ?");
            builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    if(arg1 == 0){
                        try {
                            dao_comanda.delete(comanda);
                            Toast.makeText(Comandas_Mesa.this, "Comanda Excluído com Sucesso !", Toast.LENGTH_SHORT).show();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            Toast.makeText(Comandas_Mesa.this, "Erro ao Excluir Comanda !", Toast.LENGTH_SHORT).show();
                        }
                        alerta.dismiss();
                        preenche();
                    }else if(arg1 ==1){
                        alerta.dismiss();
                    }
                }
            });
            alerta = builder.create();
            alerta.show();
        }
        return true;
    }
}