package br.edu.cafeteria.servico;

import br.edu.cafeteria.modelo.Bebida;
import br.edu.cafeteria.modelo.ItemPedido;
import br.edu.cafeteria.modelo.Venda;

public class DescontoDiaGeek implements Promocional {

    private static final double DESCONTO_BEBIDA = 0.10;

    @Override
    public void aplicarDesconto(Venda venda) {
        for (ItemPedido item : venda.getItens()) {
            if (item.getProduto() instanceof Bebida) {
                double precoComDesconto = item.getProduto().getPreco_Base() * (1 - DESCONTO_BEBIDA);
                item.setPrecoUnitarioAplicado(precoComDesconto);
            }
        }
    }
}