public class Monomial implements Comparable<Monomial> {
    private Number coefficient;
    private int power;

    public Number getCoefficient(){
        return coefficient.intValue();
    }

    public int getPower(){
        return power;
    }

    public void setCoefficient(Number coef){
        coefficient = coef;
    }

    public void setPower(int pow){
        power = pow;
    }

    public Monomial(){
        coefficient = 0;
        power = 0;
    }

    public Monomial(Number coef, int pow){
        coefficient = coef;
        power = pow;
    }

    public Monomial negate(){
        Monomial negated = new Monomial(-coefficient.intValue(),power);
        return negated;
    }

    public Monomial add(Monomial m1){
        Monomial m2 = new Monomial();
        m2.setCoefficient(m1.coefficient.intValue() + coefficient.intValue());
        m2.setPower(getPower());
        return m2;
    }

    public Monomial subtract(Monomial m1){
        Monomial m2 = new Monomial();
        m2.coefficient = coefficient.intValue() - m1.coefficient.intValue();
        m2.power = power;
        return m2;
    }

    public Monomial multiply(Monomial m1){
        Monomial m2 = new Monomial();
        m2.coefficient = m1.coefficient.intValue() * coefficient.intValue();
        m2.power = m1.power + power;
        return m2;
    }

    public Monomial derivate(){
        Monomial m = new Monomial();
        m.coefficient = coefficient.intValue() * power;
        m.power = power - 1;
        return m;
    }

    public Monomial integrate(){
        Monomial m = new Monomial();
        m.coefficient = coefficient.doubleValue() / (power + 1);
        m.power = power + 1;
        return m;
    }

    public Monomial divide(Monomial divisor){
        Monomial result = new Monomial();
        result.setPower(this.getPower() - divisor.getPower());
        result.setCoefficient(this.getCoefficient().doubleValue() / divisor.getCoefficient().doubleValue());
        return result;
    }

    @Override
    public String toString(){
        String result = "";
        /*if(coefficient.doubleValue()>0){
            result="+";
        }
        result += coefficient.toString() + "x^" + power;*/
        if(coefficient.doubleValue() != 1) {
            if (coefficient.doubleValue() == -1) {
                result = "-";
                if(power == 0){
                    result += "1";
                }
            } else{
                if(coefficient.doubleValue() == coefficient.intValue()){
                    result = Integer.toString(coefficient.intValue());
                }
                else{
                    result = String.format("%2f",coefficient.doubleValue());
                }
            }
        }
        else {
            if(power == 0){
                result = "1";
            }
        }
        if(power > 0){
            if(power == 1){
                result += "x";
            }
            else{
                result += "x^" + Integer.toString(power);
            }
        }
        return result;
    }

    @Override
    public int compareTo(Monomial o) {
        Integer m1 = new Integer(this.getPower());
        Integer m2 = new Integer(o.getPower());
        return m1.compareTo(m2);
    }
}