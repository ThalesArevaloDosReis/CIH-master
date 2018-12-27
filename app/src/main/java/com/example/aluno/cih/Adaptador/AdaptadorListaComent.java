package com.example.aluno.cih.Adaptador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aluno.cih.Controller.ListaComentarios;
import com.example.aluno.cih.R;

import java.util.List;

public class AdaptadorListaComent extends RecyclerView.Adapter<AdaptadorListaComent.ImageViewHolderComent> {

    Context context;
    private List<String> nomesList, dataList, horaList, conteudoList;
    private List<Integer> /*contList, */numeroComent;
    private List<Bitmap> imagensList;
    OnItemClickListener listener;

    public interface OnItemClickListener{
        int onClick(View view, int position);
    }

    public AdaptadorListaComent(Context context, List<String> nomesList, List<String> dataList, List<String> horaList, List<String> conteudoList, /*List<Integer> contList,*/ List<Bitmap> imagensList, List<Integer> numeroComent,OnItemClickListener listener){
        this.context = context;
        this.nomesList = nomesList;
        this.dataList = dataList;
        this.horaList = horaList;
        this.conteudoList = conteudoList;
        //this.contList = contList;
        this.imagensList = imagensList;
        this.numeroComent = numeroComent;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolderComent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comentarios_view_holder, parent, false);
        ImageViewHolderComent imageViewHolder = new ImageViewHolderComent(view,listener);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolderComent holder, int position) {
        holder.txt_coment_nome.setText(nomesList.get(position));
        holder.txt_coment_data.setText(dataList.get(position));
        holder.txt_coment_hora.setText(horaList.get(position));
        //holder.txt_cont_curtidas.setText(contList.get(position));
        holder.img_coment_usuario.setImageBitmap(imagensList.get(position));
        //holder.img_coment_curtir.setImageResource(R.drawable.icon_curtir);
        //holder.img_coment_denunciar.setImageResource(R.drawable.icon_denunciar);
        holder.edt_coment_conteudo.setText(conteudoList.get(position));
    }

    @Override
    public int getItemCount() {
        return nomesList.size();
    }

    public class ImageViewHolderComent extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txt_coment_nome;
        TextView txt_coment_data;
        TextView txt_coment_hora;
        //TextView txt_cont_curtidas;
        ImageView img_coment_usuario;
        //ImageView img_coment_curtir;
        //ImageView img_coment_denunciar;
        EditText edt_coment_conteudo;
        OnItemClickListener listener;

        public ImageViewHolderComent(View itemView, OnItemClickListener listener2) {
            super(itemView);
            txt_coment_nome = (TextView) itemView.findViewById(R.id.txt_coment_nome);
            txt_coment_data = (TextView) itemView.findViewById(R.id.txt_coment_data);
            txt_coment_hora = (TextView) itemView.findViewById(R.id.txt_coment_hora);
            //txt_cont_curtidas = (TextView) itemView.findViewById(R.id.txt_cont_curtidas);
            img_coment_usuario = (ImageView) itemView.findViewById(R.id.img_coment_usuario);
            //img_coment_curtir = (ImageView) itemView.findViewById(R.id.img_coment_curtir);
            //img_coment_denunciar = (ImageView) itemView.findViewById(R.id.img_coment_denunciar);
            edt_coment_conteudo = (EditText) itemView.findViewById(R.id.edt_coment_conteudo);
            itemView.setOnClickListener(this);
            this.listener = listener2;
        }

        public int posicao(){
            return getAdapterPosition();
        }

        @Override
        public void onClick(View v) {

            /*img_coment_curtir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    img_coment_curtir.setColorFilter(R.color.colorPrimaryDark, PorterDuff.Mode.ADD);
                    ListaComentarios listaComentarios = new ListaComentarios(context);
                    int contador = Integer.parseInt(txt_cont_curtidas.getText().toString());
                    contador++;
                    listaComentarios.alterarValor(numeroComent.get(getAdapterPosition()),contador);
                    txt_cont_curtidas.setText(contador);
                }
            });
*/
            ImageViewHolderComent.this.listener.onClick(v,getAdapterPosition());
            posicao();
        }
    }

}
