package br.com.etecia.nutriapp.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;


import br.com.etecia.nutriapp.API.Api;
import br.com.etecia.nutriapp.API.RequestHandler;
import br.com.etecia.nutriapp.R;
import br.com.etecia.nutriapp.classes.Produto;


public class CadastrarProdutoFragment extends Fragment {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ImageView btnVoltarCadProd;
    Button btnSalvarProd;
    TextInputEditText txtNomeProd, txtQuantProd, txtPrecoProd, txtMultiplicador, txtQuantEstoque, txtDesc, txtDataEntrada, txtValidade;
    List<Produto> produtoList;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    boolean isUpdating = false;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cadastrarproduto, container, false);

        btnVoltarCadProd = view.findViewById(R.id.btnVoltarCadProd);
        btnSalvarProd = view.findViewById(R.id.btnSalvarProd);
        txtNomeProd = view.findViewById(R.id.txtInputProd);
        txtMultiplicador = view.findViewById(R.id.txtInputMult);
        txtQuantProd = view.findViewById(R.id.txtInputQuant);
        txtPrecoProd = view.findViewById(R.id.txtInputPreco);
        txtQuantEstoque = view.findViewById(R.id.txtInputQuantEstoque);
        txtDataEntrada = view.findViewById(R.id.txtInputDataEntrada);
        txtValidade = view.findViewById(R.id.txtInputValidade);
        txtDesc = view.findViewById(R.id.txtInputDesc);


        //final Produto produto = produtoList.get();

        btnVoltarCadProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack(); // Volta para o fragment anterior
                }
            }
        });

        btnSalvarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    createprodutos();
                    Toast.makeText(getContext(), "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        return view;
    }

    public void createprodutos() throws ParseException {

        String nome = txtNomeProd.getText().toString().trim();
        String quantidade = txtQuantProd.getText().toString().trim();
        String preco = txtPrecoProd.getText().toString().trim();
        String multiplicador = txtMultiplicador.getText().toString().trim();
        String quantEstoque = txtQuantEstoque.getText().toString().trim();
        String validade = txtValidade.getText().toString().trim();
        String dataEntrada = txtDataEntrada.getText().toString().trim();
        String desc = txtDesc.getText().toString().trim();

        if (TextUtils.isEmpty(nome)) {
            txtNomeProd.setError("Por favor entre com o nome");
            txtNomeProd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(quantidade)) {
            txtQuantProd.setError("Por favor entre com a quantidade");
            txtQuantProd.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(preco)) {
            txtPrecoProd.setError("Por favor entre com o preço");
            txtPrecoProd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(multiplicador)) {
            txtMultiplicador.setError("Por favor entre com o multiplicador");
            txtMultiplicador.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(quantEstoque)) {
            txtQuantEstoque.setError("Por favor entre com a quantidade em estoque");
            txtQuantEstoque.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(dataEntrada)) {
            txtDataEntrada.setError("Por favor entre com a data de entrada");
            txtDataEntrada.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(validade)) {
            txtValidade.setError("Por favor entre com a validade");
            txtValidade.requestFocus();
            return;
        }
        quantidade = String.valueOf(Double.parseDouble(quantidade));
        preco = String.valueOf(Double.parseDouble(preco));
        multiplicador = String.valueOf(Double.parseDouble(multiplicador));
        quantEstoque = String.valueOf(Double.parseDouble(quantEstoque));
        dataEntrada = String.valueOf(formato.parse(dataEntrada));
        validade = String.valueOf(formato.parse(validade));

        HashMap<String, String> params = new HashMap<>();
        //params.put("id", id);
        params.put("nome", nome);
        params.put("quantidade", quantidade);
        params.put("preco", preco);
        params.put("multiplicador", multiplicador);
        params.put("quantEstoque", quantEstoque);
        params.put("dataEntrada", dataEntrada);
        params.put("validade", validade);
        params.put("desc", desc);
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_PRODUTOS, params, CODE_POST_REQUEST);
        request.execute();



    };

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }}