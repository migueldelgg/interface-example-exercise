package model.services;

public class BrazilTaxService {
    //nao é um Double pois estamos assumindo que esse metodo sempre retornara um valor nao nulo.
    public double tax(double amount) {
        if (amount <= 100.0) {
            return amount * 0.2;
        }
        else {
            return amount * 0.15;
        }
    }
}
