package io.sjcdigital.repasse.model.entity.base;

/**
 * @author Pedro Hos <pedro-hos@outlook.com>
 *
 */
public enum FaixaPopulacao {
    
    A, B, C, D, E, F, G, NA;
    
    public static void main(String[] args) {
        System.out.println(FaixaPopulacao.porHabitantes(0));
    }
    
    public static FaixaPopulacao porHabitantes(Integer habitantes) {
        
        if(habitantes <= 5000) {
            return A;
        } else if (habitantes > 5000 && habitantes <= 10000) {
            return B;
        } else if (habitantes > 10000 && habitantes <= 20000) {
            return C;
        } else if (habitantes > 20000 && habitantes <= 50000) {
            return D;
        } else if (habitantes > 50000 && habitantes <= 100000) {
            return E;
        } else if (habitantes > 100000 && habitantes <= 200000) {
            return F;
        } else if (habitantes > 200000) {
            return G;
        } 
        
        return NA;
        
        
    }
}
