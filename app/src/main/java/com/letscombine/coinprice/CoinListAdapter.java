package com.letscombine.coinprice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.letscombine.coinprice.define.StringDefine;

import java.util.ArrayList;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.ViewHolder> {
    ArrayList<CoinDetailVO> coinListArray = new ArrayList<>();


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_view_contents, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CoinListAdapter.ViewHolder holder, int position) {
        if (coinListArray != null) {
            holder.txtExchange.setText(coinListArray.get(position).getCoinExchange());
            holder.txtCoinName.setText(coinListArray.get(position).getCoinName());
            holder.txtPresentPrice.setText(coinListArray.get(position).getCoinPrice());

            // 거래대금이 백만이상이면 백만자리 아래를 절사하고 "백만"으로 표기
            if (coinListArray.get(position).getCoinTransactionAmount().length() > 6) {
                coinListArray.get(position).setCoinTransactionAmount(coinListArray.get(position).getCoinTransactionAmount().substring(0, coinListArray.get(position).getCoinTransactionAmount().length() - 6) + StringDefine.TRANSACTION_PRICE_KOR);
            }

            holder.txtVolume.setText(coinListArray.get(position).getCoinTransactionAmount());
        } else {
            Toast.makeText(holder.itemView.getContext(), "뭔가 잘못 됬음", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return coinListArray.size();
    }


    public void setItem(CoinDetailVO coinListArray) {
        this.coinListArray.add(coinListArray);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtExchange = null;
        private TextView txtCoinName = null;
        private TextView txtPresentPrice = null;
        private TextView txtVolume = null;
        private ImageView imgRemoveData = null;

        public ViewHolder(View itemView) {
            super(itemView);

            txtExchange = itemView.findViewById(R.id.txtExchange);
            txtCoinName = itemView.findViewById(R.id.txtCoinName);
            txtPresentPrice = itemView.findViewById(R.id.txtPresentPrice);
            txtVolume = itemView.findViewById(R.id.txtVolume);
            imgRemoveData = itemView.findViewById(R.id.imgRemoveData);
            imgRemoveData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    coinListArray.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
