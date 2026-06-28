package br.edu.cafeteria.servico;

import br.edu.cafeteria.modelo.Bebida;
import br.edu.cafeteria.modelo.ItemPedido;
import br.edu.cafeteria.modelo.Venda;
import java.util.ArrayList;
import java.util.List;

public class DescontoDiaGeek implements Promocional {

    private static final double DESCONTO_BEBIDA = 0.10;

    @Override
    public void aplicarDesconto(Venda venda) {
        // Criamos uma cópia local apenas para ler os itens com segurança
        List<ItemPedido> copiaItens = new ArrayList<>(venda.getItens());

        for (ItemPedido item : copiaItens) {
            if (item.getProduto() instanceof Bebida) {
                double precoBase = item.getProduto().getPreco_Base();
                double precoComDesconto = precoBase * (1 - DESCONTO_BEBIDA);

                // Pedimos para a própria venda atualizar o valor do item no carrinho dela
                venda.atualizarPrecoDoItem(item.getProduto(), precoComDesconto);
            }
        }
    }
}