package controller;

import java.util.concurrent.Semaphore;

public class ThreadTransacao extends Thread {
    private int codigoConta;
    private int saldoConta;
    private boolean estaSacando;
    private int valorTransacionado;
    private Semaphore semaforoSaque;
    private Semaphore semaforoDeposito;

    public ThreadTransacao(int codigoConta, int saldoConta, boolean estaSacando, int valorTransacionado, Semaphore semaforoSaque, Semaphore semaforoDeposito) {
        this.codigoConta = codigoConta;
        this.saldoConta = saldoConta;
        this.estaSacando = estaSacando;
        this.valorTransacionado = valorTransacionado;
        this.semaforoSaque = semaforoSaque;
        this.semaforoDeposito = semaforoDeposito;
    }

    @Override
    public void run() {
        if (estaSacando) {
            fazerSaque();
        } else {
            fazerDeposito();
        }
    }

    public void fazerSaque() {
        try {
            semaforoSaque.acquire();

            fazerTransacao();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaforoSaque.release();
        }
    }

    public void fazerDeposito() {
        try {
            semaforoDeposito.acquire();

            fazerTransacao();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaforoDeposito.release();
        }
    }

    public void fazerTransacao() {
        if (estaSacando) {
            saldoConta -= valorTransacionado;
        } else {
            saldoConta += valorTransacionado;
        }

        System.out.println("#" + codigoConta + " >>> " + nomeTipoTransacao() + " R$" + valorTransacionado + " e ficou com R$" + saldoConta);
        fazerEsperar(1000);
    }

    public String nomeTipoTransacao() {
        return (estaSacando) ? "sacou" : "depositou";
    }

    private void fazerEsperar(int tempoEspera) {
        try {
            sleep(tempoEspera);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
