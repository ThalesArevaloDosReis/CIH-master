package com.example.aluno.cih.Adaptador;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aluno.cih.Controller.ListaPublicacoes;
import com.example.aluno.cih.Model.Publicacao;
import com.example.aluno.cih.R;

import java.util.List;

public class AdaptadorListaPubli extends RecyclerView.Adapter<AdaptadorListaPubli.ImageViewHolderPub> {

    Context context;
    private List<String> nomesList, dataList, horaList, conteudoList;
    private List<Integer> numerosList;
    private List<Bitmap> imagensList;
    OnItemClickListener listener;

    public interface OnItemClickListener{
        int onClick(View view, int position);
    }

    public AdaptadorListaPubli(Context context, List<String> nomesList, List<String> dataList, List<String> horaList, List<String> conteudoList, List<Bitmap> imagensList, List<Integer> numerosList,OnItemClickListener listener){
        this.context = context;
        this.nomesList = nomesList;
        this.dataList = dataList;
        this.horaList = horaList;
        this.conteudoList = conteudoList;
        this.imagensList = imagensList;
        this.numerosList = numerosList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolderPub onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicacao_view_holder, parent, false);
        ImageViewHolderPub imageViewHolder = new ImageViewHolderPub(view,listener);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolderPub holder, int position) {
        holder.txt_pub_nome.setText(nomesList.get(position));
        holder.edt_pub_conteudo.setText(conteudoList.get(position));
        holder.txt_pub_data.setText(dataList.get(position));
        holder.txt_pub_hora.setText(horaList.get(position));
        holder.img_pub_usuario.setImageBitmap(imagensList.get(position));
        holder.txtNumeroPub.setText(numerosList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return nomesList.size();
    }

    public class ImageViewHolderPub extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txt_pub_nome;
        TextView txt_pub_data;
        TextView txt_pub_hora;
        TextView txtNumeroPub;
        ImageView img_pub_usuario;
        EditText edt_pub_conteudo;
        OnItemClickListener listener;

        public ImageViewHolderPub(View itemView, OnItemClickListener listener2) {
            super(itemView);
            txt_pub_nome = (TextView) itemView.findViewById(R.id.txt_pub_nome);
            txt_pub_data = (TextView) itemView.findViewById(R.id.txt_pub_data);
            txt_pub_hora = (TextView) itemView.findViewById(R.id.txt_pub_hora);
            txtNumeroPub = (TextView) itemView.findViewById(R.id.txtNumeroPub);
            img_pub_usuario = (ImageView) itemView.findViewById(R.id.img_pub_usuario);
            edt_pub_conteudo = (EditText) itemView.findViewById(R.id.edt_pub_conteudo);
            itemView.setOnClickListener(this);
            this.listener = listener2;
        }

        public int posicao(){
            return getAdapterPosition();
        }

        @Override
        public void onClick(View v) {
            ImageViewHolderPub.this.listener.onClick(v, Integer.parseInt(txtNumeroPub.getText().toString()));
            posicao();
        }
    }

}
