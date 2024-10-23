package br.com.etecia.nutriapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import br.com.etecia.nutriapp.R;


public  class EditarProdutoFragment extends Fragment {
    ImageView btnVoltarEditProd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editarproduto, container, false);

       btnVoltarEditProd = view.findViewById(R.id.btnVoltarEditProduto);

       btnVoltarEditProd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (getActivity() != null) {
                   getActivity().getSupportFragmentManager().popBackStack(); // Volta para o fragment anterior
               }
           }
       });

        return view;
    }
}