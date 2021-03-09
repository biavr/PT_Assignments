import java.util.*;

public class Polynomial {
    public List<Monomial> poly = new ArrayList<Monomial>();
    public int degree;
    public class QRPair{
        Polynomial quotient = new Polynomial();
        Polynomial remainder = new Polynomial();
    }
    public Polynomial() {
        degree = 0;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int deg){
        degree = deg;
    }

    public void addMonomial(Monomial mon){
        poly.add(mon);
    }

    public Polynomial add(Polynomial p2){
        Polynomial result;
        result = this.copy();
        for (Monomial m : p2.poly) {
            boolean found = false;
            for (Monomial r : result.poly) {
                if(m.getPower() == r.getPower()){
                    if(r.add(m).getCoefficient().doubleValue() != 0){
                        //result.poly.set(result.poly.indexOf(r),r.add(m));
                        r.setCoefficient(r.add(m).getCoefficient());
                    }
                    else{
                        result.poly.remove(r);
                    }
                    found = true;
                    break;
                }
            }
            if(found == false){
                result.addMonomial(m);
            }
        }
        Collections.sort(result.poly,Collections.<Monomial>reverseOrder());
        try{
            result.setDegree(result.poly.get(0).getPower());
        }
        catch(IndexOutOfBoundsException ex){
            result.setDegree(0);
            Monomial m = new Monomial();
            result.addMonomial(m);
        }
        return result;
    }

    public Polynomial subtract(Polynomial p2){
        Polynomial result;
        result = this.copy();
        for (Monomial m : p2.poly) {
            boolean found = false;
            for (Monomial r : result.poly) {
                if(m.getPower() == r.getPower()){
                    if(r.subtract(m).getCoefficient().doubleValue() != 0){
                        result.poly.set(result.poly.indexOf(r),r.subtract(m));
                    }
                    else{
                        result.poly.remove(r);
                    }
                    found = true;
                    break;
                }
            }
            if(found == false){
                result.addMonomial(m.negate());
            }
        }
        Collections.sort(result.poly,Collections.<Monomial>reverseOrder());
        try{
            result.setDegree(result.poly.get(0).getPower());
        }
        catch(IndexOutOfBoundsException ex){
            result.setDegree(0);
            Monomial m = new Monomial();
            result.addMonomial(m);
        }
        return result;
    }

    public Polynomial derivate(){
        Polynomial result = new Polynomial();
        for (Monomial m : poly) {
            Monomial m1 = m.derivate();
            if(m1.getPower() > -1){
                result.addMonomial(m1);
            }
        }
        result.setDegree(this.getDegree() - 1);
        if(result.getDegree() == -1){
            Monomial m = new Monomial();
            result.addMonomial(m);
        }
        return result;
    }

    public Polynomial integrate(){
        Polynomial result = new Polynomial();
        for (Monomial m : poly) {
            result.addMonomial(m.integrate());
        }
        result.setDegree(this.getDegree()+1);
        return result;
    }

    public Polynomial multiply(Polynomial p2){
        Polynomial result = new Polynomial();
        if(this.poly.get(0).getCoefficient().intValue() == 0 || p2.poly.get(0).getCoefficient().intValue() == 0){
            Monomial m = new Monomial();
            result.addMonomial(m);
            return result;
        }
        for (Monomial m1 : poly) {
            Polynomial temp = new Polynomial();
            for (Monomial m2 : p2.poly) {
                temp.addMonomial(m1.multiply(m2));
            }
            result = result.add(temp);
        }
        Collections.sort(result.poly,Collections.<Monomial>reverseOrder());
        result.setDegree(this.getDegree()+p2.getDegree());
        return result;
    }

    Polynomial copy(){
        Polynomial p = new Polynomial();
        for (Monomial m : poly) {
            p.addMonomial(m);
        }
        p.setDegree(this.getDegree());
        return p;
    }

    public QRPair divide(Polynomial p2) throws IllegalArgumentException{
        if(p2.getDegree() == 0 && p2.poly.get(0).getCoefficient().doubleValue() == 0){
            throw new IllegalArgumentException();
        }
        QRPair result = new QRPair();
        if(this.getDegree() == 0 && this.poly.get(0).getCoefficient().doubleValue() == 0){
            result.quotient.addMonomial(new Monomial());
            result.remainder.addMonomial(new Monomial());
        }
        else
        {
            result.remainder = this.copy();
            int step = -1;
            while(result.remainder.getDegree() >= p2.getDegree() && result.remainder.poly.get(0).getCoefficient().doubleValue() != 0){
                result.quotient.addMonomial(result.remainder.poly.get(0).divide(p2.poly.get(0)));
                step++;
                Polynomial temp = new Polynomial();
                for (Monomial m : p2.poly) {
                    temp.addMonomial(result.quotient.poly.get(step).multiply(m));
                }
                result.remainder = result.remainder.subtract(temp).copy();
            }
            if(this.degree < p2.degree){
                result.remainder = new Polynomial();
                result.remainder.addMonomial(new Monomial(0,0));
            }
            Collections.sort(result.remainder.poly,Collections.<Monomial>reverseOrder());
            Collections.sort(result.quotient.poly,Collections.<Monomial>reverseOrder());
            result.remainder.setDegree(result.remainder.poly.get(0).getPower());
            result.quotient.setDegree(result.quotient.poly.get(0).getPower());
        }
        return result;
    }
    @Override
    public String toString(){
        String result = "";
        for (Monomial m : poly) {
            if(m.getCoefficient().doubleValue() > 0){
                if(m.getPower() != this.getDegree()){
                    result += "+";
                }
            }
            result += m.toString();
        }
        return result;
    }
}
