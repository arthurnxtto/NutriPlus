package br.com.etecia.nutriapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.etecia.nutriapp.classes.Produto;
import br.com.etecia.nutriapp.R;
import br.com.etecia.nutriapp.fragment.CadastrarProdutoFragment;
import br.com.etecia.nutriapp.fragment.EditarProdutoFragment;
import br.com.etecia.nutriapp.fragment.RefeicoesFragment;


public class EstoqueAdapter extends RecyclerView.Adapter<EstoqueAdapter.ViewHolder> {

    private Context context;
     List<Produto> produtoList;

    // Construtor do Adaptador
    public EstoqueAdapter(Context context, List<Produto> produtoList) {
        this.context = context;
        this.produtoList = produtoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout para um item de produto
        View view = LayoutInflater.from(context).inflate(R.layout.produtomodelo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtém o produto para a posição atual
        Produto produto = produtoList.get(position);

        // Configura o texto das views
        holder.txtProdModelo.setText(produto.getNome());
        holder.txtQuantModelo.setText(String.valueOf(produto.getQuantidade()));
        holder.txtPrecoModelo.setText(String.format("R$ %.2f", produto.getPreco())); // Ajuste conforme necessário


        holder.btnEditarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                EditarProdutoFragment fragment = new EditarProdutoFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.idFragmentEstoque, fragment) // Substitua pelo ID do seu container de fragmentos
                        .addToBackStack(null)
                        .commit();
            }
        });

        holder.btnExcluirProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Excluir item")
                        .setMessage("Tem certeza de que deseja excluir este item?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            // Ação a ser tomada se o usuário confirmar
                            produtoList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                        })
                        .setNegativeButton("Não", (dialog, which) -> {
                            // Se o usuário cancelar, apenas fechar o diálogo
                            dialog.dismiss();
                        })
                        .create()
                        .show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    // Classe ViewHolder para armazenar as views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProdModelo;
        TextView txtQuantModelo;
        TextView txtPrecoModelo;
        ImageView btnEditarProduto;
        ImageView btnExcluirProduto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProdModelo = itemView.findViewById(R.id.txtProdModelo);
            txtQuantModelo = itemView.findViewById(R.id.txtQuantModelo);
            txtPrecoModelo = itemView.findViewById(R.id.txtPrecoModelo);
            btnEditarProduto = itemView.findViewById(R.id.btnEditarProduto);
            btnExcluirProduto = itemView.findViewById(R.id.btnExcluirProduto);

        }
    }
}
