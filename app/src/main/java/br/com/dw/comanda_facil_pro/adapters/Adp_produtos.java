package br.com.dw.comanda_facil_pro.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.dw.comanda_facil_pro.R;
import br.com.dw.comanda_facil_pro.entidades.Produto;

public class Adp_produtos extends BaseAdapter {

    Context context;
    List<Produto> produtos;

    public Adp_produtos(Context context,List<Produto> produtos){
        this.context = context;
        this.produtos = produtos;
    }
    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produtos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.adp_produtos,parent,false);
        TextView descricao =view.findViewById(R.id.adp_produto_descricao);
        TextView id = view.findViewById(R.id.adp_produto_id);
        TextView status = view.findViewById(R.id.adp_produto_status);
        TextView valor = view.findViewById(R.id.adp_produto_valor);
        ImageView imagem = view.findViewById(R.id.adp_produto_imagem);

        Produto produto = produtos.get(position);

        descricao.setText(produto.getDescricao());
        id.setText(produto.getId().toString());
        if(produto.isStatus()){
            status.setText("Ativo");
            status.setTextColor(Color.parseColor("#2A9186"));
        }else{
            status.setText("Inativo");
        }


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = 3;
        options.inJustDecodeBounds = false;
        byte[] b = produto.getImagem();
        imagem.setImageBitmap(BitmapFactory.decodeByteArray(b,0,b.length,options));
//        imagem.setImageBitmap(BitmapFactory.decodeByteArray(produto.getImagem(), 0, produto.getImagem().length));
        valor.setText("R$ "+Double.toString(produto.getValor()));

        return view;
    }

}
