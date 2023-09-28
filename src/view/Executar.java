package view;

import controller.ThreadTransacao;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Executar {
    public static void main(String[] args) {
        final int QUANTIDADE_TRANSACOES = 20;
        final int TRANSACAO_DO_MESMO_TIPO_POR_VEZ = 1;
        int saldoConta, valorTransacionado;
        boolean estaSacando;

        ThreadTransacao[] transacoes = new ThreadTransacao[QUANTIDADE_TRANSACOES];
        Semaphore semaforoSaque = new Semaphore(TRANSACAO_DO_MESMO_TIPO_POR_VEZ);
        Semaphore semaforoDeposito = new Semaphore(TRANSACAO_DO_MESMO_TIPO_POR_VEZ);

        for (int i = 0; i < QUANTIDADE_TRANSACOES; i++) {
            saldoConta = gerarNumeroAleatorio(1500, 3000);
            estaSacando = gerarBooleanAleatorio();
            valorTransacionado = gerarNumeroAleatorio(500, 2000);

            transacoes[i] = new ThreadTransacao(i, saldoConta, estaSacando, valorTransacionado, semaforoSaque, semaforoDeposito);
            transacoes[i].start();
        }
    }

    public static int gerarNumeroAleatorio(int minimo, int maximo) {
        return (int) (Math.random() * ((maximo - minimo) + 1)) + minimo;
    }

    public static boolean gerarBooleanAleatorio() {
        Random aleatorio = new Random();
        return aleatorio.nextBoolean();
    }
}
