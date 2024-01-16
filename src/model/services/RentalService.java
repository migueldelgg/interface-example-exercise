package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
    private Double pricePerDay;
    private Double pricePerHour;

    private TaxService taxService;

    //nao tem construtor padrao pois estamos assumindo que é obrigatório instanciar os 3 valores.
    public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
        this.taxService = taxService;
    }

    public void processInvoice(CarRental carRental){
        long t1 = carRental.getStart().getTime(); //pega o valor em milisegundos da data de inicio
        long t2 = carRental.getFinish().getTime(); //pega o valor em milisegundos da data de entrega;

        /* converte para segundos dividindo por 1000 -->
           converte para minutos dividindo por 60 -->
           converte para horas dividindo por 60 novamente -->
           o casting para double garante que o valor venha quebrado para podermos arrendondar */
        double hours = (double)(t2 - t1) / 1000 / 60 / 60;
        double basicPayment;
        if (hours <= 12) {
            basicPayment = Math.ceil(hours) * pricePerHour; // Math.ceil arredonda para cima
        }
        else {
            basicPayment = Math.ceil(hours / 24) * pricePerDay; // Math.ceil arredonda para cima
        }

        double tax = taxService.tax(basicPayment);

        // Criei um novo objeto de nota de pagamento
        // e associamos ao objeto de aluguel
        carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}
